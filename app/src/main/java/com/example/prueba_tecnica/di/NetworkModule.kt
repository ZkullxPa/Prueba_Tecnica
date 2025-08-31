package com.example.prueba_tecnica.di

import android.content.Context
import com.example.prueba_tecnica.core.SharedPreference
import com.example.prueba_tecnica.data.remote.CharacterDetailService
import com.example.prueba_tecnica.data.remote.CharactersInfoService
import com.example.prueba_tecnica.data.remote.EpisodeInfoService
import com.example.prueba_tecnica.data.repository.CharacterDetailRepository
import com.example.prueba_tecnica.data.repository.CharactersInfoRepository
import com.example.prueba_tecnica.data.repository.EpisodeDetailRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    //Val para peticiones
    val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()
    /*TODO RETROFIT*/
    @Provides
    @Singleton
    @Named("api")
    fun provideRickAndMortyApi(): String = "https://rickandmortyapi.com/api/"

    @Provides
    @Singleton
    @Named("apiretrofit")
    fun getRetrofit(@Named("api") url: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    /*TODO RICK AND MORTY API*/
    @Provides
    @Singleton
    fun characterApi(@Named("apiretrofit") retrofit: Retrofit): CharactersInfoService {
        return retrofit.create(CharactersInfoService::class.java)
    }

    @Provides
    @Singleton
    fun detailCharacterApi(@Named("apiretrofit") retrofit: Retrofit): CharacterDetailService {
        return retrofit.create(CharacterDetailService::class.java)
    }

    @Provides
    @Singleton
    fun episodeDetailApi(@Named("apiretrofit") retrofit: Retrofit): EpisodeInfoService {
        return retrofit.create(EpisodeInfoService::class.java)
    }

    /*TODO RICK AND MORTY PROVIDERS*/
    @Provides
    @Singleton
    fun provideCharacterApi(charactersInfoService: CharactersInfoService): CharactersInfoRepository {
        return CharactersInfoRepository(charactersInfoService)
    }

    @Provides
    @Singleton
    fun provideDetailCharacterApi(characterDetailService: CharacterDetailService): CharacterDetailRepository {
        return CharacterDetailRepository(characterDetailService)
    }

    @Provides
    @Singleton
    fun provideEpisodeDetailApi(episodeInfoService: EpisodeInfoService): EpisodeDetailRepository {
        return EpisodeDetailRepository(episodeInfoService)
    }

    @Provides
    @Singleton
    fun providePreferenceManager(@ApplicationContext context: Context): SharedPreference {
        return SharedPreference(context)
    }

}