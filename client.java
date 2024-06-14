import java.io.*;
import java.net.*;

public class client {
    public static void main(String[] args) {
        final String serverName = "localhost";
        final int serverPort = 1234;

        try (
                Socket socket = new Socket(serverName, serverPort);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))
        ) {
            System.out.println("Connected to chat server");

            new Thread(() -> {
                String serverMessage;
                try {
                    while ((serverMessage = in.readLine()) != null) {
                        System.out.println(serverMessage);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput);
            }

        } catch (UnknownHostException e) {
            System.err.println("Unknown host " + serverName);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + serverName);
        }
    }
}
