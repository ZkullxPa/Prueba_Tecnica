package com.example.prueba_tecnica.data.remote

import com.example.prueba_tecnica.data.model.EpisodeDetail
import retrofit2.http.GET
import retrofit2.http.Path

interface EpisodeInfoService {
    @GET("episode/{id}")
    suspend fun getEpisode(@Path("id") id: Int): EpisodeDetail

    @GET("episode/{ids}")
    suspend fun getEpisodes(@Path("ids", encoded = true) idsCsv: String): List<EpisodeDetail>
}