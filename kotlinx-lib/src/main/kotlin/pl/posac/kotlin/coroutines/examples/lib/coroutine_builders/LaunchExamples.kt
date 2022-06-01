package pl.posac.kotlin.coroutines.examples.lib.coroutine_builders

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object LaunchExamples {
    fun main() {
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
        Thread.sleep(2000L) //
    }
}
fun `launch example`() = Unit