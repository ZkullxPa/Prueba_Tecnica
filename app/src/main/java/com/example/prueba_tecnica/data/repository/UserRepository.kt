package com.example.prueba_tecnica.data.repository

import com.example.prueba_tecnica.data.local.dao.UserDao
import com.example.prueba_tecnica.data.model.User
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao
) {
    suspend fun register(email: String, password: String, name: String): Result<User> {
        return try {
            val newUser = User(
                email = email,
                password = password,
                name = name
            )
            val id = userDao.insert(newUser)
            val insertedUser = newUser.copy(id = id)
            Result.success(insertedUser)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun login(email: String, password: String): Result<User> = runCatching {
        userDao.login(email, password) ?: error("Credenciales inválidas")
    }
}