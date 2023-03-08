package org.example.second

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext

// https://stackoverflow.com/questions/52869672/call-kotlin-suspend-function-in-java-class
class KotlinService {

    fun createChannel() : Channel<Int> {
        return Channel()
    }

    suspend fun send(channel: Channel<Int>) {
        for (x in 1..5) channel.send(x * x)
        channel.close()
    }

    suspend fun receive(channel: ReceiveChannel<Int>) {
        repeat(5) {
            println(channel.receive())
        }
    }
}


interface CoroutineCallback<RESULT> {
    companion object {
        @JvmOverloads
        fun <R> call(
            callback: CoroutineCallback<R>,
            dispatcher: CoroutineDispatcher = Dispatchers.Default
        ): Continuation<R> {
            return object : Continuation<R> {
                override val context: CoroutineContext
                    get() = dispatcher

                override fun resumeWith(result: Result<R>) {
                    callback.onComplete(result.getOrNull(), result.exceptionOrNull())
                }
            }
        }
    }

    fun onComplete(result: RESULT?, error: Throwable?)
}
