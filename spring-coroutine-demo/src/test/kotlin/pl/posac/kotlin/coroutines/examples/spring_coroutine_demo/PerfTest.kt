package pl.posac.kotlin.coroutines.examples.spring_coroutine_demo

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

suspend fun main() = coroutineScope{
    val coroutinCount = 400
    val requestCount = 10
    val client = HttpClient(CIO)

    coroutinCount.downTo(0).forEach { coroutinCount ->
        launch {
            requestCount.downTo(0).forEach { request ->
                val response = client.get("http://localhost:8080/getMe/${coroutinCount * requestCount + request}")

            }
        }
    }
}