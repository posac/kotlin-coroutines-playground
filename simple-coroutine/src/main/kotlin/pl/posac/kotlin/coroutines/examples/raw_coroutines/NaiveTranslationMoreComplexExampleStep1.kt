package pl.posac.kotlin.coroutines.examples.raw_coroutines

import kotlin.coroutines.Continuation


object NaiveTranslationMoreComplexExampleStep1 {

    suspend fun main() {
        //label 1
        val result1 = getItems(1)
        // label 2
        val result2 = getItems(result1.size)

        //label 3
        try {
            val result3 = getItems(result2.size - 100)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        //label 4
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