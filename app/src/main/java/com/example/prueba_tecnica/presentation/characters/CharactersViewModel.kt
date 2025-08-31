package com.example.prueba_tecnica.presentation.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prueba_tecnica.domain.GetCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase
): ViewModel(){
    private val _charactersState = MutableStateFlow<CharactersState>(CharactersState.Loading)
    val charactersState: MutableStateFlow<CharactersState> = _charactersState
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing
    private var currentPage = 0
    private var totalPages = Int.MAX_VALUE
    private var isLoading = false
    init {
        loadCharacters(0)
    }
    fun loadCharacters(page: Int) {
        if (isLoading) return
        isLoading = true

        viewModelScope.launch {
            if (_charactersState.value !is CharactersState.Success) {
                _charactersState.value = CharactersState.Loading
            }
            val apiPage = (page).coerceAtLeast(1)
            val result = getCharactersUseCase(apiPage)
            result
                .onSuccess { response ->
                    currentPage = apiPage
                    totalPages = response.info.pages
                    val combined = when (val s = _charactersState.value) {
                        is CharactersState.Success -> {
                            val prev = s.data.results
                            prev + response.results
                        }
                        else -> response.results
                    }
                    _charactersState.value = CharactersState.Success(
                        response.copy(results = combined)
                    )
                    isLoading = false
                    _isRefreshing.value = false
                }
                .onFailure { e ->
                    _charactersState.value = CharactersState.Error(e.localizedMessage ?: "Error desconocido")
                    isLoading = false
                }
        }
    }
    fun loadNextPage() {
        if (currentPage >= totalPages) return
        loadCharacters(currentPage + 1)
    }
    fun refreshAll(){
        _isRefreshing.value = true
        isLoading = false
        loadCharacters(0)
    }
}
