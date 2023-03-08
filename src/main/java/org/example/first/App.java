package org.example.first;

import kotlin.Unit;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class App
{
    public static void main(String[] args) throws InterruptedException {
        String language = "kotlin";
        // Do nothing
        if (language.equals("java")) {
            new JavaService().sayHello();
        } else if (language.equals("kotlin")) {
            KotlinService service = new KotlinService();
            final List<CompletableFuture<Unit>> completableFutures = service.doIt().toList();
            completableFutures.forEach(CompletableFuture::join);
        }

        Thread.sleep(4000);
    }
}