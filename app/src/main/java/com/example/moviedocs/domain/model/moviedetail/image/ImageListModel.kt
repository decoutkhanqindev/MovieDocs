package com.example.moviedocs.domain.model.moviedetail.image


data class ImageListModel(
  val backdrops: List<ImageItemModel>,
  val id: Int, // 912649
  val logos: List<ImageItemModel>,
  val posters: List<ImageItemModel>
)