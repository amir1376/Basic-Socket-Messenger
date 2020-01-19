package ir.amirab.serversocket.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class StringUtils {
    //    private static String pattern = "^(.*?)\n\n";
    private static String pattern = "^(.*?)asd";
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        AsyncApp.getService().execute(() -> {
            while (true) {
            String next;
            try {
                next = reader.readLine();
                System.out.print(next);
            }catch (IOException e){}
            }
        });
    }
}
