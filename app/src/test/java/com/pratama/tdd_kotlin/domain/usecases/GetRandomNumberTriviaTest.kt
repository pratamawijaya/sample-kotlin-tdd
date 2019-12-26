package com.pratama.tdd_kotlin.domain.usecases

import com.pratama.tdd_kotlin.core.data.Result
import com.pratama.tdd_kotlin.core.usecase.UseCase
import com.pratama.tdd_kotlin.domain.entities.NumberTrivia
import com.pratama.tdd_kotlin.domain.repositories.NumberTriviaRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class GetRandomNumberTriviaTest {

    private val testDispatcher = TestCoroutineDispatcher()
    private val repository: NumberTriviaRepository = mockk()
    private val getRandomNumberTrivia = GetRandomNumberTrivia(repository)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `getRandomNumberTrivia should return NumberTrivia`() = runBlocking {
        val testNumber = 1
        val testNumberTrivia = NumberTrivia(number = testNumber, text = "Test")

        every {
            runBlocking {
                repository.getRandomNumberTrivia()
            }
        } returns Result.Success(
            testNumberTrivia
        )

        val result = getRandomNumberTrivia.execute(UseCase.None())

        assertEquals(result, Result.Success(testNumberTrivia))

    }
}
