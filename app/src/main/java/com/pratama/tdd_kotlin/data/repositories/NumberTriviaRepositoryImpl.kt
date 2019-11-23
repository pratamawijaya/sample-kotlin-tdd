package com.pratama.tdd_kotlin.data.repositories

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

    override suspend fun getRandomNumberTrivia(): Either<Failure, NumberTrivia> {
        return if (networkInfo.isConnected()) {
            Either.Right(mapper.map(remoteDatasource.getRandomNumberTrivia()))
        } else {
            Either.Right(mapper.map(localDatasource.getLastNumberTrivia()))
        }
    }

    override suspend fun getConcreteNumberTrivia(number: Int): Either<Failure, NumberTrivia> {
        return Either.Right(mapper.map(remoteDatasource.getConcreteNumberTrivia(number)))
    }
}