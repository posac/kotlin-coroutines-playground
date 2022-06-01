package pl.posac.kotlin.coroutines.examples.spring_coroutine_demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringCoroutineDemoApplication

fun main(args: Array<String>) {
    runApplication<SpringCoroutineDemoApplication>(*args)
}
