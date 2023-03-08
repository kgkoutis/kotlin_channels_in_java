package org.example.third;

import kotlin.Unit;
import kotlinx.coroutines.channels.Channel;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class App {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        KotlinService kotlinService = new KotlinService();
        Channel<Ball> channel = kotlinService.createChannel();
        kotlinService.player("ping", channel);
        kotlinService.player("pong", channel);
        final CompletableFuture<Unit> future = kotlinService.sendAsync(channel, new Ball(0));
        future.join();
        Thread.sleep(1000);

        final CompletableFuture<Ball> future2 = kotlinService.receiveAsync(channel);
        future2.join();
        System.out.println(future2.get());
    }
}