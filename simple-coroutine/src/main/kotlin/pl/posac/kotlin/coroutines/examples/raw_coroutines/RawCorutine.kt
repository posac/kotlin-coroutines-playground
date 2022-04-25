package pl.posac.kotlin.coroutines.examples.raw_coroutines

import kotlin.coroutines.resume

import kotlin.coroutines.suspendCoroutine

suspend fun main() {
    println("Before")
    var count = 0
    val result = suspendCoroutine<Boolean> { continuation ->
        println("Before continuation")
        continuation.resume(true)
        println("After resume")

    }
    println("After result = ${result}")
}