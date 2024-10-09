package pages.clientapi;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class JsonComparator {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter the path of the first text file: ");
            String filePath1 = scanner.nextLine();

            System.out.print("Enter the path of the second text file: ");
            String filePath2 = scanner.nextLine();

            // Read text files
            String content1 = readFileContent(filePath1);
            String content2 = readFileContent(filePath2);

            // Convert text to JSON
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode json1 = objectMapper.readTree(content1);
            JsonNode json2 = objectMapper.readTree(content2);

            // Compare the JSON nodes
            compareJsonNodes(json1, json2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String readFileContent(String filePath) throws IOException {
        // Read the content from the file
        byte[] bytes = Files.readAllBytes(Paths.get(filePath));
        return new String(bytes); // Convert bytes to String
    }

    private static void compareJsonNodes(JsonNode node1, JsonNode node2) {
        if (!node1.equals(node2)) {
            System.out.println("Differences found:");
            System.out.println("File 1: " + node1);
            System.out.println("File 2: " + node2);
        } else {
            System.out.println("No differences.");
        }
    }
}
