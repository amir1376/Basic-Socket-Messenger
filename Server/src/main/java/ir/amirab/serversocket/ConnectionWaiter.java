package ir.amirab.serversocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionWaiter {

    Thread servingThread=null;
    private ServerSocket serverSocket=null;
    private OnConnectionAcceptedListener onConnectionAcceptedListener=null;
    public void setOnConnectionAcceptedListener(OnConnectionAcceptedListener onConnectionAcceptedListener) {
        this.onConnectionAcceptedListener = onConnectionAcceptedListener;
    }

    public interface OnConnectionAcceptedListener{
        void onConnectionAccepted(Socket socket) throws IOException;
    }
    public ConnectionWaiter(ServerSocket serverSocket){
        this.serverSocket=serverSocket;
    }
    public boolean startServing(){
        if (servingThread!=null){return false;}
        servingThread= new Thread(servRunnable);
        servingThread.start();
        return true;
    }
    public void stopServing(){
        if (servingThread!=null){
            servingThread.interrupt();
        }
    }
    Runnable servRunnable = () -> {
        while ( (!Thread.currentThread().isInterrupted())) {
            try {
                Socket socket = serverSocket.accept();
                onConnectionAcceptedListener.onConnectionAccepted(socket);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    };
}
