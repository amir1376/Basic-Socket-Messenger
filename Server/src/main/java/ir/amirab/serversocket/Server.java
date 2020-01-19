package ir.amirab.serversocket;

import ir.amirab.serversocket.connectionmanager.ChatManager;
import ir.amirab.serversocket.util.AsyncApp;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {

    private static final int SERVER_PORT = 4545;
    public static void main(String[] args) {
        try {
            createServerSocket();
        } catch (IOException e) {
            System.out.println("error connecting to server_socket");
            e.printStackTrace();
        }
    }

    private static void createServerSocket() throws IOException {
        ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
        ConnectionWaiter waiter = new ConnectionWaiter(serverSocket);
        System.out.println("start listening on port "+ serverSocket.getLocalPort());
        waiter.setOnConnectionAcceptedListener(socket -> {
            AsyncApp.getService().execute(()->{
                try {
                    new ChatManager(socket).manage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });
        waiter.startServing();
    }

}
