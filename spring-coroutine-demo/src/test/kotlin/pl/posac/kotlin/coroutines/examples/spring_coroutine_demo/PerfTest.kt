package pl.posac.kotlin.coroutines.examples.spring_coroutine_demo


import ch.qos.logback.classic.Level
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.reactive.function.client.awaitExchange
import reactor.netty.http.HttpProtocol
import reactor.netty.http.client.HttpClient
import reactor.netty.resources.ConnectionProvider
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime
import kotlin.time.toDuration

sealed class Results {
    class Success(val time: kotlin.time.Duration) : Results()
    class Failure(val exception: Throwable) : Results()
}

@OptIn(ExperimentalTime::class, InternalCoroutinesApi::class)
suspend fun main() = coroutineScope {
    val coroutinCount = 100
    val requestCount = 10
    val webClientCounts = 3
    val logger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME) as ch.qos.logback.classic.Logger
    logger.setLevel(Level.INFO)


    val channel = Channel<Results>(capacity = coroutinCount * requestCount)

    launch {
        val totalTime = stressApi(
            webClientCounts,
            coroutinCount,
            requestCount,
            channel
        )
        logger.info("total time ${totalTime}")
    }

    val results = mutableListOf<Results>()
    var failuresCount = 0
    channel.consumeAsFlow().collect {
        results.add(it)
        if (it is Results.Failure) {
            failuresCount++
            logger.info("[failures] count = ${failuresCount}")
        }
    }
    val times = results.filterIsInstance(Results.Success::class.java).map { it.time }
    val inSeconds = times.map { it.inWholeSeconds }.toLongArray()
    logger.info("[avg] time ${inSeconds.average().toInt().toDuration(DurationUnit.SECONDS)}")
    logger.info("[avg] min ${inSeconds.minOrNull()?.toDuration(DurationUnit.SECONDS)}")
    logger.info("[avg] max ${inSeconds.maxOrNull()?.toDuration(DurationUnit.SECONDS)}")

    logger.info("inSeconds count = ${inSeconds.size}")

    logger.info("request count= ${coroutinCount * requestCount * webClientCounts}")


}

@OptIn(ExperimentalTime::class)
private suspend fun stressApi(
    webClientCounts: Int,
    coroutinCount: Int,
    requestCount: Int,
    channel: Channel<Results>
): kotlin.time.Duration {
    val totalTime = measureTime {
        supervisorScope {

            for (webclient in 1..webClientCounts) {
                launch {
                    val connectionProvider = ConnectionProvider.builder("myConnectionPool${webclient}")
                        .maxConnections(100)
                        .pendingAcquireMaxCount(1000)
                        .build()
                    val clientHttpConnector =
                        ReactorClientHttpConnector(HttpClient.create(connectionProvider).protocol(HttpProtocol.HTTP11))
                    val webClient = WebClient.builder()
                        .clientConnector(clientHttpConnector)
                        .build()
                        .get()


                    coroutinCount.downTo(1).forEach { coroutinCount ->
                        launch {
                            kotlin.runCatching {
                                measureTime {
                                    requestCount.downTo(1).forEach { request ->
                                        val url =
                                            "http://localhost:8080/getMe/${coroutinCount * requestCount + request}"
                                        webClient
                                            .uri(url)
                                            .awaitExchange {
                                                it.awaitBody(String::class)
                                            }
                                    }
                                }
                            }.fold(
                                onSuccess = { time ->
                                    channel.send(Results.Success(time))
                                },
                                onFailure = {
                                    channel.send(Results.Failure(it))
                                }
                            )
                        }
                    }

                }
            }

        }
    }

    channel.close()
    return totalTime
}
