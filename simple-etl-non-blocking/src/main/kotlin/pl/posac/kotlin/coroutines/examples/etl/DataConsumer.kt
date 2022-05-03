package pl.posac.kotlin.coroutines.examples.etl

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

class DataConsumer {
    suspend fun consume(data: Flow<TransformedData>) {

        data
            .buffer(5)
            .onEach { value ->
                println("[DataConsumer] consuming ${value}")
                delay(100)
            }
            .onCompletion {
                flush()
            }
            .collect()

    }

    private suspend fun flush() {
        println("[DataConsumer] flush")
        delay(1000)
        println("[DataConsumer] flush ends")
    }

}

private fun makeFlow() = flow {
    println("Flow started")
    for (i in 1..3) {
        delay(1000)
        emit(i)
    }
}
suspend fun main() = coroutineScope {
    val flow = makeFlow().map {
        it * 100
    }
    delay(1000)
    println("Calling flow...")
    flow.collect { value -> println(value) }
    println("Consuming again...")
    flow.collect { value -> println(value) }
}