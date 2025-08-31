package com.example.prueba_tecnica.domain

import com.example.prueba_tecnica.data.repository.FavoritesCharactersRepository
import javax.inject.Inject

class AddFavoriteCharacterUseCase @Inject constructor(
    private val favoritesCharactersRepository: FavoritesCharactersRepository
) {
    suspend operator fun invoke(userId: Long, characterId: Int, name: String, image: String){
        return favoritesCharactersRepository.addFavorite(userId, characterId, name, image)
    }
}