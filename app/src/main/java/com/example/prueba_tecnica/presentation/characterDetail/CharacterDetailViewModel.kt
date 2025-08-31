package com.example.prueba_tecnica.presentation.characterDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prueba_tecnica.core.SharedPreference
import com.example.prueba_tecnica.data.model.EpisodeDetail
import com.example.prueba_tecnica.domain.AddFavoriteCharacterUseCase
import com.example.prueba_tecnica.domain.GetDetailCharacterUseCase
import com.example.prueba_tecnica.domain.GetEpisodeDetailUseCase
import com.example.prueba_tecnica.domain.IsFavoriteCharacterUseCase
import com.example.prueba_tecnica.domain.RemoveFavoriteCharacterUseCase
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random


@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val characterDetailUseCase: GetDetailCharacterUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val episodesUseCase: GetEpisodeDetailUseCase,
    private val isFavoriteUseCase: IsFavoriteCharacterUseCase,
    private val addFavoriteUseCase: AddFavoriteCharacterUseCase,
    private val removeFavoriteUseCase: RemoveFavoriteCharacterUseCase,
    private val sharedPreference: SharedPreference
): ViewModel() {
    private val _uiState = MutableStateFlow<CharacterDetailState>(CharacterDetailState.Loading)
    val uiState: StateFlow<CharacterDetailState> = _uiState

    private var lastLoadedId: Int? = null
    private var isLoading = false
    val idCharacter: Int? = savedStateHandle["id"]
    private val _episodes = MutableStateFlow<List<EpisodeDetail>>(emptyList())
    val episodes: StateFlow<List<EpisodeDetail>> = _episodes
    private val _episodesError = MutableStateFlow<String?>(null)
    val episodesError: StateFlow<String?> = _episodesError
    private val _lastLocation = MutableStateFlow<LatLng?>(null)
    val lastLocation: StateFlow<LatLng?> = _lastLocation
    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite
    val currentUserId = sharedPreference.getUserId()!!.toLong()
    private val _watched = MutableStateFlow<Set<Int>>(emptySet())
    val watched: StateFlow<Set<Int>> = _watched


    init {
        _watched.value = sharedPreference.getWatchedEpisodes(currentUserId, idCharacter)
        getDetailCharacter(idCharacter!!)
        observeFavorite(currentUserId, idCharacter)
    }
    fun getDetailCharacter(id: Int) {
        if (isLoading || lastLoadedId == id) return
        isLoading = true
        lastLoadedId = id

        _uiState.value = CharacterDetailState.Loading

        viewModelScope.launch {
            characterDetailUseCase(id)
                .onSuccess { character ->
                    _uiState.value = CharacterDetailState.Success(character)
                    isLoading = false
                    _uiState.value = CharacterDetailState.Success(character)
                    updateSimulatedLocationFor(character.id)
                    episodesUseCase(character.episode)
                        .onSuccess { episde ->
                            _episodes.value = episde
                        }
                        .onFailure { e ->
                            _episodesError.value = e.localizedMessage ?: "Error al cargar episodios"
                        }
                }
                .onFailure { e ->
                    _uiState.value = CharacterDetailState.Error(
                        e.localizedMessage ?: "Error al cargar detalle"
                    )
                    isLoading = false
                }
        }
    }
    fun retry() {
        getDetailCharacter(idCharacter!!)
    }
    private fun simulateLocation(id: Int): LatLng {
        val baseLat = 19.4326
        val baseLng = -99.1332
        val r = Random(id)
        val lat = baseLat + (r.nextDouble(-0.05, 0.05))
        val lng = baseLng + (r.nextDouble(-0.05, 0.05))
        return LatLng(lat, lng)
    }
    private fun updateSimulatedLocationFor(id: Int) {
        _lastLocation.value = simulateLocation(id)
    }
    private fun observeFavorite(userId: Long, characterId: Int) {
        viewModelScope.launch {
            isFavoriteUseCase(userId, characterId).collect { fav ->
                _isFavorite.value = fav
            }
        }
    }

    fun toggleFavorite() {
        val s = _uiState.value
        if (s !is CharacterDetailState.Success) return
        val c = s.character
        viewModelScope.launch {
            val newVal = !_isFavorite.value
            _isFavorite.value = newVal
            runCatching {
                if (newVal) {
                    addFavoriteUseCase(currentUserId, c.id, c.name, c.image)
                } else {
                    removeFavoriteUseCase(currentUserId, c.id)
                }
            }.onFailure {
                _isFavorite.value = !newVal
            }
        }
    }

    fun isEpisodeWatched(episodeId: Int): Boolean = _watched.value.contains(episodeId)

    fun toggleEpisodeWatched(episodeId: Int) {
        val episode = _watched.value
        val newSet = if (episodeId in episode) episode - episodeId else episode + episodeId
        _watched.value = newSet
        viewModelScope.launch {
            if (episodeId in episode) {
                sharedPreference.removeWatchedEpisode(currentUserId, idCharacter, episodeId)
            } else {
                sharedPreference.addWatchedEpisode(currentUserId, idCharacter, episodeId)
            }
        }
    }

}