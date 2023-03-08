package org.example.third

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.delay
import kotlinx.coroutines.future.future

data class Ball(val hits: Int) {
    override fun toString() = "Ball(hits=$hits)"
    fun increaseHits() = Ball(hits + 1)

    fun decreaseHits() = Ball(hits - 1)
}

class KotlinService {
    fun createChannel() = Channel<Ball>()

    @OptIn(DelicateCoroutinesApi::class)
    fun player(name: String, table: Channel<Ball>) = GlobalScope.future {
        for (ball in table) {
            val increasedBall = ball.increaseHits()
            println("$name $increasedBall")
            delay(100)
            table.send(increasedBall)
        }
    }

    fun sendAsync(channel: Channel<Ball>, ball: Ball) = GlobalScope.future {
        channel.send(ball)
    }

    fun receiveAsync(channel: ReceiveChannel<Ball>) = GlobalScope.future {
        val res = channel.receive()
        res
    }

    suspend fun send(channel: Channel<Ball>, ball: Ball) = channel.send(ball)

    suspend fun receive(channel: ReceiveChannel<Int>) {
        channel.receive()
    }
}

