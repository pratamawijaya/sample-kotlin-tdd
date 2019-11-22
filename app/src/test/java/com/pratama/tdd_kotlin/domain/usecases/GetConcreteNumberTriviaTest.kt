package com.pratama.tdd_kotlin.domain.usecases

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.pratama.tdd_kotlin.domain.repositories.NumberTriviaRepository
import com.pratama.tdd_kotlin.utils.ManagedCoroutineScope
import com.pratama.tdd_kotlin.utils.TestScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetConcreteNumberTriviaTest {
    private val testDispatcher = TestCoroutineDispatcher()
    private val managedCoroutineScope: ManagedCoroutineScope = TestScope(testDispatcher)

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
    fun should_get_data_from_repository() {
        runBlocking {
            getConcreteUseCase.execute(1)

            verify(repository).getConcreteNumberTrivia(1)
            verifyNoMoreInteractions(repository)

            // exect  . . .
        }
    }
}