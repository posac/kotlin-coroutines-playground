package pl.posac.kotlin.coroutines.examples.lib.flows

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import pl.posac.kotlin.coroutines.examples.lib.context.log

fun `flows examples`() = Unit

object FlowsExamples {
    val messageCount = 1

    @JvmStatic
    fun main(args: Array<String>) = runBlocking {
        val flow = flow<String> {
            messageCount.downTo(1).forEach {
                log("Loading value count =$it")
                val value = loadValue(it)
                emit(value)
                log("value emited =$it")
            }
        }.map {
            val res = mapSuspendable(it)
            log("mapped value $it")
            res

        }.collect {
            collectSusp(it)
            log("collect ${it}")
        }
    }

    suspend fun collectSusp(msg: String) {
        delay(100)

    }

    suspend fun loadValue(it: Int): String {
        delay(100)
        return "delayed value - ${it}"
    }

    suspend fun mapSuspendable(msg: String): String {
        delay(100)
        return "transformed - ${msg}"
    }
}