package com.example.moviedocs.domain.model.media


data class MediaListModel(
  val backdrops: List<MediaItemModel>,
  val id: Int, // 912649
  val logos: List<MediaItemModel>,
  val posters: List<MediaItemModel>
)