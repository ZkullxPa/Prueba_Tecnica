package com.example.prueba_tecnica.presentation.characters

import com.example.prueba_tecnica.data.model.CharacterApiResponse

sealed class CharactersState {
    object Loading : CharactersState()
    data class Success(val data: CharacterApiResponse) : CharactersState()
    data class Error(val message: String) : CharactersState()
}