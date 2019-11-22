package com.pratama.tdd_kotlin.domain.usecases

import com.pratama.tdd_kotlin.core.error.Failure
import com.pratama.tdd_kotlin.core.functional.Either
import com.pratama.tdd_kotlin.core.usecase.UseCase
import com.pratama.tdd_kotlin.domain.entities.NumberTrivia
import com.pratama.tdd_kotlin.domain.repositories.NumberTriviaRepository

class GetRandomNumberTrivia(private val repository: NumberTriviaRepository) :
    UseCase<NumberTrivia, UseCase.None>() {

    override suspend fun execute(params: None): Either<Failure, NumberTrivia> {
        return repository.getRandomNumberTrivia()
    }

}
