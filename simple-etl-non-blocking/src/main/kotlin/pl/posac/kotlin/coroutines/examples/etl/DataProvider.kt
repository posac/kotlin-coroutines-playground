package pl.posac.kotlin.coroutines.examples.etl

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

data class Data(val id: Int)

class DataProvider(
    val count: Int = 10,
    val inBatch: Int = 10,
    val delayDuration: Duration = 500.toDuration(DurationUnit.MILLISECONDS)
) {
    suspend fun loadData(): Flow<Data> = flow<Data> {
        count.downTo(0).forEach { batchId ->
            inBatch.downTo(0).forEach {
                val data = Data(batchId * 1000 + it)
                println("[DataProvider] Sending data ${data}")
                emit(data)
            }
            println("[DataProvider] Delay")
            delay(delayDuration)
        }
    }


}
