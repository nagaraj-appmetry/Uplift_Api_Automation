package main.utilities.utilitiesapi;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoggerUtil {

    private static final String LOG_FILE_PATH = "test_run_log.txt";
    private static RequestSpecification request;

    public static void log(String message) {
        try (FileWriter fileWriter = new FileWriter(LOG_FILE_PATH, true);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            printWriter.println(timestamp + " - " + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void logRequest(RequestSpecification request, String endpoint, String body) {
        LoggerUtil.request = request;
        log("Request URL: " + endpoint);
        if (body != null && !body.isEmpty()) {
            log("Request Body: " + body);
        }
        log("=====NEXT RESPONSE LOG====");
    }

    public static void logResponse(Response response) {
        log("Status Code: " + response.getStatusCode());
        log("Response Body: " + response.getBody().asString());
        log("=====NEXT REQUEST LOG====");
    }

    public static void clearLogFile() {
        try (PrintWriter writer = new PrintWriter(LOG_FILE_PATH)) {
            writer.print("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


