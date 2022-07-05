package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {
        String address = "127.0.0.1";
        int port = 23456;
        try (
                Socket socket = new Socket(InetAddress.getByName(address), port);
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream());
        ) {
            System.out.println("Client started!");
            String outputText = "Give me everything you have!";
            output.writeUTF(outputText);
            System.out.println("Sent: " + outputText);
            String inputText = input.readUTF();
            System.out.println("Received: " + inputText);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
