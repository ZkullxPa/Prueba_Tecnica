package com.example.prueba_tecnica.data.model

import com.google.gson.annotations.SerializedName

data class EpisodeApiResponse(
    @SerializedName("info") val info: Info,
    @SerializedName("results") val results: List<EpisodeDetail>
)