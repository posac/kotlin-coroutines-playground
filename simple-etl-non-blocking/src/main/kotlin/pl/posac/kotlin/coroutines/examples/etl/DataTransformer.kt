package pl.posac.kotlin.coroutines.examples.etl

data class TransformedData(val id: Int, val source: String = "xxx")
class DataTransformer {
    suspend fun transform(it: Data): TransformedData {
        val transformedData = TransformedData(id = it.id)
        println("[DataTransformer] transformed ${transformedData}")
        return transformedData
    }

}
