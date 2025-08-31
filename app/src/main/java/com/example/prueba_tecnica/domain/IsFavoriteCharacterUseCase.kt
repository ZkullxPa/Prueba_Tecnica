package com.example.prueba_tecnica.domain

import com.example.prueba_tecnica.data.repository.FavoritesCharactersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IsFavoriteCharacterUseCase @Inject constructor(
    private val favoritesCharactersRepository: FavoritesCharactersRepository
){
    suspend operator fun invoke(userId: Long, characterId: Int): Flow<Boolean> {
        return favoritesCharactersRepository.isFavoriteFlow(userId, characterId)
    }
}