package pages.pagesappium;

import main.runnerappium.CucumberRunner;
import org.openqa.selenium.By;

public class Insights extends CucumberRunner {
    By textInsightTitle = By.xpath("//XCUIElementTypeStaticText[@name=\"Insights\"]");
    By textDefaultInsightMessage = By.xpath("//XCUIElementTypeStaticText[@name=\"See your jump insights\"]");
    By buttonStartAssessment = By.xpath("//XCUIElementTypeButton[@name=\"Start Assessments\"]");
    By menuButtonInsight = By.xpath("//XCUIElementTypeButton[@name=\"Insights\"]");


    public void verifyLandingInsight(){
        isElementVisible(textInsightTitle);
    }

    public void checkForDefaultInsightMessageAndButton(){
        isElementVisible(textDefaultInsightMessage);
        isElementVisible(buttonStartAssessment);
    }


}
