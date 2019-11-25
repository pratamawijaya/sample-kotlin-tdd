package com.pratama.tdd_kotlin.data.repositories

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import com.pratama.tdd_kotlin.core.data.Result
import com.pratama.tdd_kotlin.core.error.Failure
import com.pratama.tdd_kotlin.core.functional.Either
import com.pratama.tdd_kotlin.core.network.NetworkInfo
import com.pratama.tdd_kotlin.data.datasources.local.NumberTriviaLocalDatasource
import com.pratama.tdd_kotlin.data.datasources.remote.NumberTriviaRemoteDatasource
import com.pratama.tdd_kotlin.data.mapper.NumberTriviaMapper
import com.pratama.tdd_kotlin.data.model.NumberTriviaModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NumberTriviaRepositoryImplTest {

    private val testDispatcher = TestCoroutineDispatcher()

    private lateinit var repository: NumberTriviaRepositoryImpl

    @Mock
    private lateinit var networkInfo: NetworkInfo
    @Mock
    private lateinit var localDatasource: NumberTriviaLocalDatasource
    @Mock
    private lateinit var remoteDatasource: NumberTriviaRemoteDatasource
    private lateinit var mapper: NumberTriviaMapper

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        mapper = NumberTriviaMapper()

        repository = NumberTriviaRepositoryImpl(
            networkInfo = networkInfo,
            localDatasource = localDatasource,
            remoteDatasource = remoteDatasource,
            mapper = mapper
        )
    }

    @Test(expected = java.lang.Exception::class)
    fun `ifOnline getConcreteNumber should throw error when remote data failure`() = runBlocking {
        whenever(networkInfo.isConnected()).thenReturn(true)
        whenever(remoteDatasource.getConcreteNumberTrivia(1)).thenThrow(Exception())

        val result = repository.getConcreteNumberTrivia(1)
    }

    @Test
    fun `ifOnline getConcreteNumber should return data when remote data is success`() =
        runBlocking {

            val numberTriviaModel = NumberTriviaModel(1, "test")
            val numberTriviaDomain = mapper.map(numberTriviaModel)

            whenever(networkInfo.isConnected()).thenReturn(true)
            whenever(remoteDatasource.getConcreteNumberTrivia(1))
                .thenReturn(numberTriviaModel)

            val result = repository.getConcreteNumberTrivia(1)

            // assert mapper success
            assertEquals(numberTriviaModel.number, numberTriviaDomain.number)

            verify(remoteDatasource).getConcreteNumberTrivia(1)

            assertEquals(result, Result.Success(numberTriviaDomain))
        }

    @Test
    fun `ifOnline getRandomNumber  should return data when remote data is success`() = runBlocking {
        val numberTriviaModel = NumberTriviaModel(1, "test")
        val numberTrivia = mapper.map(numberTriviaModel)

        whenever(networkInfo.isConnected()).thenReturn(true)
        whenever(remoteDatasource.getRandomNumberTrivia()).thenReturn(numberTriviaModel)

        val result = repository.getRandomNumberTrivia()

        assertEquals(result, Result.Success(numberTrivia))
    }

    @Test
    fun `ifOffline getRandomNumber should return data from local`() = runBlocking {
        val numberTriviaModel = NumberTriviaModel(1, "test")
        val numberTrivia = mapper.map(numberTriviaModel)


        whenever(networkInfo.isConnected()).thenReturn(false)
        whenever(localDatasource.getLastNumberTrivia()).thenReturn(numberTriviaModel)

        val result = repository.getRandomNumberTrivia()

        assertEquals(result, Result.Success(numberTrivia))
    }

    @Test
    fun `ifOffline getConcreteNumber should return data from local`() = runBlocking {
        val numberTriviaModel = NumberTriviaModel(1, "test")
        val numberTrivia = mapper.map(numberTriviaModel)

        whenever(networkInfo.isConnected()).thenReturn(false)

        whenever(localDatasource.getLastNumberTrivia()).thenReturn(numberTriviaModel)

        val result = repository.getConcreteNumberTrivia(1)

        assertEquals(result, Result.Success(numberTrivia))
    }
}
