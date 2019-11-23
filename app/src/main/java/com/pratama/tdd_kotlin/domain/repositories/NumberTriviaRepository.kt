package com.pratama.tdd_kotlin.domain.repositories

import com.pratama.tdd_kotlin.core.data.Result
import com.pratama.tdd_kotlin.domain.entities.NumberTrivia

interface NumberTriviaRepository {
    suspend fun getRandomNumberTrivia(): Result<NumberTrivia>
    suspend fun getConcreteNumberTrivia(number: Int): Result<NumberTrivia>
}