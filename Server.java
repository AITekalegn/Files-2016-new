import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        final int PORT = 65432;

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is listening...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connected to client: " + clientSocket.getInetAddress());

                // Create a new thread to handle client's request
                Thread clientThread = new Thread(new ClientHandler(clientSocket));
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ClientHandler implements Runnable {
        private Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try (
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())
            ) {
                
                Object data = in.readObject();
                System.out.println("Received data from client: " + data);

                
                out.writeObject(data);
                out.flush();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}