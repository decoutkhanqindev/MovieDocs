package com.example.moviedocs.data.mapper.externalId

import com.example.moviedocs.data.remote.externalId.ExternalIdsResponse
import com.example.moviedocs.domain.externalId.ExternalIdsModel

fun ExternalIdsResponse.toExternalIdsModel(): ExternalIdsModel =
  ExternalIdsModel(
    id = id,
    facebookId = facebookId?.takeIf { it.isNotEmpty() } ?: "",
    instagramId = instagramId?.takeIf { it.isNotEmpty() } ?: "",
    twitterId = twitterId?.takeIf { it.isNotEmpty() } ?: "",
    tiktokId = tiktokId?.takeIf { it.isEmpty() } ?: ""
  )