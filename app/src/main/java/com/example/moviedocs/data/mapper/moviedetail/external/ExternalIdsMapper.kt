package com.example.moviedocs.data.mapper.moviedetail.external

import com.example.moviedocs.data.remote.response.moviedetails.external.ExternalIdsResponse
import com.example.moviedocs.domain.model.moviedetail.external.ExternalIdsModel

fun ExternalIdsResponse.toExternalIdsModel(): ExternalIdsModel =
  ExternalIdsModel(
    id = id,
    facebookId = facebookId ?: "Not found FB id",
    instagramId = instagramId ?: "Not found IG id",
    twitterId = twitterId ?: "Not found Twitter id",
  )