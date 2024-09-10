package main.utilities;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

import java.io.File;
import java.net.URL;

public class Hooks {
    private static AppiumDriverLocalService service;
    private static String APPIUM_SERVER_URL;

    public static void startAppiumServer() {
        System.out.println(String.format("Start local Appium server"));
        service = new AppiumServiceBuilder().withAppiumJS(new File("//usr//local//lib//node_modules//appium//build//lib//main.js"))
                .withIPAddress("127.0.0.1").usingPort(4723).build();
        service.start();


        APPIUM_SERVER_URL = service.getUrl().toString();
        System.out.println(String.format("Appium server started on url: '%s'",
                service.getUrl().toString()));
    }

    public static void stopAppiumServer() {
        if (null != service) {
            System.out.println(String.format("Stopping the local Appium server running on: '%s'",
                    service.getUrl()
                            .toString()));
            service.stop();
            System.out.println("Is Appium server running? " + service.isRunning());
        }
    }

    public static URL getAppiumServerUrl() {
        System.out.println("Appium server url: " + service.getUrl());
        return service.getUrl();
    }
}