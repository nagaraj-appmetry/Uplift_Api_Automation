package stepdefinitions.stepdefinitionsappium;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginPageStepDefinitions extends StepDefMaster{

    @Given("the user is in the homepage and clicks on login")
    public void isUserInLoginPage() throws InterruptedException {
        login.isHomePage();
        login.clickOnStartLoginButton();
    }

    @When("the user enters {string} as email and {string} as password")
    public void theUserEntersAsEmailAndAsPassword(String userName, String password) {
        login.enterUsername(userName);
        login.enterPassword(password);
    }

    @And("user clicks on the Login button")
    public void userClicksOnTheLoginButton() throws InterruptedException {
        login.clickLoginButton();
    }


    @Then("the user successfully logs in")
    public void theUserSuccessfullyLogsIn() {
        login.isInsightOpened();
    }

    @Then("driver is quit")

    public void driverIsQuit() {
        
    }
}
