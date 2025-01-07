package stepdefinitions.stepdefinitionsapi;

import io.cucumber.java.After;
import io.cucumber.java.Before;

import static main.runnerappium.CucumberRunner.driver;

public class Hooks {
    private static boolean isLoggedIn = false;
    private final APIStepDefinitions apiSteps = new APIStepDefinitions();

    @Before("@requiresLogin")
    public void ensureLoggedIn() throws InterruptedException {
        if (!isLoggedIn) {
            apiSteps.openLoginPage();
            apiSteps.login("harik@appmetry.com", "12345678");
            apiSteps.navigateToAccountPageAndFetchToken();
            isLoggedIn = true;
        }
    }

    @After("@requiresLogin")
    public void quitBrowser() throws InterruptedException {
        Thread.sleep(5000);
        apiSteps.closeLoginPage();
    }

}