package com.example.prueba_tecnica.data.remote

import com.example.prueba_tecnica.data.model.CharacterApiResponse
import okhttp3.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface CharactersInfoService {

    @GET("character/")
    suspend fun getAllCharacters(
        @Query("page") page: Int,
    ): CharacterApiResponse

}