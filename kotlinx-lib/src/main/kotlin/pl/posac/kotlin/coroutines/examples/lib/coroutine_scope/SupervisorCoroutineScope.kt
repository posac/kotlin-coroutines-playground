package pl.posac.kotlin.coroutines.examples.lib.coroutine_scope

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope
import kotlin.time.DurationUnit
import kotlin.time.toDuration


fun `supervisor coroutine scope`() = Unit

object SupervisorCoroutineScope {
    @JvmStatic
    fun main(args: Array<String>) = runBlocking {
        supervisorScope {
            10.downTo(1).forEach {
                launch() {
                    println("[${it}]Start")
                    delay(it.toDuration(DurationUnit.SECONDS))
                    println("[${it}]Stop")
                    throw RuntimeException("Failed child ${it}")
                }
            }

        }
    }
}