package com.example.prueba_tecnica.presentation.login

import com.example.prueba_tecnica.data.model.User

sealed class UserState {
    object Idle : UserState()
    object Loading : UserState()
    data class Success(val user: User) : UserState()
    data class Error(val message: String) : UserState()
}