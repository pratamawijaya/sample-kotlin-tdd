package com.pratama.tdd_kotlin.data.mapper

import com.pratama.tdd_kotlin.core.mapper.Mapper
import com.pratama.tdd_kotlin.data.model.NumberTriviaModel
import com.pratama.tdd_kotlin.domain.entities.NumberTrivia

class NumberTriviaMapper : Mapper<NumberTriviaModel, NumberTrivia> {
    override fun map(from: NumberTriviaModel): NumberTrivia {
        return NumberTrivia(number = from.number, text = from.text)
    }
}