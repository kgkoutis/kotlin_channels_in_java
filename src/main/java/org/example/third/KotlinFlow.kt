package org.example.third

import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

// https://davidecerbo.medium.com/backpressure-in-kotlin-flows-9324d86c964e
fun currTime() = System.currentTimeMillis()
fun threadName() = Thread.currentThread().name

var start: Long = 0

fun emitter(): Flow<Int> =
    (1..5)
        .asFlow()
        .onStart { start = currTime() }
        .onEach {
            delay(1000)
            print("Emit $it (${currTime() - start}ms) ")
        }

@InternalCoroutinesApi
fun main() = runBlocking<Unit> {
    val time = measureTimeMillis {
        emitter()
            .buffer() // buffer unblocks the emitter
            .collect {
                print("\nCollect $it starts (${currTime() - start}ms) ")
                delay(3000)
                println("Collect $it ends (${currTime() - start}ms) ")
            }
    }
    print("\nCollected in $time ms")
}