package com.test.poc;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebElement;

import io.appium.java_client.android.AndroidDriver;

public class AppTest {

    // Declare a field to store the AndroidDriver instance
    private AndroidDriver driver;

    @Test
    public void setUp() throws MalformedURLException {

        MutableCapabilities capabilities = new MutableCapabilities();

        capabilities.setCapability("browserName", "chrome");
        capabilities.setCapability("platformName", "android");
        capabilities.setCapability("appium:app", "storage:filename=Android-MyDemoAppRN.1.3.0.build-244.apk"); // The filename of the mobile app
        // W3C Protocol is mandatory for Appium 2
        capabilities.setCapability("appium:platformVersion", "12");
        capabilities.setCapability("appium:deviceName", "Google_Pixel_5_free");
        // Mandatory for Appium 2
        capabilities.setCapability("appium:automationName", "uiautomator2");

        HashMap<String, Object> sauceOptions = new HashMap<String, Object>();
        // appiumVersion is mandatory to use Appium 2 on Sauce Labs
        sauceOptions.put("appiumVersion", "2.0.0");
        sauceOptions.put("build", "appium-build-60B44");
        sauceOptions.put("name", "Sauce Labs Appium Test");
        sauceOptions.put("username", "Jocarsoli2001");
        sauceOptions.put("accessKey", "71f6843b-dfff-4220-aa49-e64c81034606");
        sauceOptions.put("deviceOrientation", "PORTRAIT");
        capabilities.setCapability("sauce:options", sauceOptions);

        // Set username and password for Sauce Labs
        String username = "Jocarsoli2001";
        String accessKey = "71f6843b-dfff-4220-aa49-e64c81034606";

        // Start the session
        URL url = new URL("https://ondemand.us-west-1.saucelabs.com:443/wd/hub");
        AndroidDriver driver = new AndroidDriver(url, capabilities);

        // replace with commands and assertions
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
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
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        driver.quit();
    }

}

