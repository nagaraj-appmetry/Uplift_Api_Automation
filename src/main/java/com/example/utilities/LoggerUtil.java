package com.example.utilities;

import io.restassured.response.Response;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoggerUtil {

    private static final String LOG_FILE_PATH = "test_run_log.txt";

    public static void log(String message) {
        try (FileWriter fileWriter = new FileWriter(LOG_FILE_PATH, true);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            printWriter.println(timestamp + " - " + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void logResponse(Response response) {
        log("Status Code: " + response.getStatusCode());
        log("Response Body: " + response.getBody().asString());
        log("Headers: " + response.getHeaders().toString());
        log("===========================================================================================================================");
        log("        ===========================================NEXT=====================================================");
        log("===========================================================================================================================");
    }

    public static void clearLogFile() {
        try (PrintWriter writer = new PrintWriter(LOG_FILE_PATH)) {
            writer.print(""); // Clear the file content
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
