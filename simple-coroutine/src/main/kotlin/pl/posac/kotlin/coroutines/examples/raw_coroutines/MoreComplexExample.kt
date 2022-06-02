package pl.posac.kotlin.coroutines.examples.raw_coroutines

import kotlinx.coroutines.runBlocking

object MoreComplexExample {
    @JvmStatic
    fun main(args: Array<String>)  = runBlocking{
        val result1 = getItems(1)
        val result2 = getItems(result1.size)
        try {
            val result3 = getItems(result2.size - 100)
        } catch (e: Exception) {
            println("Error ${e.message}")
        }
        println("result1=$result1 result2=${result2}")

    }


    suspend fun getItems(id: Int): List<String> {
        val validated = validate(id)
        if (validated) {
            return listOf("x", "y", "z")
        }
        throw RuntimeException("Not valid id")
    }

    suspend fun validate(id: Int): Boolean {
        return id > 0
    }

}

fun `Suspending functions - how they are working`(x: MoreComplexExample.() -> Unit) = MoreComplexExample