package com.example.prueba_tecnica.data.repository

import com.example.prueba_tecnica.data.model.EpisodeDetail
import com.example.prueba_tecnica.data.remote.EpisodeInfoService
import javax.inject.Inject

class EpisodeDetailRepository @Inject constructor(
    private val episodeInfoService: EpisodeInfoService
) {
    suspend fun getEpisodesDetails(episodeUrls: List<String>): Result<List<EpisodeDetail>> = runCatching {
        val ids = extractEpisodeIds(episodeUrls)
        if (ids.isEmpty()) return@runCatching emptyList()
        if (ids.size == 1) listOf(episodeInfoService.getEpisode(ids.first()))
        else episodeInfoService.getEpisodes(ids.joinToString(","))
    }
    fun extractEpisodeIds(urls: List<String>): List<Int> =
        urls.mapNotNull { it.substringAfterLast("/").toIntOrNull() }

}