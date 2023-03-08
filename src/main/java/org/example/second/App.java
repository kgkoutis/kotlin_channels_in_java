package org.example.second;

import kotlinx.coroutines.channels.Channel;

public class App
{
    public static void main(String[] args) throws InterruptedException {
        String language = "kotlin";
        // Do nothing
        if (language.equals("java")) {
            new JavaService().sayHello();
        } else if (language.equals("kotlin")) {
            KotlinService service = new KotlinService();

            final Channel<Integer> channel = service.createChannel();

            service.send(channel, CoroutineCallback.Companion.call((unit, error) -> {
                if (error != null) {
                    System.out.println("Error: " + error.getMessage());
                } else {
                    System.out.println("server Done");
                }
            }));

            service.receive(channel, CoroutineCallback.Companion.call((unit, error) -> {
                if (error != null) {
                    System.out.println("Error: " + error.getMessage());
                } else {
                    System.out.println("receiver Done");
                }
            }));
        }

        Thread.sleep(4000);
    }
}