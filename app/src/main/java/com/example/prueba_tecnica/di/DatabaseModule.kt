package com.example.prueba_tecnica.di

import android.content.Context
import androidx.room.Room
import com.example.prueba_tecnica.data.local.dao.FavoriteCharacterDao
import com.example.prueba_tecnica.data.local.dao.UserDao
import com.example.prueba_tecnica.data.local.database.AppDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDataBase {
        return Room.databaseBuilder(
            context,
            AppDataBase::class.java,
            "prueba_tecnica_db"
        ).fallbackToDestructiveMigration()
            .build()
    }
    @Provides
    @Singleton
    fun provideUserDao(appDataBase: AppDataBase): UserDao {
        return appDataBase.userDao()
    }

    @Provides
    @Singleton
    fun provideCharacterDao(appDataBase: AppDataBase): FavoriteCharacterDao {
        return appDataBase.favoriteCharacterDao()
    }
}