package pl.posac.kotlin.coroutines.examples.spring_coroutine_demo.controllers

import kotlinx.coroutines.delay
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.toDuration

@RestController
class SimpleSuspendedController {
    val log = LoggerFactory.getLogger(javaClass)

    @OptIn(ExperimentalTime::class)
    @GetMapping("/getMe/{id}")
    suspend fun getSomething(@PathVariable  id : Int ): String {
        log.info("getMe ${id} start")
        delay(1.toDuration(DurationUnit.SECONDS))
        log.info("getMe ${id} stop")
        return "OK"
    }
}