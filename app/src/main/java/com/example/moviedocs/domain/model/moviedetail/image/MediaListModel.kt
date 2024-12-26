package com.example.moviedocs.domain.model.moviedetail.image


data class MediaListModel(
  val backdrops: List<MediaItemModel>,
  val id: Int, // 912649
  val logos: List<MediaItemModel>,
  val posters: List<MediaItemModel>
)