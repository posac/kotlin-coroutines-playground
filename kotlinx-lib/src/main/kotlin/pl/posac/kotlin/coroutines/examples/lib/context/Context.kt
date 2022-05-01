package pl.posac.kotlin.coroutines.examples.lib.context

import kotlinx.coroutines.*
import kotlin.coroutines.coroutineContext

fun CoroutineScope.log(msg: String) {
    val name = coroutineContext[CoroutineName]?.name
    println("[$name] $msg")
}
fun main() = runBlocking(CoroutineName("main")) {
    log("Started")
    val v1 = async(CoroutineName("c1")) {
        delay(500)
        log("Running async")
        magic()
        42
    }
    launch {
        delay(1000)
        log("Running launch")

    }
    println("Child coroutines ${coroutineContext.job.children.toList()}")
    log("The answer is ${v1.await()}")

}


suspend fun magic(){

    println("${coroutineContext[CoroutineName]?.name} - magic function")
}