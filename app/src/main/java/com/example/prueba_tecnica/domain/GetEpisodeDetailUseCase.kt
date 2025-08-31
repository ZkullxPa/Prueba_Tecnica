package com.example.prueba_tecnica.domain

import com.example.prueba_tecnica.data.model.EpisodeDetail
import com.example.prueba_tecnica.data.repository.EpisodeDetailRepository
import javax.inject.Inject

class GetEpisodeDetailUseCase @Inject constructor(
    private val episodeDetailRepository: EpisodeDetailRepository
) {
    suspend operator fun invoke(episodeUrls: List<String>): Result<List<EpisodeDetail>>{
        return episodeDetailRepository.getEpisodesDetails(episodeUrls)
    }
}