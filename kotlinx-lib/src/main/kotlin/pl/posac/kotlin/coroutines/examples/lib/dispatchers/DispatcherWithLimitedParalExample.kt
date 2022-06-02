package pl.posac.kotlin.coroutines.examples.lib.dispatchers

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import pl.posac.kotlin.coroutines.examples.lib.context.magicLog

fun `dispatchers limited parallerism example`() = Unit

object DispatcherWithLimitedParalExample{
    val firstDispatcher = Dispatchers.IO.limitedParallelism(3)
    val secondDispatcher = Dispatchers.IO.limitedParallelism(1)
    @JvmStatic
    fun main(args: Array<String>) = runBlocking {
        10.downTo(1).forEach {coroutineId ->
            launch(firstDispatcher) {
                delay(100)
                magicLog("[$coroutineId] first dispatcher")
                withContext(secondDispatcher){
                    Thread.sleep(200)
                    magicLog("[$coroutineId] second dispatcher")
                }
            }
        }
        Unit
    }
}