package ir.amirab.serversocket.connectionmanager;

import ir.amirab.serversocket.util.AsyncApp;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class LineTextBaseSocketConnectionManager extends SocketConnectionManager {
    public interface OnNewLineReceived {
        void onNewLineReceived(String line);
    }

    public interface OnDisconnected {
        void onDisconnected();
    }

    public LineTextBaseSocketConnectionManager(Socket socket) {
        super(socket);
    }

    @Override
    public void manage() throws IOException {
        System.out.println("connected " + getSocket().getInetAddress());
        System.out.println("ok proceeding");
        System.out.println("so because not implemented closing");
        registerIncomingLines();
    }

    private BufferedReader reader = null;
    private BufferedWriter writer = null;
    private OnNewLineReceived onNewLineReceived = null;
    private OnDisconnected onDisconnected = null;

    public void setOnNewLineReceived(OnNewLineReceived onNewLineReceived) {
        this.onNewLineReceived = onNewLineReceived;
    }

    public void setOnDisconnected(OnDisconnected onDisconnected) {
        this.onDisconnected = onDisconnected;
    }

    protected void registerIncomingLines() {
        AsyncApp.getService().execute(() -> {
                    try {
                        while (!getSocket().isClosed()) {
                            String line = getInputReader().readLine();
                            if (line != null && onNewLineReceived != null) {
                                onNewLineReceived.onNewLineReceived(line);
                            }
                        }
                    } catch (IOException e) {
                        if (onDisconnected != null) {
                            onDisconnected.onDisconnected();
                        }
                    }

                }
        );
    }


    public void replay(String text) throws IOException {
        getOutputWriter().write(text);
        getOutputWriter().write("\n");
        getOutputWriter().flush();
    }

    public void readStream() throws IOException {
        char[] b = new char[4 * 1024];
        int len;
        while (!getSocket().isClosed()) {
            len = getInputReader().read(b);
            if (len != -1) {
                System.out.print(String.valueOf(b, 0, len));
            }
        }
    }

    public String readOneLineOrReadIntoLast() throws IOException {
        StringBuilder builder = new StringBuilder();
        char[] b = new char[1];
        int len;
        while (!getSocket().isClosed()) {
            len = getInputReader().read(b);
            if (len != -1) {
                char character = b[0];
                if (character == '\n') {
                    builder.append(character);
                }
            } else {
                String string = builder.toString();
                return string.isEmpty() ? null : string;
            }
        }
        return null;
    }

    public String readSingleLine() throws IOException {
        BufferedReader in = getInputReader();
        try {
            return in.readLine();
        } catch (SocketException e) {
            return null;
        }
    }

    public String readAllToStrong() throws IOException {
        BufferedReader in = getInputReader();
        StringBuilder builder = new StringBuilder();
        while (true) {
            String line = in.readLine();
            if (!getSocket().isConnected()) break;
            if (line == null) continue;
            builder.append(line);
        }
        return builder.toString();
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
