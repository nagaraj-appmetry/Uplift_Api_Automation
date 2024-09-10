package pages.pagesappium;

import main.runnerappium.CucumberRunner;
import org.openqa.selenium.By;

public class AssessmentSelection extends CucumberRunner {
    By titleAssessments = By.xpath("//XCUIElementTypeStaticText[@name=\"Assessments\"]");
    By jumpHigherAssessment = By.xpath("//XCUIElementTypeScrollView/XCUIElementTypeOther[1]/XCUIElementTypeOther");
    By menuButtonAssessment = By.xpath("//XCUIElementTypeButton[@name=\"Assessments\"]");

    public void clickMenuAssessment(){
        clickElement(menuButtonAssessment);
    }

    public void verifyIfUserOnAssessments(){
        isElementVisible(titleAssessments);
    }

    public void clickOnJumpHigherAssessment(){
        clickElement(jumpHigherAssessment);

    }
}
