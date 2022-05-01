package pl.posac.kotlin.coroutines.examples.lib.job

import kotlinx.coroutines.*

fun main() = runBlocking {
    val job = Job()
    launch(job) {
        repeat(5) { num ->
            delay(num*100L)
            println("Rep$num")
        }
    }

    launch(job) {
//        delay(300)
//        println("Break with exception")
//        job.completeExceptionally(RuntimeException("Something")) // cancels all childs
        delay(500)
        job.complete()
    }
    job.join()
    println("Done")
}