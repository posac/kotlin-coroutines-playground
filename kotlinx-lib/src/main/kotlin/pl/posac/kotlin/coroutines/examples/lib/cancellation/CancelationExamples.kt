package pl.posac.kotlin.coroutines.examples.lib.cancellation

import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.time.DurationUnit
import kotlin.time.toDuration

fun `cancelation coroutine or jobs`() = Unit

object CancelationExamples {

    @JvmStatic
    fun main(args: Array<String>) = runBlocking {
        val longRunningTask = launch {
            println("long running ")
            try {
                delay(1.toDuration(DurationUnit.MINUTES))
            } finally {
                println("closing")
            }

        }
        delay(10.toDuration(DurationUnit.SECONDS))
        longRunningTask.cancelAndJoin() // cancel has to wait for next continuation to break
    }
}