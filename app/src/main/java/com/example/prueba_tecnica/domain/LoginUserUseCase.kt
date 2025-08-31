package com.example.prueba_tecnica.domain

import com.example.prueba_tecnica.data.model.User
import com.example.prueba_tecnica.data.repository.UserRepository
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<User> {
        return userRepository.login(email, password)
    }
}