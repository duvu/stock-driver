package com.vd5.stock;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author beou on 11/19/19 16:54
 */
public class StockDriver {
    private static WebDriver driver;
    static void init() {
        if (SystemUtils.IS_OS_WINDOWS) {
            if (StringUtils.endsWith(SystemUtils.OS_ARCH, "64")) {
                System.setProperty("webdriver.gecko.driver", "bin/windows/x64/geckodriver.exe");
            } else {
                System.setProperty("webdriver.gecko.driver", "bin/windows/x32/geckodriver.exe");
            }
        } else if (SystemUtils.IS_OS_LINUX) {
            if (StringUtils.endsWith(SystemUtils.OS_ARCH, "64")) {
                System.setProperty("webdriver.gecko.driver", "bin/linux/x64/geckodriver");
            } else {
                System.setProperty("webdriver.gecko.driver", "bin/linux/x32/geckodriver");
            }
        } else if (SystemUtils.IS_OS_MAC) {
            System.setProperty("webdriver.gecko.driver", "bin/macos/geckodriver");
        }

        driver = new FirefoxDriver();
        waitForLoad(driver);
        driver.navigate().to("https://iboard.ssi.com.vn/bang-gia/vn30");

    }
    public static WebDriver getDriver() {
        return driver;
    }
    static void stop() {
        if (driver != null) {
            driver.quit();
        }
    }
    private static void waitForLoad(WebDriver driver) {
        ExpectedCondition<Boolean> pageLoadCondition = webDriver -> {
            System.out.println("Waiting ...");
            return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
        };
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(pageLoadCondition);
    }
}
