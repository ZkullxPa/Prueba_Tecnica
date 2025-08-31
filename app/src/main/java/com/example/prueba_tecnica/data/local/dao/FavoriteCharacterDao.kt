package com.example.prueba_tecnica.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.prueba_tecnica.data.model.FavoriteCharacter
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteCharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: FavoriteCharacter)

    @Query("DELETE FROM favorite_characters WHERE userId = :userId AND characterId = :characterId")
    suspend fun delete(userId: Long, characterId: Int)

    @Query("SELECT COUNT(*) FROM favorite_characters WHERE userId = :userId AND characterId = :characterId")
    suspend fun count(userId: Long, characterId: Int): Int

    @Query("SELECT * FROM favorite_characters WHERE userId = :userId ORDER BY name ASC")
    fun getFavorites(userId: Long): Flow<List<FavoriteCharacter>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_characters WHERE userId = :userId AND characterId = :characterId)")
    fun isFavorite(userId: Long, characterId: Int): Flow<Boolean>
}