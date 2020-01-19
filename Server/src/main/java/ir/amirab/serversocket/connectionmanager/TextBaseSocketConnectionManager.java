package ir.amirab.serversocket.connectionmanager;

import java.io.*;
import java.net.Socket;

public class TextBaseSocketConnectionManager extends SocketConnectionManager {

    public TextBaseSocketConnectionManager(Socket socket) {
        super(socket);
    }

    @Override
    public void manage() throws IOException {
        System.out.println("connected " + getSocket().getInetAddress());
        System.out.println("ok proceeding");
        System.out.println("so because not implemented closing");
        close();
    }

    private BufferedReader reader = null;
    private BufferedWriter writer = null;




    public void replay(String text) throws IOException {
        getOutputWriter().write(text);
        getOutputWriter().write("\n");
        getOutputWriter().flush();
    }



    protected BufferedWriter getOutputWriter() throws IOException {
        if (writer == null) {
            writer = new BufferedWriter(new OutputStreamWriter(getSocket().getOutputStream()));
        }
        return writer;
    }

    protected BufferedReader getInputReader() throws IOException {
        if (reader == null) {
            reader = new BufferedReader(new InputStreamReader(getSocket().getInputStream()));
        }
        return reader;
    }
}
