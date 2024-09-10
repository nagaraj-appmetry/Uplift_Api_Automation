package main.runnerappium;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import main.utilities.utilitiesapi.LoggerUtil;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import main.utilities.Hooks;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;

@CucumberOptions(
        monochrome = true,
        features = "src/test/resources/features/featuresappium",
        glue = {"stepdefinitions.stepdefinitionsappium", "main.runnerappium"},
        publish = true,
        plugin = { "pretty","html:target/cucumber-reports/cucumber_report.html",
                "com.epam.reportportal.cucumber.StepReporter",
                "main.utilities.StepDetailsUtil"}
        )

public class CucumberRunner extends AbstractTestNGCucumberTests {
    public static AppiumDriver driver;
    public static Properties config;
    public static WebDriverWait wait;
    public static WebDriverWait shortWait;
    public static HashMap<String,String> currentTestDetails = new HashMap<>();


    public void LoadConfigProperty() throws IOException {
        config = new Properties();
        FileInputStream ip = new FileInputStream(
                System.getProperty("user.dir") + "//src//test//resources//config//config.properties");
        config.load(ip);
    }

    @BeforeSuite
    public void beforeAll() throws IOException {
        Hooks.startAppiumServer();
        driver = createAppiumDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        shortWait = new WebDriverWait(driver, Duration.ofSeconds(2));
    }

    @AfterSuite
    public void afterAll() {
        if (null != driver) {
            System.out.println("Close the driver");
            driver.quit();
        }
        Hooks.stopAppiumServer();
    }


    private AppiumDriver createAppiumDriver() throws IOException {
        LoadConfigProperty();
        XCUITestOptions dc = new XCUITestOptions();
        dc.setPlatformName(config.getProperty("platformName"));
        dc.setCapability("udid",config.getProperty("udid"));
        dc.setCapability("appium:bundleId", config.getProperty("bundleId"));
        //dc.setCapability("appium:app", System.getProperty("user.dir")+config.getProperty("app"));
        //dc.setCapability("orientation", config.getProperty("orientation"));
        dc.setCapability("autoAcceptAlerts",Boolean.parseBoolean(config.getProperty("autoAcceptAlerts")));

        return new AppiumDriver(Hooks.getAppiumServerUrl(), dc);
    }

    public By returnParameterisedXpath(String xpath, String parameter){
        return By.xpath(String.format(xpath,parameter));
    }

    public void isElementVisible(By element){
        WebElement mobElement =  wait.until(ExpectedConditions.visibilityOfElementLocated(element));
        if(mobElement == null){
            throw new NoSuchElementException("Element is not present");
        }
    }

    public void isElementPresent(By element){
        WebElement mobElement =  wait.until(ExpectedConditions.presenceOfElementLocated(element));
        if(mobElement == null){
            throw new NoSuchElementException("Element is not visible");
        }
    }

    public boolean isElementAbsent(By element) {
        List<WebElement> webElements = driver.findElements(element);
        return webElements.isEmpty();

    }

    public void clickElement(By element) {
        WebElement mobElement = wait.until(ExpectedConditions.elementToBeClickable(element));
        if (mobElement != null) {
            mobElement.click();
        } else {
            throw new NoSuchElementException("Element is not found to be clicked");
        }
    }

    public void sendKeysToElement(By element, String keys) {
        WebElement mobElement = wait.until(ExpectedConditions.visibilityOfElementLocated(element));
        if (mobElement != null) {
            mobElement.sendKeys(keys);
        } else {
            throw new NoSuchElementException("Element is not found to enter keys");
        }
    }

    public String getTextFromElement(By element){
    WebElement mobElement = wait.until(ExpectedConditions.visibilityOfElementLocated(element));
        if (mobElement != null) {
        return mobElement.getText();
    } else {
            throw new NoSuchElementException("Element is not found to retrieve text");

        }
    }

}


