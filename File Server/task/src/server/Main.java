package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Main {

    public static void main(String[] args) {
        String address = "127.0.0.1";
        int port = 23456;
        try (
                ServerSocket server = new ServerSocket(port, 50, InetAddress.getByName(address));
        ) {
            System.out.println("Server started!");
            try (
                    Socket socket = server.accept();
                    DataInputStream input = new DataInputStream(socket.getInputStream());
                    DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            ) {
                String inputText = input.readUTF();
                System.out.println("Received: " + inputText);
                String outputText = "All files were sent!";
                output.writeUTF(outputText);
                System.out.println("Sent: " + outputText);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}