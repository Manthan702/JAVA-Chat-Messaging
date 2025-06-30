import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {
    Socket clientSocket;
    BufferedReader bReader;
    PrintWriter pWriter;
    Server server;

    public ClientHandler(Socket clientSocket, Server server) {
        this.clientSocket = clientSocket;
        this.server = server;
        try {
            this.bReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            this.pWriter = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String name = "";
        try {
            // Request and read client's name
            while (true) {
                pWriter.println("SUBMITNAME");
                name = bReader.readLine();
                if (name == null) return;
                if (!name.isEmpty()) break;
            }

            pWriter.println("NAMEACCEPTED");
            server.broadcast("MESSAGE " + name + " has connected");

            // Read messages and broadcast
            while (true) {
                String userMessage = bReader.readLine();
                if (userMessage == null) return;
                server.broadcast("MESSAGE " + name + " : " + userMessage);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            server.broadcast("MESSAGE " + name + " has left the chat");
        }
    }
}
