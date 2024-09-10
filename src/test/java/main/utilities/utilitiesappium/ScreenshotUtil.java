package main.utilities;

import com.epam.reportportal.service.ReportPortal;
import com.epam.reportportal.listeners.LogLevel;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

public class ScreenshotUtil {

    public static void addScreenShotToLogs(WebDriver driver, String message) {
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        Path path = Paths.get("screenshots", message + ".png");
        try {
            Files.createDirectories(path.getParent());
            FileHandler.copy(srcFile, path.toFile());
            ReportPortal.emitLog(message, LogLevel.INFO.name(), new Date(), path.toFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    }
