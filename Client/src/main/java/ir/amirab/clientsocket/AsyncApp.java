package ir.amirab.clientsocket;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncApp {
    private static ExecutorService service = Executors.newFixedThreadPool(10);

    public static ExecutorService getService() {
        return service;
    }
}
