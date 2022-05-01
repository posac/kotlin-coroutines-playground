package pl.posac.kotlin.coroutines.examples.etl

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.map


class EtlApplication {
    private val dataProvider = DataProvider()
    private val dataTransformer = DataTransformer()
    private val dataConsumer = DataConsumer()

    suspend fun process() {
        val data = dataProvider.loadData()
            .map {
                dataTransformer.transform(it)
            }
        dataConsumer.consume(data)
    }
}


suspend fun main() = coroutineScope {

    EtlApplication().process()

}