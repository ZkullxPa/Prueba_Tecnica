package com.example.prueba_tecnica.domain

import com.example.prueba_tecnica.data.model.FavoriteCharacter
import com.example.prueba_tecnica.data.repository.FavoritesCharactersRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetFavoriteCharacterUseCase @Inject constructor(
    private val favoritesCharactersRepository: FavoritesCharactersRepository
){
    suspend operator fun invoke(userId: Long): Flow<List<FavoriteCharacter>>{
        return favoritesCharactersRepository.getFavorites(userId)
    }

}