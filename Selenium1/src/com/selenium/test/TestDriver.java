package com.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestDriver {
    private String driveUrl = "C:\\chromedriver-win64\\chromedriver.exe";
    public TestDriver(){}
    public ChromeDriver getDriver()
    {
        System.setProperty("webdriver.chrome.driver",driveUrl);
        return new ChromeDriver();
    }
}
