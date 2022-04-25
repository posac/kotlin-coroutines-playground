package pl.posac.kotlin.coroutines.examples.raw_coroutines

import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

const val COROUTINE_SUSPENDED = "COROUTINE_SUSPENDED"

object NaiveTranslationMoreComplexExample {

    fun main(continuation: Continuation<Any>): Any {
        val continuation = object : Continuation<Any> {
            var result1: Any? = null
            var result2: Any? = null
            var result3: Any? = null

            var label: Int = 0
            var result: Any? = null

            override fun resumeWith(result: Result<Any>) {
                this.result = result.getOrNull()
                main(continuation)
                // some parent calling...
            }

            override val context: CoroutineContext
                get() = TODO("Not yet implemented")
        }

        when (continuation.label) {
            1 -> {
                continuation.label = 2
                val res = getItems(1, continuation)
                if (res == COROUTINE_SUSPENDED) return COROUTINE_SUSPENDED
            }
            2 -> {
                continuation.result1 = continuation.result
                continuation.label = 3
                val res = getItems((continuation.result1 as List<String>).size, continuation)
                if (res == COROUTINE_SUSPENDED) return COROUTINE_SUSPENDED

            }
            3 -> {
                continuation.result2 = continuation.result
                continuation.label = 4
                try {
                    val res = getItems((continuation.result2 as List<String>).size - 100, continuation)
                    if (res == COROUTINE_SUSPENDED) return COROUTINE_SUSPENDED
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            4 -> {
                println("result1=${continuation.result1} result2=${continuation.result2}")
            }

        }
        return Unit
    }


    fun getItems(id: Int, continuation: Continuation<Any>): Any {
        val innerContinuation = object : Continuation<Any> {
            var valiadted: Boolean? = null
            var label: Int = 0
            var result: Any? = null

            override fun resumeWith(result: Result<Any>) {
                this.result = result.getOrNull()
                getItems(id, continuation)
                // some parent calling...
            }

            override val context: CoroutineContext
                get() = TODO("Not yet implemented")
        }
        //TODO make here also split
        validate(id, continuation)

        if (innerContinuation.valiadted ?: false) {
            continuation.resume(listOf("x", "y", "z"))
        }
        continuation.resumeWithException(RuntimeException("Not valid id"))
        return Unit
    }

    fun validate(id: Int, continuation: Continuation<Any>): Any {
        continuation.resume(id > 0)
        return Unit
    }
}