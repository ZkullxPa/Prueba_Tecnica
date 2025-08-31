package com.example.prueba_tecnica.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.prueba_tecnica.data.local.dao.FavoriteCharacterDao
import com.example.prueba_tecnica.data.local.dao.UserDao
import com.example.prueba_tecnica.data.model.FavoriteCharacter
import com.example.prueba_tecnica.data.model.User

@Database(entities = [User::class, FavoriteCharacter::class], version = 2, exportSchema = false)
abstract class AppDataBase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun favoriteCharacterDao(): FavoriteCharacterDao
}