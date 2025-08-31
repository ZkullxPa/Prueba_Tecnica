package com.example.prueba_tecnica.data.repository

import com.example.prueba_tecnica.data.model.Character
import com.example.prueba_tecnica.data.remote.CharacterDetailService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CharacterDetailRepository @Inject constructor(
    private val characterDetailService: CharacterDetailService
) {
    suspend fun getCharacterDetail(id: Int): Result<Character> {
        return withContext(Dispatchers.IO){
            try {
                Result.success(characterDetailService.getCharacterDetail(id))
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}