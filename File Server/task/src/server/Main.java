package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {

    static String address = "127.0.0.1";
    static int port = 23456;

    private static final String DATA_DIR = System.getProperty("user.dir") + File.separator + "src" + File.separator + "server" + File.separator + "data" + File.separator;

    public static void main(String[] args) {
        String command = "";
        File path = new File(DATA_DIR);
        System.out.println(DATA_DIR);
        if (!path.exists()) {
            path.mkdirs();
        }

        try (ServerSocket server = new ServerSocket(port, 50, InetAddress.getByName(address));) {
            System.out.println("Server started!");
            while (!"exit".equals(command)) {
                try (
                        Socket socket = server.accept();
                        DataInputStream input = new DataInputStream(socket.getInputStream());
                        DataOutputStream output = new DataOutputStream(socket.getOutputStream());
                ) {
                    String result = "";
                    String inputText = input.readUTF();
                    System.out.println("Received: " + inputText);
                    String[] lines = inputText.split(" ", 3);
                    command = lines[0];

                    switch (command) {
                        case "PUT":
                            result = createFile(lines[1], lines[2]);
                            output.writeUTF(result);
                            break;
                        case "GET":
                            result = readFile(lines[1]);
                            output.writeUTF(result);
                            break;
                        case "DELETE":
                            result = deleteFile(lines[1]);
                            output.writeUTF(result);
                            break;
                        case "exit":
                            output.writeUTF("");
                            break;
                        default:
                            break;
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String deleteFile(String fileName) {
        try {
            Files.delete(Path.of(DATA_DIR + fileName));
            return "200";
        } catch (IOException e) {
            return "404";
        }
    }

    private static String readFile(String fileName) {
        try (Scanner sc = new Scanner(new File(DATA_DIR + fileName));
        ) {
            return "200 " + sc.nextLine();
        } catch (FileNotFoundException e) {
            return "404";
        }
    }

    private static String createFile(String fileName, String content) {
        if (new File(DATA_DIR + fileName).exists()) {
            return "403";
        }
        try (FileWriter file = new FileWriter(DATA_DIR + fileName)) {
            file.write(content);
        } catch (IOException e) {
            return "403";
        }
        return "200";
    }

}