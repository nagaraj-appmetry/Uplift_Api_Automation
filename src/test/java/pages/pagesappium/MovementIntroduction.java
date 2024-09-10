package pages.pagesappium;

import main.runnerappium.CucumberRunner;
import org.openqa.selenium.By;

public class MovementIntroduction extends CucumberRunner {
    String titleXpath = "//XCUIElementTypeStaticText[contains(@name ,\"%s\")]";
    String movementCounterXpath = "//XCUIElementTypeStaticText[@name=\"%s\"]";
    String nextArrow = "//XCUIElementTypeStaticText[@name=\"%s\"]/following-sibling::XCUIElementTypeButton[@name='Right']";
    static String lastUsedMovementCounter = "";

    public void isMovementIntroProvided(String movementName, String movementCounter){
        isElementVisible(returnParameterisedXpath(titleXpath , movementName));
        isElementVisible(returnParameterisedXpath(movementCounterXpath, movementCounter));
        lastUsedMovementCounter = movementCounter;
    }

    public void clickRightArrow(){
        clickElement(returnParameterisedXpath(nextArrow, lastUsedMovementCounter));
    }
}
