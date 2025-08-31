package com.example.prueba_tecnica.domain

import com.example.prueba_tecnica.data.model.*
import com.example.prueba_tecnica.data.repository.CharactersInfoRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetCharactersUseCaseTest {

    private lateinit var repository: CharactersInfoRepository
    private lateinit var useCase: GetCharactersUseCase

    @Before
    fun setUp() {
        repository = mockk(relaxed = true)
        useCase = GetCharactersUseCase(repository)
    }

    @Test
    fun returnsSuccessWhenRepositoryReturnsCharacters() = runTest {
        // Arrange
        val page = 2
        val response = CharacterApiResponse(
            info = Info(
                count = 826,
                pages = 42,
                next = "https://rickandmortyapi.com/api/character?page=3",
                prev = "https://rickandmortyapi.com/api/character?page=1"
            ),
            results = listOf(
                Character(
                    id = 1,
                    name = "Rick Sanchez",
                    status = "Alive",
                    species = "Human",
                    type = "",
                    gender = "Male",
                    characterOrigin = CharacterOrigin("Earth (C-137)", ""),
                    location = CharacterLocation("Citadel of Ricks", ""),
                    image = "https://...",
                    episode = listOf("https://.../1"),
                    url = "https://...",
                    created = "2017-11-04T18:48:46.250Z"
                )
            )
        )
        coEvery { repository.getAllCharacters(page) } returns Result.success(response)

        // Act
        val result = useCase(page)

        // Assert
        assertTrue(result.isSuccess)
        assertEquals(response, result.getOrNull())
        coVerify(exactly = 1) { repository.getAllCharacters(page) }
    }

    @Test
    fun returnsFailureWhenRepositoryThrowsException() = runTest {
        // Arrange
        val page = 5
        val ex = RuntimeException("Network boom")
        coEvery { repository.getAllCharacters(page) } returns Result.failure(ex)

        // Act
        val result = useCase(page)

        // Assert
        assertTrue(result.isFailure)
        assertEquals(ex, result.exceptionOrNull())
        coVerify(exactly = 1) { repository.getAllCharacters(page) }
    }

    @Test
    fun forwardsPageValueToRepository() = runTest {
        // Arrange
        val page = 10
        val emptyResponse = CharacterApiResponse(
            info = Info(count = 0, pages = 0, next = "", prev = ""),
            results = emptyList()
        )
        coEvery { repository.getAllCharacters(page) } returns Result.success(emptyResponse)

        // Act
        val result = useCase(page)

        // Assert
        assertTrue(result.isSuccess)
        coVerify(exactly = 1) { repository.getAllCharacters(page) }
    }
}
