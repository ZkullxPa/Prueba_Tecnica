package com.example.prueba_tecnica.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.prueba_tecnica.data.local.dao.FavoriteCharacterDao
import com.example.prueba_tecnica.data.local.database.AppDataBase
import com.example.prueba_tecnica.data.model.FavoriteCharacter
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FavoritesDaoInstrumentedTest {

    private lateinit var db: AppDataBase
    private lateinit var dao: FavoriteCharacterDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDataBase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.favoriteCharacterDao()
    }

    @Test
    fun insertFavorite_savesIt() = runBlocking {
        val fav = FavoriteCharacter(1, 123, "Rick", "image.png")
        dao.upsert(fav)

        val all = dao.getFavorites(1).first()
        assertTrue(all.any { it.characterId == 123 })
    }

    @After
    fun tearDown() {
        db.close()
    }
}
