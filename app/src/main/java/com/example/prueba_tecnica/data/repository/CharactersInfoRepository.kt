package com.example.prueba_tecnica.data.repository

import android.util.Log
import com.example.prueba_tecnica.data.model.CharacterApiResponse
import com.example.prueba_tecnica.data.remote.CharactersInfoService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CharactersInfoRepository @Inject constructor(
    private val charactersInfoService: CharactersInfoService
) {
    suspend fun getAllCharacters(page: Int): Result<CharacterApiResponse> {
        return withContext(Dispatchers.IO){
            try {
                val response = charactersInfoService.getAllCharacters(page)
                Log.d("Personajes:", response.toString())
                Result.success(response)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}