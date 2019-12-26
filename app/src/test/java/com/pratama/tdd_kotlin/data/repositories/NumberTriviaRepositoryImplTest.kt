package com.pratama.tdd_kotlin.data.repositories

import com.pratama.tdd_kotlin.core.data.Result
import com.pratama.tdd_kotlin.core.network.NetworkInfo
import com.pratama.tdd_kotlin.data.datasources.local.NumberTriviaLocalDatasource
import com.pratama.tdd_kotlin.data.datasources.remote.NumberTriviaRemoteDatasource
import com.pratama.tdd_kotlin.data.mapper.NumberTriviaMapper
import com.pratama.tdd_kotlin.data.model.NumberTriviaModel
import com.pratama.tdd_kotlin.domain.entities.NumberTrivia
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class NumberTriviaRepositoryImplTest {


    private val testDispatcher = TestCoroutineDispatcher()

    val localDatasource: NumberTriviaLocalDatasource = mockk()
    val remoteDatasource: NumberTriviaRemoteDatasource = mockk()
    val networkInfo: NetworkInfo = mockk()
    val mapper = NumberTriviaMapper()

    val repo = NumberTriviaRepositoryImpl(networkInfo, mapper, localDatasource, remoteDatasource)


    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    fun getNumberTriviaModel(): NumberTriviaModel {
        return NumberTriviaModel(1, "test")
    }

    fun getNumberTriviaDomain(): NumberTrivia {
        return mapper.map(getNumberTriviaModel())
    }

    @Test
    fun `ifOnline getConcreteNumber should return from remote data`() =
        runBlocking {
            val numberTriviaModel = getNumberTriviaModel()
            val numberTriviaDomain = getNumberTriviaDomain()

            every { networkInfo.isConnected() } returns true
            every { remoteDatasource.getConcreteNumberTrivia(1) } returns numberTriviaModel

            val result = repo.getConcreteNumberTrivia(1)

            verify { remoteDatasource.getConcreteNumberTrivia(1) }

            assertEquals(numberTriviaModel.number, numberTriviaDomain.number)
            assertEquals(result, Result.Success(numberTriviaDomain))

        }

    @Test
    fun `ifOnline getRandomNumber should return from remote data`() = runBlocking {
        every { networkInfo.isConnected() } returns true
        every { remoteDatasource.getRandomNumberTrivia() } returns getNumberTriviaModel()

        val result = repo.getRandomNumberTrivia()

        verify { remoteDatasource.getRandomNumberTrivia() }

        assertEquals(result, Result.Success(getNumberTriviaDomain()))
    }
}
