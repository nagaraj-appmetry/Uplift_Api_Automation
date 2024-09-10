package stepdefinitions.stepdefinitionsappium;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

import java.util.List;
import java.util.Map;

public class JumpHigherAssessmentStepDefinition extends StepDefMaster {

    @Then("user should see the following movements present")
    public void userVerifiesMovementsAndReps(DataTable dataTable){
        List<Map<String, String>> movementAndReps = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> movement : movementAndReps) {
            String movementName = movement.get("movementname");
            String reps = movement.get("reps");
            jumpHigherAssessment.verifyMovementListAndReps(movementName,reps);
            // Code to add user to your system
        }
        System.out.println("Monkey");
    }

    @And("user clicks on Start Assessment button")
    public void userClicksOnStartAssessmentButton() {
        jumpHigherAssessment.clickStartAssessment();
    }
}
