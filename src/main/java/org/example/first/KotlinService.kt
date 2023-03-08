package org.example.first

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.future.future
import java.util.concurrent.CompletableFuture

// https://stackoverflow.com/questions/52869672/call-kotlin-suspend-function-in-java-class
class KotlinService {
    fun doIt(): Holder {
        val channel = Channel<Int>()
        val h = Holder(GlobalScope.future { send(channel) }, GlobalScope.future { receive(channel) })
        return h
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

data class Holder(val sender: CompletableFuture<Unit>, val receiver : CompletableFuture<Unit>) {
    fun toList() : List<CompletableFuture<Unit>> {
        return listOf(sender, receiver)
    }
}