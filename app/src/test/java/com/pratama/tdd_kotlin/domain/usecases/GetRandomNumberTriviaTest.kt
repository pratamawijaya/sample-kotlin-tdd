package com.pratama.tdd_kotlin.domain.usecases

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.pratama.tdd_kotlin.core.functional.Either
import com.pratama.tdd_kotlin.core.usecase.UseCase
import com.pratama.tdd_kotlin.domain.entities.NumberTrivia
import com.pratama.tdd_kotlin.domain.repositories.NumberTriviaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetRandomNumberTriviaTest {
    private val testDispatcher = TestCoroutineDispatcher()

    private lateinit var getRandomNumberTrivia :GetRandomNumberTrivia

    @Mock private lateinit var repository: NumberTriviaRepository

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getRandomNumberTrivia = GetRandomNumberTrivia(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getRandomNumberTrivia should return NumberTrivia`() {
        val testNumber = 1
        val testNumberTrivia = NumberTrivia(number = testNumber, text = "Test Text")

        runBlocking {
            whenever(repository.getRandomNumberTrivia()).thenReturn(Either.Right(testNumberTrivia))

            val result = getRandomNumberTrivia.execute(UseCase.None())

            verify(repository).getRandomNumberTrivia()

            assertEquals(result, Either.Right(testNumberTrivia))
        }
    }
}