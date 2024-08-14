package com.example.testrunners;

import com.example.utilities.LoggerUtil;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/resources/features-api/GroupManagement.feature",
        glue = "com.example.stepdefs",
        plugin = {
                "pretty",
                "html:target/cucumber-reports.html"
        },
       // tags = "@GET",
        monochrome = true
)

public class TestRunner extends AbstractTestNGCucumberTests {
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
