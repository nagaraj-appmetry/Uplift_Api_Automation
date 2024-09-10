package stepdefinitions.stepdefinitionsappium;

import io.cucumber.java.en.And;

public class MovementIntroductionStepDefinitions extends StepDefMaster{

    @And("user lands on {string} page which would be the {string}")
    public void checkIfUserInMovementIntro(String movementName, String movementCounter){
        movementIntroduction.isMovementIntroProvided(movementName,movementCounter);
    }

    @And("user clicks on the Arrow")
    public void moveToRecordScreen(){
        movementIntroduction.clickRightArrow();
    }

}
