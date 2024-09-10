package stepdefinitions.stepdefinitionsappium;
import io.cucumber.java.AfterStep;
import main.utilities.ScreenshotUtil;
import main.utilities.StepDetailsUtil;

import static main.runnerappium.CucumberRunner.driver;

public class Hooks {


    @AfterStep
    public void afterEachStep() {
        ScreenshotUtil.addScreenShotToLogs(driver, StepDetailsUtil.stepName);
    }
}
