import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        final String SERVER_ADDRESS = "127.0.0.1";
        final int PORT = 65432;

        try (
            Socket socket = new Socket(SERVER_ADDRESS, PORT);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream())
        ) {
            // Send data to server
            Object data = "Hello, server!";
            out.writeObject(data);
            out.flush();
            System.out.println("Sent data to server: " + data);

            // Receive result from server
            Object result = in.readObject();
            System.out.println("Received result from server: " + result);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}