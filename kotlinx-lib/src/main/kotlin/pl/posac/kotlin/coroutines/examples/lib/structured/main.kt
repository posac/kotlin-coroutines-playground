package pl.posac.kotlin.coroutines.examples.lib.structured

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    launch { // same as just launch
        delay(1000L)
        println("World!")
    }
    launch { // same as this.launch
        delay(2000L)
        println("World!")
    }
    println("Hello,")
}


