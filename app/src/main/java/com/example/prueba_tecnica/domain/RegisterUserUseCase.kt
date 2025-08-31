package com.example.prueba_tecnica.domain

import com.example.prueba_tecnica.data.model.User
import com.example.prueba_tecnica.data.repository.UserRepository
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(email: String, password: String, name: String): Result<User> {
        return userRepository.register(email, password, name)
    }
}