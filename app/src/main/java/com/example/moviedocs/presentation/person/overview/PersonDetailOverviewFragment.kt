package com.example.moviedocs.presentation.person.overview

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.moviedocs.databinding.FragmentPersonDetailOverviewBinding
import com.example.moviedocs.presentation.base.BaseFragment
import com.example.moviedocs.presentation.person.PersonDetailUiState
import com.example.moviedocs.presentation.person.PersonDetailViewModel
import com.example.moviedocs.utils.formatDate
import com.example.moviedocs.utils.invisible
import com.example.moviedocs.utils.launchAndRepeatStarted
import com.example.moviedocs.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.Period

@AndroidEntryPoint
class PersonDetailOverviewFragment : BaseFragment<FragmentPersonDetailOverviewBinding>(
  FragmentPersonDetailOverviewBinding::inflate
) {
  companion object {
    fun newInstance(): PersonDetailOverviewFragment = PersonDetailOverviewFragment()
  }

  private var personId: Int = 0

  private val viewModel: PersonDetailViewModel by viewModels()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    personId = arguments?.getInt("personId") ?: 0
    return super.onCreateView(inflater, container, savedInstanceState)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    viewModel.setPersonId(personId)
    bindViewModel()
  }

  private fun bindViewModel() {
    launchAndRepeatStarted({ viewModel.uiState.collect(::renderUi) })
  }

  @SuppressLint("SetTextI18n")
  private fun renderUi(state: PersonDetailUiState) {
    when (state) {
      PersonDetailUiState.Loading -> {
        hideErrorDialog()

        binding.scrollView.invisible()
      }

      is PersonDetailUiState.Success -> {
        hideErrorDialog()

        binding.apply {
          scrollView.visible()

          personDetailBiography.text = state.personDetail.biography
          knownForValue.text = state.personDetail.knownForDepartment
          genderValue.text = state.personDetail.gender
          val birthday: String = state.personDetail.birthday.formatDate()
          val deathday: String = state.personDetail.deathday.formatDate()
          val age: Int = calculateAge(birthday, deathday)
          birthdayValue.text = when {
            birthday == "Unknown date" -> birthday
            deathday == "Unknown date" && age != 0 -> "$birthday - Now ($age years old)"
            deathday == "Unknown date" -> "$birthday - Now"
            else -> "$birthday - $deathday ($age years old)"
          }
          placeOfBirthValue.text = state.personDetail.placeOfBirth
          alsoKnownAsValue.text = state.personDetail.alsoKnownAs.joinToString("\n")
        }
      }

      is PersonDetailUiState.Error -> {
        binding.scrollView.invisible()

        showErrorDialog { viewModel.loadData(personId) }
      }
    }
  }

  @SuppressLint("NewApi")
  private fun calculateAge(birthday: String, deathday: String): Int {
    if (birthday == "Unknown date") return 0

    val birthdayArr: List<Int> = birthday.split("/").map { it.toInt() }
    val birthdayDate: LocalDate = LocalDate.of(birthdayArr[2], birthdayArr[1], birthdayArr[0])

    val deathdayDate: LocalDate = if (deathday != "Unknown date") {
      val deathdayArr: List<Int> = deathday.split("/").map { it.toInt() }
      val deathDate: LocalDate = LocalDate.of(deathdayArr[2], deathdayArr[1], deathdayArr[0])
      deathDate
    } else {
      LocalDate.now()
    }

    return Period.between(birthdayDate, deathdayDate).years
  }
}