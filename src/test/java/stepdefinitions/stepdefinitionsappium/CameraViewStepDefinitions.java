package stepdefinitions.stepdefinitionsappium;

import io.cucumber.java.en.And;

public class CameraViewStepDefinitions extends StepDefMaster{

    @And("user records a video")
    public void recordAQuickVideo() throws InterruptedException {
        cameraView.recordAVideo();
        Thread.sleep(1500);
        cameraView.stopRecordingAVideo();
        cameraView.checkIfVideosRecorded();
    }

    @And("user should find the progression title {string}, movement title {string} and videos as {string}")
    public void userShouldFindTheProgressionTitleMovementTitleAndVideosAs(String progressionTitle, String movementTitle, String clipsRemaining) {
        cameraView.areKeyComponentsPresent(progressionTitle,movementTitle,clipsRemaining);
    }

    @And("the text should change to {string}")
    public void theTextShouldChangeTo(String clipsRemaining) {
        cameraView.checkIfVideosRemainingUpdated(clipsRemaining);
    }

    @And("user deletes a video")
    public void userDeletesAVideo() {
        cameraView.deleteVideo();
    }

    @And("user records a video post which the user should see movement completed")
    public void userRecordsAVideoPostWhichTheUserShouldSeeMovementCompleted() throws InterruptedException {
        recordAQuickVideo();
        cameraView.checkIfMovementCompleted();
    }

    @And("user moves on to next")
    public void userMovesOnToNext() {
        cameraView.clickNextMovement();
    }

    @And("user uploads a video from media")
    public void userUploadsAVideoFromMedia() throws InterruptedException {
        cameraView.clickAddMedia();
        cameraView.chooseVideo();
    }

    @And("user gets notified that progression is finished and can see his results.")
    public void userGetsNotifiedThatProgressionIsFinishedAndCanSeeHisResults() {
        cameraView.checkProgressionCompleted();
    }

    @And("user clicks on view my results button")
    public void userClicksOnViewMyResultsButton() {
        cameraView.clickViewResults();
    }
}
