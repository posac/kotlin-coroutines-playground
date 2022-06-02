package pl.posac.kotlin.coroutines.examples.raw_coroutines

import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume

import kotlin.coroutines.suspendCoroutine


fun continueAfterSecond(continuation: Continuation<Boolean>) {
    thread {
        Thread.sleep(1000)
        continuation.resume(true)
    }
}

object RawCorutine {

    @JvmStatic
    fun main(args: Array<String>)  = runBlocking{
        println("Before")
        val result = suspendCoroutine<Boolean> { continuation ->
            continueAfterSecond(continuation)
        }
        println("After with result = ${result}")
    }
}


typealias `Introcution to simple coroutine, continuation` = RawCorutine