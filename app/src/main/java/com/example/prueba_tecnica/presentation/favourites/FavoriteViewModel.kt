package com.example.prueba_tecnica.presentation.favourites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prueba_tecnica.core.SharedPreference
import com.example.prueba_tecnica.domain.GetFavoriteCharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.prueba_tecnica.data.model.Character
import com.example.prueba_tecnica.data.model.FavoriteCharacter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getFavoriteCharacterUseCase: GetFavoriteCharacterUseCase,
    private val sharedPreference: SharedPreference
): ViewModel(){
    private val _favorites = MutableLiveData<List<FavoriteCharacter>>(emptyList())
    val favorites: LiveData<List<FavoriteCharacter>> = _favorites

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>(null)
    val error: LiveData<String?> = _error

    val currentUserId = sharedPreference.getUserId()!!.toLong()


    init {
        getAllFavorites()
    }

    fun getAllFavorites(){
        viewModelScope.launch {
            getFavoriteCharacterUseCase(currentUserId) // <- Flow<List<FavoriteCharacter>>
                .onStart { _loading.value = true; _error.value = null }
                .catch { e -> _error.value = e.localizedMessage ?: "Error desconocido" }
                .collect { list ->
                    _favorites.value = list
                    _loading.value = false
                }
        }
    }
}