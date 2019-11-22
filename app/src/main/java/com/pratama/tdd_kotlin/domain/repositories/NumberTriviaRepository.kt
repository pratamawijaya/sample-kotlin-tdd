package com.pratama.tdd_kotlin.domain.repositories

import com.pratama.tdd_kotlin.core.error.Failure
import com.pratama.tdd_kotlin.core.functional.Either
import com.pratama.tdd_kotlin.domain.entities.NumberTrivia

interface NumberTriviaRepository {
    suspend fun getRandomNumberTrivia(): Either<Failure, NumberTrivia>
    suspend fun getConcreteNumberTrivia(number: Int): Either<Failure, NumberTrivia>
}