package pl.posac.kotlin.coroutines.examples.lib.dispatchers

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

fun `dispatchers example`() = Unit

object DispatchersExample {
    @JvmStatic
    fun main(args: Array<String>) = runBlocking {
        launch(Dispatchers.Default) {
            println(Thread.currentThread().name)
            withContext(Dispatchers.IO) {
                println(Thread.currentThread().name)
            }
        }
        Unit
    }
}