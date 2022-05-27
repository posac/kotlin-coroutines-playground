package pl.posac.kotlin.coroutines.examples.spring_coroutine_demo


import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.reactive.function.client.awaitExchange
import reactor.netty.http.client.HttpClient
import reactor.netty.resources.ConnectionProvider
import java.time.Duration

suspend fun main() = coroutineScope{
    val connectionProvider = ConnectionProvider.builder("myConnectionPool")
        .maxConnections(500)
        .pendingAcquireMaxCount(5000)
        .disposeTimeout(Duration.ofMinutes(1))
        .build()
    val clientHttpConnector = ReactorClientHttpConnector(HttpClient.create(connectionProvider))
    for (i in 1..10) {
            launch {
                val webClient = WebClient.builder()
                    .clientConnector(clientHttpConnector)
                    .build()

                    .get()
                coroutineScope{
                    val coroutinCount = 300
                    val requestCount = 10


                    coroutinCount.downTo(0).forEach { coroutinCount ->
                        launch {
                            requestCount.downTo(0).forEach { request ->
                                val url = "http://localhost:8080/getMe/${coroutinCount * requestCount + request}"
                                webClient
                                    .uri(url)
                                    .awaitExchange {
                                        it.awaitBody(String::class)
                                    }
                            }
                        }
                    }
                }
            }
    }

}