package stepdefinitions.stepdefinitionsappium;

import io.cucumber.java.en.And;

import java.util.Objects;

public class AssessmentSelectionPageStepDefinition extends StepDefMaster {

    @And("user clicks on the Assessment button at the menu bar")
    public void moveToAssessments() {
        assessmentSelection.clickMenuAssessment();
        assessmentSelection.verifyIfUserOnAssessments();
    }


    @And("user clicks on the {string}")
    public void userClicksOnThe(String assessmentType) {
        if(Objects.equals(assessmentType, "Jump Higher Assessment")){
            assessmentSelection.clickOnJumpHigherAssessment();
            jumpHigherAssessment.isUserInJumpHigherAssessmentPage();
        }
    }
}
