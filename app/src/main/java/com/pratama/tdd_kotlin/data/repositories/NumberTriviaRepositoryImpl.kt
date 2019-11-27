package com.pratama.tdd_kotlin.data.repositories

import com.pratama.tdd_kotlin.core.data.Result
import com.pratama.tdd_kotlin.core.error.Failure
import com.pratama.tdd_kotlin.core.functional.Either
import com.pratama.tdd_kotlin.core.network.NetworkInfo
import com.pratama.tdd_kotlin.data.datasources.local.NumberTriviaLocalDatasource
import com.pratama.tdd_kotlin.data.datasources.remote.NumberTriviaRemoteDatasource
import com.pratama.tdd_kotlin.data.mapper.NumberTriviaMapper
import com.pratama.tdd_kotlin.domain.entities.NumberTrivia
import com.pratama.tdd_kotlin.domain.repositories.NumberTriviaRepository

class NumberTriviaRepositoryImpl(
    val networkInfo: NetworkInfo,
    val mapper: NumberTriviaMapper,
    val localDatasource: NumberTriviaLocalDatasource,
    val remoteDatasource: NumberTriviaRemoteDatasource
) : NumberTriviaRepository {

    override suspend fun getRandomNumberTrivia(): Result<NumberTrivia> {
        return if (networkInfo.isConnected()) {
            try {
                Result.Success(mapper.map(remoteDatasource.getRandomNumberTrivia()))
            } catch (e: Exception) {
                Result.Error(Failure.ServerError)
            }
        } else {
            Result.Success(mapper.map(localDatasource.getLastNumberTrivia()))
        }
    }

    override suspend fun getConcreteNumberTrivia(number: Int): Result<NumberTrivia> {
        return if (networkInfo.isConnected()) {
            try {
                Result.Success(
                    mapper.map(remoteDatasource.getConcreteNumberTrivia(number))
                )
            } catch (e: Exception) {
                Result.Error(Failure.ServerError)
            }
        } else {
            Result.Success(
                mapper.map(localDatasource.getLastNumberTrivia())
            )
        }
    }
}