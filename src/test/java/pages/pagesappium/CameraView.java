package pages.pagesappium;
import main.runnerappium.CucumberRunner;
import org.openqa.selenium.By;
import org.testng.Assert;

public class CameraView extends CucumberRunner {
    String textAssessmentTitle = "//XCUIElementTypeStaticText[@name=\"%s\"]";
    String textMovementTitle = "//XCUIElementTypeStaticText[@name=\"%s\"]";
    By textCapturesRemaining = By.xpath("//XCUIElementTypeStaticText[contains(@name, \"remaining\")]");
    By buttonRecord = By.xpath("//XCUIElementTypeButton[@name=\"List\"]/preceding-sibling::XCUIElementTypeOther[2]/XCUIElementTypeButton");
    By buttonStopRecording = By.xpath("//XCUIElementTypeButton[@value=\"1\"]");
    By imageVideoThumbnail = By.xpath("//XCUIElementTypeButton[@name=\"trash\"]/preceding-sibling::XCUIElementTypeImage");
    By buttonDeleteVideo = By.xpath("//XCUIElementTypeButton[@name=\"trash\"]");
    By buttonNextMovement = By.xpath("//XCUIElementTypeButton[@name=\"Right\"]");
    By textMovementCompleted = By.xpath("//XCUIElementTypeStaticText[@name=\"Movement Completed!\"]");
    By buttonAddMedia = By.xpath("//XCUIElementTypeStaticText[@name=\"Media\"]/parent::XCUIElementTypeButton");
    By overlayMedia = By.xpath("//XCUIElementTypeNavigationBar[@name=\"Videos\"]");
    By dummyVideo = By.xpath("//XCUIElementTypeImage[@name=\"Video, two seconds, 28 June, 16:25\"]");
    By buttonAddSelectedVideo = By.xpath("//XCUIElementTypeButton[@name=\"Add\"]");
    By textGreatJob = By.xpath("//XCUIElementTypeStaticText[@name=\"Great Job\"]");
    By buttonViewMyResults = By.xpath("//XCUIElementTypeButton[@name=\"View My Results\"]");


    public void areKeyComponentsPresent(String assessmentTitle, String movementTitle, String clipsRemaining) {
        isElementVisible(returnParameterisedXpath(textAssessmentTitle, assessmentTitle));
        isElementVisible(returnParameterisedXpath(textMovementTitle, movementTitle));
        Assert.assertEquals(getTextFromElement(textCapturesRemaining),clipsRemaining);
    }


    public void recordAVideo(){
        Assert.assertTrue(isElementAbsent(buttonStopRecording));
        clickElement(buttonRecord);
        isElementVisible(buttonStopRecording);

    }

    public void stopRecordingAVideo(){
        clickElement(buttonStopRecording);
        Assert.assertTrue(isElementAbsent(buttonStopRecording));
    }

    public void checkIfVideosRecorded(){
        isElementPresent(imageVideoThumbnail);
        isElementVisible(buttonDeleteVideo);

    }

    public void deleteVideo(){
        clickElement(buttonDeleteVideo);
    }
    public void checkIfVideosRemainingUpdated(String clipsRemaining){
        Assert.assertEquals(clipsRemaining, getTextFromElement(textCapturesRemaining));
    }

    public void checkIfMovementCompleted(){
        isElementVisible(buttonNextMovement);
        isElementVisible(textMovementCompleted);
    }

    public void clickNextMovement(){
        clickElement(buttonNextMovement);
    }

    public void clickAddMedia(){
        clickElement(buttonAddMedia);
    }

    public void chooseVideo() throws InterruptedException {
        isElementVisible(overlayMedia);
        isElementVisible(dummyVideo);
        clickElement(dummyVideo);
        Thread.sleep(3000);
        clickElement(buttonAddSelectedVideo);
    }

    public void checkProgressionCompleted(){
        isElementVisible(textGreatJob);
        isElementVisible(buttonViewMyResults);
    }

    public void clickViewResults(){
        clickElement(buttonViewMyResults);
    }

}
