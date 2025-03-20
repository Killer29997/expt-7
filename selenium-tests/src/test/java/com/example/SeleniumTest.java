package com.example.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class SeleniumTest {
    private WebDriver driver;
    private String testUrl = System.getProperty("testUrl", "https://youtube.com");
    private String browser = System.getProperty("browser", "chrome");
    private String driversPath = "C:\\Users\\PSYCHOPENGUIN\\Downloads\\drivers";

    @BeforeMethod
    public void setup() {
        testUrl = System.getProperty("testUrl", "").trim();
        if (testUrl.isEmpty()) {
            testUrl = "https://youtube.com";
        }
        browser = System.getProperty("browser", "chrome").trim();
        if (browser.isEmpty()) {
            browser = "chrome";
        }
        if (!testUrl.toLowerCase().startsWith("http://") && !testUrl.toLowerCase().startsWith("https://")) {
            testUrl = "https://" + testUrl;
        }
        switch (browser.toLowerCase()) {
            case "firefox":
                System.setProperty("webdriver.gecko.driver", driversPath + File.separator + "geckodriver.exe");
                driver = new FirefoxDriver();
                break;
            case "chrome":
            default:
                System.setProperty("webdriver.chrome.driver", driversPath + File.separator + "chromedriver.exe");
                driver = new ChromeDriver();
                break;
        }
    }

    @Test
    public void testWebsiteTitle() throws Exception {
        driver.get(testUrl);
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        Files.copy(screenshot.toPath(), Path.of("screenshot.png"), StandardCopyOption.REPLACE_EXISTING);
        Assert.assertFalse(driver.getTitle().isEmpty(), "Page title should not be empty");
    }

    @AfterMethod
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
