package pl.posac.kotlin.coroutines.examples.lib.coroutine_scope

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking


fun `coroutine scope`() = Unit

object CoroutineScope {
    @JvmStatic
    fun main(args: Array<String>) = runBlocking {
        val a = coroutineScope {
            println("Start calculating a")
            delay(1000)
            10
        }
        println("a is calculated")
        val b = coroutineScope {
            println("Start calculating b")
            delay(1000)
            20
        }
        println(a) // 10
        println(b) // 20
    }
}