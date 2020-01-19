package ir.amirab.serversocket.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncApp {
    //thread pool for concorent executing server_socket connection
    private static ExecutorService service = Executors.newFixedThreadPool(10);
    public static ExecutorService getService() {
        return service;
    }
}
