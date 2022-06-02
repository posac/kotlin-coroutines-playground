package pl.posac.kotlin.coroutines.examples.lib.channels

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import pl.posac.kotlin.coroutines.examples.lib.context.log
import kotlin.time.DurationUnit
import kotlin.time.toDuration

fun `channels examples`() = Unit

object ChannelsExample {
    val producersCount = 2
    val receiverCount = 1
    val messageCount = 2
    val bufferSize = 1
    val producerDelay = 100.toDuration(DurationUnit.MILLISECONDS)
    val receiverDelay = 200.toDuration(DurationUnit.MILLISECONDS)

    @JvmStatic
    fun main(args: Array<String>) = runBlocking {

        val channel = produce<String>(capacity = bufferSize) {
            val producer = this
            producersCount.downTo(1).forEach { coroutineId ->
                launch(CoroutineName("Producer${coroutineId}")) {
                    messageCount.downTo(1).forEach {
                        val message = "$coroutineId:$it"
                        delay(producerDelay)
                        log("before send of message = $message")
                        producer.send(message)
                        log("after send of message = $message")
                    }
                }
            }
        }

        receiverCount.downTo(1).forEach {coroutineId->
            launch(CoroutineName("Receiver${coroutineId}")) {
                for (message in channel) {
                    log("recieved message ${message}")
                    delay(receiverDelay)
                }
            }
        }

    }
}