package pl.posac.kotlin.coroutines.examples.lib.exceptions

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.time.DurationUnit
import kotlin.time.toDuration


fun `exception handling - cancellation exception`() = Unit

object CancellationExceptionExamples {
    @JvmStatic
    fun main(args: Array<String>) = runBlocking {
        launch {

            launch {
                delay(500.toDuration(DurationUnit.MILLISECONDS))
                println("This will be not printed")
            }
            println("Throw exception that is not going out of coroutine")
            throw CancellationException("Parent shouldn't be notified about that")
        }
        launch {
            delay(1.toDuration(DurationUnit.SECONDS))
            println("this will be printed")
        }

        delay(2.toDuration(DurationUnit.SECONDS))
        Unit
    }
}