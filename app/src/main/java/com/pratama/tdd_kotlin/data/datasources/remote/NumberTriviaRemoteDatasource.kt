package com.pratama.tdd_kotlin.data.datasources.remote

import com.pratama.tdd_kotlin.data.model.NumberTriviaModel

interface NumberTriviaRemoteDatasource {
    fun getConcreteNumberTrivia(number: Int): NumberTriviaModel
    fun getRandomNumberTrivia(): NumberTriviaModel
}

//class NumberTriviaRemoteDatasourceImpl : NumberTriviaRemoteDatasource {
//    override fun getConcreteNumberTrivia(number: Int): NumberTriviaModel {
//        return NumberTriviaModel(number, "dummy")
//    }
//
//    override fun getRandomNumberTrivia(): NumberTriviaModel {
//        return NumberTriviaModel(1, "dummy")
//    }
//
//}