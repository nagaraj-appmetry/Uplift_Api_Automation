package stepdefinitions.stepdefinitionsappium;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Objects;

public class InsightsPageStepDefinitions extends StepDefMaster {

    @And("he lands on the insights page")
    public void verifyIfUserInInsights() {
        insights.verifyLandingInsight();
        checkInsightDefault(); // Adding this validation here. to be changed later
    }

    public void checkInsightDefault() {
        insights.checkForDefaultInsightMessageAndButton();
    }

}
