package com.example.prueba_tecnica.presentation.characterDetail

import com.example.prueba_tecnica.data.model.Character

sealed class CharacterDetailState {
    object Loading : CharacterDetailState()
    data class Success(val character: Character) : CharacterDetailState()
    data class Error(val message: String) : CharacterDetailState()
}