package com.pratama.tdd_kotlin.data.mapper

import com.pratama.tdd_kotlin.data.model.NumberTriviaModel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class NumberTriviaMapperTest {

    lateinit var mapper: NumberTriviaMapper

    @Before
    fun setUp() {
        mapper = NumberTriviaMapper()
    }

    @Test
    fun `test map from numbertrivia model to numbertrivia entities`() {
        val testNumberTriviaModel = NumberTriviaModel(number = 1, text = "test mapper")

        val result = mapper.map(testNumberTriviaModel)

        // expect
        assertEquals(result.number, 1)
        assertEquals(result.text, "test mapper")
    }
}