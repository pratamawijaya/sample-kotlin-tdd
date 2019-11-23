package com.pratama.tdd_kotlin.core.usecase

import com.pratama.tdd_kotlin.core.data.Result
import com.pratama.tdd_kotlin.core.error.Failure
import com.pratama.tdd_kotlin.core.functional.Either

abstract class UseCase<out Type, in Params> where Type : Any {
    abstract suspend fun execute(params: Params): Result<Type>

    class None
}