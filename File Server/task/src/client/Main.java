package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;

public class Main {
    static Map<String, String> map = Map.of("1", "GET", "2", "PUT", "3", "DELETE", "exit", "exit");

    public static void main(String[] args) {
        String address = "127.0.0.1";
        int port = 23456;

        String request = getInput();
        String response = sendRequest(address, port, request);
        String command = request.split(" ", 2)[0];

        switch (command+response.split(" ", 2)[0]) {
            case "PUT200":
                System.out.println("The response says that the file was created!");
                break;
            case "PUT403":
                System.out.println("The response says that creating the file was forbidden!");
                break;
            case "GET200":
                System.out.printf("The content of the file is: %s%s", response.split(" ", 2)[1], System.lineSeparator());
                break;
            case "GET404":
                System.out.println("The response says that the file was not found!");
                break;
            case "DELETE200":
                System.out.println("The response says that the file was successfully deleted!");
                break;
            case "DELETE404":
                System.out.println("The response says that the file was not found!");
                break;
            case "exit":
            default:
                break;
        }

    }

    private static String getInput() {
        StringBuilder request = new StringBuilder();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter action (1 - get a file, 2 - create a file, 3 - delete a file):");
        String command = scanner.next();

        request.append(map.get(command)).append(" ");
        if (!"exit".equals(command)) {
            System.out.println("Enter filename:");
            request.append(scanner.next()).append(" ");

            if ("2".equals(command)) {
                System.out.println("The content of the file is:");
                request.append(scanner.nextLine()).append(scanner.nextLine());
            }
        }
        return request.toString();
    }

    private static String sendRequest(String address, int port, String request) {
        String response = null;

        try (
                Socket socket = new Socket(InetAddress.getByName(address), port);
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream());
        ) {
            output.writeUTF(request);
            response = input.readUTF();
            System.out.println("Received: " + response);

            return response;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
