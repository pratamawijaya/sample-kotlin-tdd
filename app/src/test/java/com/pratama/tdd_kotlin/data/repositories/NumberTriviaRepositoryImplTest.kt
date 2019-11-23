package com.pratama.tdd_kotlin.data.repositories

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.pratama.tdd_kotlin.core.functional.Either
import com.pratama.tdd_kotlin.core.network.NetworkInfo
import com.pratama.tdd_kotlin.data.datasources.local.NumberTriviaLocalDatasource
import com.pratama.tdd_kotlin.data.datasources.remote.NumberTriviaRemoteDatasource
import com.pratama.tdd_kotlin.data.mapper.NumberTriviaMapper
import com.pratama.tdd_kotlin.data.model.NumberTriviaModel
import com.pratama.tdd_kotlin.domain.entities.NumberTrivia
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

    @Test
    fun `getConcreteNumber should return data when remote data is success`() = runBlocking {

        val numberTriviaModel = NumberTriviaModel(1, "test")
        val numberTriviaDomain = mapper.map(numberTriviaModel)

        whenever(remoteDatasource.getConcreteNumberTrivia(1))
            .thenReturn(numberTriviaModel)

        val result = repository.getConcreteNumberTrivia(1)

        // assert mapper success
        assertEquals(numberTriviaModel.number, numberTriviaDomain.number)

        verify(remoteDatasource).getConcreteNumberTrivia(1)

        assertEquals(result, Either.Right(numberTriviaDomain))
    }
}
