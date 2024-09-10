package pages.pagesappium;

import main.runnerappium.CucumberRunner;
import org.openqa.selenium.By;
import org.testng.Assert;

public class JumpHigherAssessment extends CucumberRunner {
    String movementNameXpath = "//XCUIElementTypeStaticText[contains(@name, \"%s\")]";
    String movementRepsXpath = "//XCUIElementTypeStaticText[contains(@name, \"%s\")]/following-sibling::XCUIElementTypeStaticText[1]";
    By jumpAssessmentTitle = By.xpath("//XCUIElementTypeStaticText[@name=\"Jump Higher Assessment\"]");
    By textAssessmentTag = By.xpath("//XCUIElementTypeStaticText[@name=\"POWER, STABILITY & MOBILITY\"]");
    By buttonStartAssessment = By.xpath("//XCUIElementTypeButton[@name=\"Start Assessment\"]");


    public void isUserInJumpHigherAssessmentPage(){
        isElementVisible(jumpAssessmentTitle);
        isElementVisible(textAssessmentTag);
    }

    public void verifyMovementListAndReps(String movementName,String reps){
        isElementVisible(returnParameterisedXpath(movementNameXpath, movementName));
        Assert.assertEquals(getTextFromElement(returnParameterisedXpath(movementRepsXpath,movementName)),reps);
    }

    public void clickStartAssessment(){
        clickElement(buttonStartAssessment);
    }
}
