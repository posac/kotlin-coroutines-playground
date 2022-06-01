package pl.posac.kotlin.coroutines.examples.lib.exceptions

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.time.DurationUnit
import kotlin.time.toDuration

fun `exception handling`() = Unit

object ExceptionHandler {
    @JvmStatic
    fun main(args: Array<String>) = runBlocking {

        val handler = CoroutineExceptionHandler { ctx, exception ->
            println("Handler")
            println("Caught $exception ${exception.message}")
        }

        val scope = CoroutineScope(handler)
        scope.launch() {
            println("Will throw exception")
            delay(1.toDuration(DurationUnit.SECONDS))
            throw RuntimeException("Handler test")
        }
        delay(2.toDuration(DurationUnit.SECONDS))
        Unit
    }
}