package com.example.prueba_tecnica.data.model

import androidx.room.Entity

@Entity(
    tableName = "favorite_characters",
    primaryKeys = ["userId", "characterId"]
)
data class FavoriteCharacter(
    val userId: Long,
    val characterId: Int,
    val name: String,
    val image: String
)