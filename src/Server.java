import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    ArrayList<Socket> sockets = new ArrayList<>();

    public void broadcast(String message) {
        for (Socket socket: sockets) {
            try {
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                writer.println(message);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(4444);
            System.out.println("Server started on port 4444");

            while(true) {
                Socket clientSocket = serverSocket.accept();
                sockets.add(clientSocket);
                System.out.println("Client Connected");

                ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Server().run();
    }

}
