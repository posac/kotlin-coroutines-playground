package pl.posac.kotlin.coroutines.examples.lib.coroutine_builders

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun `run blocking`() = Unit

object RunBlockingExample {
    fun main() = runBlocking {
        GlobalScope.launch {
            delay(1000L)
            println("World!")
        }
        GlobalScope.launch {
            delay(1000L)
            println("World!")
        }
        GlobalScope.launch {
            delay(1000L)
            println("World!")
        }
        println("Hello,")
        delay(2000L) // still needed
    }
}


fun `coroutine builders`(x: () -> Unit) = Unit
