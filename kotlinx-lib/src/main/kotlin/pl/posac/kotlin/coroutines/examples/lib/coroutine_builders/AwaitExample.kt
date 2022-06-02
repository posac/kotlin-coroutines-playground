package pl.posac.kotlin.coroutines.examples.lib.coroutine_builders

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

object AwaitExample {
    @JvmStatic
    fun main(args: Array<String>)  = runBlocking{
        val resultDeferred: Deferred<Int> = async {
            delay(1000L)
            42
        }
// do other stuff...
        val result: Int = resultDeferred.await() // (1 sec)
        println(result) // 42
// or just

        println(resultDeferred.await()) // 42
    }
}

fun `await example`() = Unit