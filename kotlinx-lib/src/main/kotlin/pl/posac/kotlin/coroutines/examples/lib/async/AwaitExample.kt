package pl.posac.kotlin.coroutines.examples.lib.async

import kotlinx.coroutines.*

fun main() = runBlocking {
    val resultDeferred: Deferred<Int> = GlobalScope.async {
        delay(1000L)
        42
    }
// do other stuff...
    val result: Int = resultDeferred.await() // (1 sec)
    println(result) // 42
// or just

    println(resultDeferred.await()) // 42
}