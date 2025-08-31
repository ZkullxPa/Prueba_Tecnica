package com.example.prueba_tecnica.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prueba_tecnica.core.SharedPreference
import com.example.prueba_tecnica.domain.LoginUserUseCase
import com.example.prueba_tecnica.domain.RegisterUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val registerUser: RegisterUserUseCase,
    private val loginUser: LoginUserUseCase,
    private val sharedPreference: SharedPreference
) : ViewModel() {
    private val _state = MutableStateFlow<UserState>(UserState.Idle)
    val state: StateFlow<UserState> = _state

    fun register(email: String, password: String, name: String?) {
        viewModelScope.launch {
            _state.value = UserState.Loading
            if (name != null) {
                registerUser(email.trim(), password, name)
                    .onSuccess { user -> _state.value = UserState.Success(user) }
                    .onFailure { e -> _state.value = UserState.Error(e.message ?: "Error al registrar") }
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _state.value = UserState.Loading
            loginUser(email.trim(), password)
                .onSuccess { user ->
                    _state.value = UserState.Success(user)
                    sharedPreference.saveUserId(user.id.toString())
                }
                .onFailure { e ->
                    _state.value = UserState.Error(e.message ?: "Credenciales inválidas")
                }
        }
    }

}