package ir.amirab.serversocket.connectionmanager;

import java.io.IOException;
import java.net.Socket;

public abstract class SocketConnectionManager extends ConnectionManager  {
    private final Socket socket;

    public Socket getSocket() {
        return socket;
    }

    public SocketConnectionManager(Socket socket) {
        this.socket = socket;
    }

    public abstract void manage() throws IOException;

    @Override
    public void close() throws IOException {
        finishingUp();
        socket.close();
    }
    protected void finishingUp() throws IOException {
    }
}
