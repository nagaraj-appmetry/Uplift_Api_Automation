package com.example.testrunners;

import com.example.utilities.LoggerUtil;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/main/resources/features-api/GroupManagement.feature",
        glue = "com.example.stepdefs",
        publish = true,
        plugin = { "pretty","html:target/cucumber-reports/cucumber_report.html",
                "com.epam.reportportal.cucumber.StepReporter"},
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
        return super.scenarios();
    }

    @AfterSuite
    public void tearDown() {
        LoggerUtil.log("Test suite execution completed.");
    }
}
