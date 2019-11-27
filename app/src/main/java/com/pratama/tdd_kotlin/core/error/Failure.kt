package com.pratama.tdd_kotlin.core.error

// https://raw.githubusercontent.com/android10/Android-CleanArchitecture-Kotlin/master/app/src/main/kotlin/com/fernandocejas/sample/core/exception/Failure.kt

sealed class Failure {
    object NetworkConnection : Failure()
    object ServerError : Failure()

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure : Failure()
}