package com.pratama.tdd_kotlin.domain.usecases

import com.pratama.tdd_kotlin.core.data.Result
import com.pratama.tdd_kotlin.domain.entities.NumberTrivia
import com.pratama.tdd_kotlin.domain.repositories.NumberTriviaRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class GetConcreteNumberTriviaTest {
    private val testDispatcher = TestCoroutineDispatcher()

    private val repository: NumberTriviaRepository = mockk()
    private val getConcreteUseCase: GetConcreteNumberTrivia = GetConcreteNumberTrivia(repository)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getConcreteNumberTrivia should return NumberTrivia`() = runBlocking {
        val testNumber = 1
        val testNumberTrivia = NumberTrivia(number = testNumber, text = "Test Text")

        every {
            runBlocking {
                repository.getConcreteNumberTrivia(testNumber)
            }
        } returns Result.Success(testNumberTrivia)

        val result = getConcreteUseCase.execute(testNumber)

        verify { runBlocking { repository.getConcreteNumberTrivia(testNumber) } }

        assertEquals(result, Result.Success(testNumberTrivia))
    }
}