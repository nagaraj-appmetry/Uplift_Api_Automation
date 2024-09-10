
# Coach takeoff Automation

Contains information on Coach takeoff automation.

## Contents
• Pre-requisites

• How to run

## Pre-requisites

1. Install Homebrew.
2. Install npm
3. Install appium with npm
4. Install Appium inspector
5. Install XCUITest with appium command
6. Install Xcode
7. Install Java(preferably Oracle's JAVA)
8. #### Setup  WDA
WebDriverAgent source is automatically downloaded as part of XCUITest driver package. appium driver install xcuitest installes the module in $APPIUM_HOME/node_modules/appium-xcuitest-driver/node_modules/appium-webdriveragent. APPIUM_HOME is ~/.appium by default.

In order to make sure that WDA source is configured properly:

• Open $APPIUM_HOME/node_modules/appium-xcuitest-driver/node_modules/appium-webdriveragent/WebDriverAgent.xcodeproj in Xcode

• Select WebDriverAgentRunner project

• Select your real phone/Simulator you'd like to run automated tests on as build target

• Select Product->Test from the main menu

Xcode should successfully build the project and install it on the real device/Simulator, so you'll see the icon of WebDriverAgentRunner application on the springboard.

Take a look at this video for reference: https://www.youtube.com/watch?v=8cEqR9a78pc
#### Note:Choose uplift's credentials for Signing and see if you can deploy the default integration app on a real device from the above video.

9. Enable developer mode in the target device.
10. Pull the code from the repository.
11. Install Docker
12. Install and start the report portal container (Follow: https://reportportal.io/installation)


## How to Run

1. Open the repo in any IDE and create a maven project with it.
2. Make sure to install all the Maven dependencies.
3. Once done, run the CucumberRunner as a testNG test.
4. Once the test is done, go to localhost:8080/ui to look at the reports.
5. Alternatively, you can find the reports on target/cucumber-reports
