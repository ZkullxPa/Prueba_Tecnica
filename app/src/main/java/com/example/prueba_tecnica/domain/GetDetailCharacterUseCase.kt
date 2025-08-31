package com.example.prueba_tecnica.domain

import com.example.prueba_tecnica.data.model.Character
import com.example.prueba_tecnica.data.repository.CharacterDetailRepository
import javax.inject.Inject

class GetDetailCharacterUseCase @Inject constructor(
    private val characterDetailRepository: CharacterDetailRepository
) {
    suspend operator fun invoke(id: Int): Result<Character>{
        return characterDetailRepository.getCharacterDetail(id)
    }

}