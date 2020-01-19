package ir.amirab.clientsocket;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Client {
    private static final String CHAT_START_PROTOCOL = "CHAT_START";
    private static final int SERVER_PORT = 4545;
    public static String SERVER_HOST = "127.0.0.1";

    public static void main(String[] args) {
        try {
            connectToServer();
        } catch (IOException | InterruptedException e) {
//            System.out.println("error connecting to server_socket");
            e.printStackTrace();
        }
    }

    private static void connectToServer() throws IOException, InterruptedException {
        Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
        System.out.println("connected to server");
        PrintWriter out = createStreamWriter(socket.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        out.print(CHAT_START_PROTOCOL);
        out.print("\n");
        out.flush();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        AsyncApp.getService().execute(() -> {
            while (!socket.isClosed() && !socket.isOutputShutdown()) {
                try {
                    String line = readSingleLine(in);
                    if (line != null) {
                        System.out.println("("+getNow()+"): " + line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        AsyncApp.getService().execute(() -> {
            System.out.println("executing...");

            while (!socket.isClosed()&& !socket.isInputShutdown()) {
                try {
                    String line = reader.readLine();
                    out.println(line);
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        //        Scanner scanner = new Scanner(System.in);
//        while (true) {
//            String str = scanner.nextLine();
//            if (str.equals("</>")) {
//                str = null;
//                break;
//            } else {
//                out.println(str);
//            }
//            out.flush();
//        }
//        System.out.println(readStreamToString( client.getInputStream()));
    }

    private static String getNow() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public static String readSingleLine(BufferedReader in) throws IOException {
        try {
            return in.readLine();
        } catch (SocketException e) {
            return null;
        }
    }

    static PrintWriter createStreamWriter(OutputStream os) {
        return new PrintWriter(
                new BufferedWriter(
                        new OutputStreamWriter(os)
                )
        );
    }

    static String readStreamToString(InputStream is) throws IOException, InterruptedException {
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        StringBuilder builder = new StringBuilder();
        while (true) {
            String line = in.readLine();
            if (line == null) break;
            builder.append(line);
            builder.append("\n");
        }
        return builder.toString();
    }
}
