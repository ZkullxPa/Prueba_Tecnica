package com.example.prueba_tecnica.data.model

import com.google.gson.annotations.SerializedName

data class CharacterOrigin(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
)
