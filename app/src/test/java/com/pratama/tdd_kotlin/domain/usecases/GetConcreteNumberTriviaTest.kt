package com.pratama.tdd_kotlin.domain.usecases

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import com.pratama.tdd_kotlin.core.functional.Either
import com.pratama.tdd_kotlin.domain.entities.NumberTrivia
import com.pratama.tdd_kotlin.domain.repositories.NumberTriviaRepository
import com.pratama.tdd_kotlin.utils.ManagedCoroutineScope
import com.pratama.tdd_kotlin.utils.TestScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetConcreteNumberTriviaTest {
    private val testDispatcher = TestCoroutineDispatcher()

    private lateinit var getConcreteUseCase: GetConcreteNumberTrivia
    @Mock
    private lateinit var repository: NumberTriviaRepository

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getConcreteUseCase = GetConcreteNumberTrivia(repository = repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getConcreteNumberTrivia should return NumberTrivia`() {
        val testNumber = 1
        val testNumberTrivia = NumberTrivia(number = testNumber, text = "Test Text")
        runBlocking {

            whenever(repository.getConcreteNumberTrivia(testNumber)).thenReturn(
                Either.Right(
                    testNumberTrivia
                )
            )

            val result = getConcreteUseCase.execute(testNumber)

            verify(repository).getConcreteNumberTrivia(1)
            verifyNoMoreInteractions(repository)

            assertEquals(result, Either.Right(testNumberTrivia))

        }
    }
}