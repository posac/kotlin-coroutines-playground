package pl.posac.kotlin.coroutines.examples.etl

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

data class Data(val id: Int)

class DataProvider(
    val count: Int = 2,
    val providerParallelizm: Int = 2,
    val inBatch: Int = 10,
    val delayDuration: Duration = 500.toDuration(DurationUnit.MILLISECONDS)
) {
    suspend fun loadData(): Flow<Data> = channelFlow  {
        val producer = this
        coroutineScope {
            providerParallelizm.downTo(0).forEach { coroutine ->

                launch {
                    count.downTo(0).forEach { batchId ->

                        inBatch.downTo(0).forEach {
                            val data = Data(batchId * 1000 + it)
                            println("[DataProvider${coroutine}] Sending data ${data}")
                            producer.send(data)
                        }
                        println("[DataProvider${coroutine}] Delay")
                        delay(delayDuration.times(providerParallelizm+1))
                    }

                }
            }
        }


    }


}
