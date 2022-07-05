package server;

import java.util.HashMap;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HashMap<String, String> fileStorage = new HashMap<>();
        String command = scanner.next();
        while (!"exit".equals(command)) {
            String fileName = scanner.next();
            switch (command) {
                case "add":
                    if (fileStorage.containsKey(fileName) || !fileName.matches("(file)([1-9]|10)\\b")) {
                        System.out.println("Cannot add the file " + fileName);
                    } else {
                        fileStorage.put(fileName, fileName);
                        System.out.println("The file " + fileName + " added successfully");
                    }
                    break;
                case "get":
                    if (!fileStorage.containsKey(fileName)) {
                        System.out.println("The file " + fileName + " not found");
                    } else {
                        System.out.println("The file " + fileName + " was sent");
                    }
                    break;
                case "delete":
                    if (!fileStorage.containsKey(fileName)) {
                        System.out.println("The file " + fileName + " not found");
                    } else {
                        fileStorage.remove(fileName);
                        System.out.println("The file " + fileName + " was deleted");
                    }
                    break;
            }
            command = scanner.next();
        }
    }
}