package com.example.prueba_tecnica.domain

import com.example.prueba_tecnica.data.model.CharacterApiResponse
import com.example.prueba_tecnica.data.repository.CharactersInfoRepository
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val charactersInfoRepository: CharactersInfoRepository
) {
    suspend operator fun invoke(page: Int): Result<CharacterApiResponse>{
        return charactersInfoRepository.getAllCharacters(page)
    }
}