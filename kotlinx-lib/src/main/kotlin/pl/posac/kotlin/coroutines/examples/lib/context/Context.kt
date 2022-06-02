package pl.posac.kotlin.coroutines.examples.lib.context

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.coroutineContext

fun `coroutine context`() = Unit
fun CoroutineScope.log(msg: String) {
    val name = coroutineContext[CoroutineName]?.name
    println("[${Thread.currentThread().name}][$name] $msg")
}

fun main() = runBlocking(CoroutineName("main")) {
    log("Started")
    val v1 = async(CoroutineName("c1")) {
        delay(500)
        log("Running async")
        magicLog()
        42
    }
    launch {
        delay(1000)
        log("Running launch")

    }
    println("Child coroutines ${coroutineContext.job.children.toList()}")
    log("The answer is ${v1.await()}")

}


suspend fun magicLog(msg: String = "magic function") {
    println("[${Thread.currentThread().name}]${coroutineContext[CoroutineName]?.name} - ${msg}")
}