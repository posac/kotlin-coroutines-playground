package pl.posac.kotlin.coroutines.examples.lib.job

import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.time.DurationUnit
import kotlin.time.toDuration


fun `supervisor job`() = Unit

object JobSupervisorExample {
    @JvmStatic
    fun main(args: Array<String>) = runBlocking{
        val job = SupervisorJob(this.coroutineContext.job)
        10.downTo(1).forEach {
            launch(job) {
                println("[${it}]Start")
                delay(it.toDuration(DurationUnit.SECONDS))
                println("[${it}]Stop")
                throw RuntimeException("Failed child ${it}")
            }
        }
        job.children.forEach { it.join() }
    }
}