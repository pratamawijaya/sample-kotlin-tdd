package com.pratama.tdd_kotlin.data.datasources.local

import com.pratama.tdd_kotlin.data.model.NumberTriviaModel

interface NumberTriviaLocalDatasource {
    fun getLastNumberTrivia(): NumberTriviaModel
    fun cacheNumberTrivia(numberTriviaModel: NumberTriviaModel)
}