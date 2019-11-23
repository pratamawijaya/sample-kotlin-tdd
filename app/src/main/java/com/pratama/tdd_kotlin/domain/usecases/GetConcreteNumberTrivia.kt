package com.pratama.tdd_kotlin.domain.usecases

import com.pratama.tdd_kotlin.core.data.Result
import com.pratama.tdd_kotlin.core.usecase.UseCase
import com.pratama.tdd_kotlin.domain.entities.NumberTrivia
import com.pratama.tdd_kotlin.domain.repositories.NumberTriviaRepository

class GetConcreteNumberTrivia(private val repository: NumberTriviaRepository) :
    UseCase<NumberTrivia, Int>() {

    override suspend fun execute(params: Int): Result<NumberTrivia> {
        return repository.getConcreteNumberTrivia(params)
    }
}