package pages.pagesappium;

import main.runnerappium.CucumberRunner;
import org.openqa.selenium.By;


public class Login extends CucumberRunner {
    By textTitlePage = By.xpath("//XCUIElementTypeStaticText[@name=\"BRING IN-PERSON COACHING HOME\"]");
    By buttonStartLogin = By.xpath("//XCUIElementTypeButton[@name=\"login\"]");
    By inputEmail = By.xpath("//XCUIElementTypeTextField[@name=\"email\"]");
    By inputPassword = By.xpath("//XCUIElementTypeSecureTextField[@name=\"password\"]");
    By buttonLogin = By.xpath("//XCUIElementTypeButton[@name=\"login\"]");
    By textInsightTitle = By.xpath("//XCUIElementTypeStaticText[@name=\"Insights\"]");
    public void isHomePage() {

        isElementVisible(textTitlePage);
    }

    public void clickOnStartLoginButton() throws InterruptedException {
        clickElement(buttonStartLogin);
    }

    public void enterUsername(String email){
        sendKeysToElement(inputEmail,email);
    }

    public void enterPassword(String password){
        sendKeysToElement(inputPassword,password);
    }

    public void clickLoginButton() throws InterruptedException {
        clickElement(buttonLogin);
    }

    public void isInsightOpened(){
        isElementVisible(textInsightTitle);
    }

    public void driverQuit(){
        driver.quit();
    }

}
