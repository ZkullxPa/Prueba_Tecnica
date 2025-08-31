package com.example.prueba_tecnica.data.remote

import com.example.prueba_tecnica.data.model.Character
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CharacterDetailService {
    @GET("character/{id}")
    suspend fun getCharacterDetail(@Path("id") id: Int): Character

}