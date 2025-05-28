package com.test.poc;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebElement;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

public class AppTest {

    // Declare a field to store the AndroidDriver instance
    private AndroidDriver driver;
    private IOSDriver iosDriver;
    String os = "android_native";
    // String os = "android_web";
    // String os = "ios_native";
    // String os = "ios_web";

    @Before
    public void setup_SauceLabs() {
        MutableCapabilities capabilities = new MutableCapabilities();

        // Switch case to set capabilities based on the OS (android_native, android_web, ios_native, ios_web)
        switch (os) {
            case "android_native":
                try {
                    capabilities.setCapability("platformName", "android");
                    capabilities.setCapability("appium:app", "storage:filename=Android-MyDemoAppRN.1.3.0.build-244.apk");
                    capabilities.setCapability("appium:platformVersion", "12.*"); 
                    capabilities.setCapability("appium:deviceName", "Samsung.*");
                    capabilities.setCapability("appium:automationName", "uiautomator2");
                    System.out.println("Android Native App capabilities set successfully.");
                } catch (Exception e) {
                    System.err.println("Error setting Android Native App capabilities: " + e.getMessage());
                    throw new RuntimeException("Failed to set Android Native App capabilities", e);
                } 
                break;
            case "android_web":
                try {
                    capabilities.setCapability("browserName", "chrome");
                    capabilities.setCapability("platformName", "android");
                    capabilities.setCapability("appium:platformVersion", "15"); 
                    capabilities.setCapability("appium:deviceName", "Samsung.*");
                    capabilities.setCapability("appium:automationName", "uiautomator2");
                    System.out.println("Android Web App capabilities set successfully.");
                } catch (Exception e) {
                    System.err.println("Error setting Android Web App capabilities: " + e.getMessage());
                    throw new RuntimeException("Failed to set Android Web App capabilities", e);
                }
                break;
            case "ios_native":
                try {
                    capabilities.setCapability("platformName", "ios");
                    capabilities.setCapability("appium:app", "storage:filename=iOS-Real-Device-MyRNDemoApp.1.3.0-162.ipa");
                    capabilities.setCapability("appium:platformVersion", "16");
                    capabilities.setCapability("appium:deviceName", "iPhone.*");
                    capabilities.setCapability("appium:automationName", "xcuitest");
                    System.out.println("iOS Native App capabilities set successfully.");
                } catch (Exception e) {
                    System.err.println("Error setting iOS Native App capabilities: " + e.getMessage());
                    throw new RuntimeException("Failed to set iOS Native App capabilities", e);
                }
                break;
            case "ios_web":
                try {
                    capabilities.setCapability("platformName", "ios");
                    capabilities.setCapability("appium:platformVersion", "16");
                    capabilities.setCapability("appium:deviceName", "iPhone.*");
                    capabilities.setCapability("appium:automationName", "xcuitest");
                    capabilities.setCapability("browserName", "safari");
                    System.out.println("iOS Web App capabilities set successfully.");
                } catch (Exception e) {
                    System.err.println("Error setting iOS Web App capabilities: " + e.getMessage());
                    throw new RuntimeException("Failed to set iOS Web App capabilities", e);
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid OS type: " + os);
        }
        // Set common capabilities
        HashMap<String, Object> sauceOptions = new HashMap<String, Object>();
        // appiumVersion is mandatory to use Appium 2 on Sauce Labs
        sauceOptions.put("appiumVersion", "2.0.0");
        if(os.equals("android_native") || os.equals("android_web")) {
            // Android-specific Sauce Labs options
            sauceOptions.put("build", "244");
        } else if(os.equals("ios_native") || os.equals("ios_web")) {
            // iOS-specific Sauce Labs options
            sauceOptions.put("build", "162");
        }

        // Add a unique name for the test with a timestamp
        sauceOptions.put("name", "Appium Test - " + os + " - " + System.currentTimeMillis());
        sauceOptions.put("networkCapture", true);
        sauceOptions.put("resigningEnabled", true);
        sauceOptions.put("vitals", true);
        sauceOptions.put("username", "Jocarsoli2001");
        sauceOptions.put("accessKey", "71f6843b-dfff-4220-aa49-e64c81034606");
        sauceOptions.put("deviceOrientation", "PORTRAIT");
        capabilities.setCapability("sauce:options", sauceOptions);

        // Start the session
        try {
            URL url = new URL("https://ondemand.us-west-1.saucelabs.com:443/wd/hub");

            // Initialize the driver based on the OS
            if(os.equals("android_native") || os.equals("android_web")) {
                // Initialize AndroidDriver
                driver = new AndroidDriver(url, capabilities);
                System.out.println("Android Driver initialized successfully.");
            } else if(os.equals("ios_native") || os.equals("ios_web")) {
                iosDriver = new IOSDriver(url, capabilities);
                System.out.println("iOS Driver initialized successfully.");
            }

            // get the URL of a site to test
            if(os.equals("android_web")){
                driver.get("https://www.google.com");
            } else if(os.equals("ios_web")){
                iosDriver.get("https://www.google.com");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        
    }

    @Test
    public void MainPOC() throws MalformedURLException {

        // replace with commands and assertions
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        if(os.equals("android_native")) {
            // Android-specific test commands
            try {
                WebElement element = driver.findElement(By.xpath("(//android.view.ViewGroup[@content-desc=\"store item\"])[1]/android.view.ViewGroup[1]/android.widget.ImageView"));
                element.click();
                WebElement element2 = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"open menu\"]/android.widget.ImageView"));
                element2.click();
                driver.executeScript("sauce:job-result=passed");
            } catch (Exception e) {
                e.printStackTrace();
                driver.executeScript("sauce:job-result=failed");
            }

        } else if(os.equals("ios_native")) {
            // iOS-specific test commands
            try {
                WebElement element = iosDriver.findElement(By.xpath("//XCUIElementTypeOther[@name=\"Sauce Labs Backpack\"]"));
                element.click();
                WebElement element2 = iosDriver.findElement(By.xpath("//XCUIElementTypeOther[@name=\"red circle\"]"));
                element2.click();
                WebElement element3 = iosDriver.findElement(By.xpath("//XCUIElementTypeOther[@name=\"counter plus button\"]"));
                element3.click();
                element3.click();
                element3.click();
                WebElement element4 = iosDriver.findElement(By.xpath("//XCUIElementTypeOther[@name=\"navigation back button\"]"));
                element4.click();
                iosDriver.executeScript("sauce:job-result=passed");
            } catch (Exception e) {
                e.printStackTrace();
                iosDriver.executeScript("sauce:job-result=failed");
            }
        }
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @After
    public void tearDown() {
        // Quit the driver
        if (os.equals("android_native") || os.equals("android_web")) {
            if (driver != null) {
                driver.quit();
            }
        } else if (os.equals("ios_native") || os.equals("ios_web")) {
            if (iosDriver != null) {
                iosDriver.quit();
            }
        }
    }

}

