package com.example.prueba_tecnica.data.repository

import com.example.prueba_tecnica.data.local.dao.FavoriteCharacterDao
import com.example.prueba_tecnica.data.model.FavoriteCharacter
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoritesCharactersRepository @Inject constructor(
    private val favoritesCharactersDao: FavoriteCharacterDao
) {
    suspend fun addFavorite(userId: Long, characterId: Int, name: String, image: String) {
        favoritesCharactersDao.upsert(FavoriteCharacter(userId, characterId, name, image))
    }

    suspend fun removeFavorite(userId: Long, characterId: Int) {
        favoritesCharactersDao.delete(userId, characterId)
    }

    fun isFavoriteFlow(userId: Long, characterId: Int): Flow<Boolean> =
        favoritesCharactersDao.isFavorite(userId, characterId)

    suspend fun isFavorite(userId: Long, characterId: Int): Boolean =
        favoritesCharactersDao.count(userId, characterId) > 0

    fun getFavorites(userId: Long): Flow<List<FavoriteCharacter>> =
        favoritesCharactersDao.getFavorites(userId)
}