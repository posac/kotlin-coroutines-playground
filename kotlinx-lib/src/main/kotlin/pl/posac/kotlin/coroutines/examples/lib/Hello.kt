package pl.posac.kotlin.coroutines.examples.lib

import pl.posac.kotlin.coroutines.examples.lib.cancellation.`cancelation coroutine or jobs`
import pl.posac.kotlin.coroutines.examples.lib.channels.`channels examples`
import pl.posac.kotlin.coroutines.examples.lib.context.`coroutine context`
import pl.posac.kotlin.coroutines.examples.lib.coroutine_builders.`await example`
import pl.posac.kotlin.coroutines.examples.lib.coroutine_builders.`coroutine builders`
import pl.posac.kotlin.coroutines.examples.lib.coroutine_builders.`launch example`
import pl.posac.kotlin.coroutines.examples.lib.coroutine_builders.`run blocking`
import pl.posac.kotlin.coroutines.examples.lib.coroutine_scope.`coroutine scope`
import pl.posac.kotlin.coroutines.examples.lib.coroutine_scope.`supervisor coroutine scope`
import pl.posac.kotlin.coroutines.examples.lib.dispatchers.`dispatchers example`
import pl.posac.kotlin.coroutines.examples.lib.exceptions.`exception handling`
import pl.posac.kotlin.coroutines.examples.lib.exceptions.`exception handling - cancellation exception`
import pl.posac.kotlin.coroutines.examples.lib.flows.`flows examples`
import pl.posac.kotlin.coroutines.examples.lib.job.`Jobs examples`
import pl.posac.kotlin.coroutines.examples.lib.job.`simple job`
import pl.posac.kotlin.coroutines.examples.lib.job.`supervisor job`

fun `Introduction to kotlin lib and more advanced concepts`() {
    `coroutine builders` {
        `run blocking`()
        `launch example`()
        `await example`()
    }

    `coroutine context`()

    `Jobs examples` {
        `simple job`()
        `supervisor job`()
    }

    `cancelation coroutine or jobs`()

    exceptions {
        `exception handling`()
        `exception handling - cancellation exception`()
    }

    `scope functions` {
        `coroutine scope`() //TODO
        `supervisor coroutine scope`()
    }

    `dispatchers example`() //TODO


    `channels examples`() // TODO
    `flows examples`() //TODO

}


fun `exceptions`(x: () -> Unit) = Unit
fun `scope functions`(x: () -> Unit) = Unit
