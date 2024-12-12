package com.example.moviedocs.data.mapper.externalId

import com.example.moviedocs.data.remote.externalId.ExternalIdsResponse
import com.example.moviedocs.domain.externalId.ExternalIdsModel

fun ExternalIdsResponse.toExternalIdsModel(): ExternalIdsModel =
  ExternalIdsModel(
    id = id,
    facebookId = facebookId?.takeIf { it.isNotEmpty() } ?: "Not found Facebook id",
    instagramId = instagramId?.takeIf { it.isNotEmpty() } ?: "Not found Instagram id",
    twitterId = twitterId?.takeIf { it.isNotEmpty() } ?: "Not found Twitter id",
    tiktokId = tiktokId?.takeIf { it.isEmpty() } ?: "Not found TikTok id"
  )