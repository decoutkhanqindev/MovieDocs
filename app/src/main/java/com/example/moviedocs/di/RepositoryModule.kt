package com.example.moviedocs.di

import com.example.moviedocs.data.repository.company.CompanyRepositoryImpl
import com.example.moviedocs.data.repository.country.CountryRepositoryImpl
import com.example.moviedocs.data.repository.credits.CreditsRepositoryImpl
import com.example.moviedocs.data.repository.externalId.ExternalIdRepositoryImpl
import com.example.moviedocs.data.repository.genre.GenreRepositoryImpl
import com.example.moviedocs.data.repository.language.LanguageRepositoryImpl
import com.example.moviedocs.data.repository.media.MediaRepositoryImpl
import com.example.moviedocs.data.repository.moviedetail.MovieDetailRepositoryImpl
import com.example.moviedocs.data.repository.movielist.MovieListRepositoryImpl
import com.example.moviedocs.data.repository.person.PersonRepositoryImpl
import com.example.moviedocs.data.repository.slider.SliderRepositoryImpl
import com.example.moviedocs.domain.repository.company.CompanyRepository
import com.example.moviedocs.domain.repository.country.CountryRepository
import com.example.moviedocs.domain.repository.credits.CreditsRepository
import com.example.moviedocs.domain.repository.externalId.ExternalIdRepository
import com.example.moviedocs.domain.repository.genre.GenreRepository
import com.example.moviedocs.domain.repository.language.LanguageRepository
import com.example.moviedocs.domain.repository.media.MediaRepository
import com.example.moviedocs.domain.repository.moviedetail.MovieDetailRepository
import com.example.moviedocs.domain.repository.movielist.MovieListRepository
import com.example.moviedocs.domain.repository.person.PersonRepository
import com.example.moviedocs.domain.repository.slider.SliderRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
  @Binds
  fun bindSliderRepository(
    repository: SliderRepositoryImpl
  ): SliderRepository

  @Binds
  fun bindMovieListRepository(
    repository: MovieListRepositoryImpl
  ): MovieListRepository

  @Binds
  fun bindGenreRepository(
    repository: GenreRepositoryImpl
  ): GenreRepository

  @Binds
  fun bindMovieDetailRepository(
    repository: MovieDetailRepositoryImpl
  ): MovieDetailRepository

  @Binds
  fun bindCompanyRepository(
    repository: CompanyRepositoryImpl
  ): CompanyRepository

  @Binds
  fun bindCountryRepository(
    repository: CountryRepositoryImpl
  ): CountryRepository

  @Binds
  fun bindLanguageRepository(
    repository: LanguageRepositoryImpl
  ): LanguageRepository

  @Binds
  fun bindCreditsRepository(
    repository: CreditsRepositoryImpl
  ): CreditsRepository

  @Binds
  fun bindExternalIdRepository(
    repository: ExternalIdRepositoryImpl
  ): ExternalIdRepository

  @Binds
  fun bindPersonRepository(
    repository: PersonRepositoryImpl
  ): PersonRepository

  @Binds
  fun bindMediaRepository(
    repository: MediaRepositoryImpl
  ): MediaRepository
}