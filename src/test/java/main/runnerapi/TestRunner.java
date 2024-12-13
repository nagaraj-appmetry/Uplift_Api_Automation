package main.runnerapi;

import main.utilities.utilitiesapi.LoggerUtil;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import stepdefinitions.stepdefinitionsapi.Hooks;

@CucumberOptions(
        features = {"src/test/resources/features/featuresapi/FacilitateAssessment.feature"},
        glue = "stepdefinitions.stepdefinitionsapi",
        publish = true,
        plugin = { "pretty","html:target/cucumber-reports/cucumber_report.html",
                //"com.epam.reportportal.cucumber.StepReporter",
                "main.utilities.StepDetailsUtil"},
       // tags = "@GET",
        monochrome = true
)

public class TestRunner extends AbstractTestNGCucumberTests {
    @BeforeSuite
    public void setup() {
        LoggerUtil.clearLogFile();

    }

    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        Object[][] scenarios = super.scenarios();
        for (int i = 0; i < scenarios.length; i++) {
            if (i > 0) {
                // Introduce a delay between feature files
                try {
                    Thread.sleep(10000);
                    // Delay of 5 seconds
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return scenarios;
    }

        @AfterSuite
        public void tearDown () {
            LoggerUtil.log("Test suite execution completed.");
        }
}


