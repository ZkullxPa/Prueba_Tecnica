package com.example.prueba_tecnica.domain

import com.example.prueba_tecnica.data.repository.FavoritesCharactersRepository
import javax.inject.Inject

class RemoveFavoriteCharacterUseCase @Inject constructor(
    private val favoritesCharactersRepository: FavoritesCharactersRepository
) {
    suspend operator fun invoke(userId: Long, characterId: Int){
        return favoritesCharactersRepository.removeFavorite(userId, characterId)
    }
}