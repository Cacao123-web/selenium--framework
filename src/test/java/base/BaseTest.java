package base;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseTest {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public WebDriver getDriver() {
        return driver.get();
    }

    @Parameters({"browser", "env"})
    @BeforeMethod
    public void setUp(@Optional("chrome") String browser,
                      @Optional("dev") String env) {

        boolean isCI = System.getenv("CI") != null;

        if (browser.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();

            if (isCI) {
                options.addArguments("--headless=new");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--window-size=1920,1080");
            } else {
                options.addArguments("--start-maximized");
            }

            driver.set(new ChromeDriver(options));

        } else if (browser.equalsIgnoreCase("firefox")) {
            FirefoxOptions options = new FirefoxOptions();

            if (isCI) {
                options.addArguments("-headless");
            }

            driver.set(new FirefoxDriver(options));

        } else {
            throw new IllegalArgumentException("Browser không được hỗ trợ: " + browser);
        }

        if (!browser.equalsIgnoreCase("chrome") || !isCI) {
            getDriver().manage().window().maximize();
        }
    }

    @AfterMethod
    public void tearDown(ITestResult result) {

        if (result.getStatus() == ITestResult.FAILURE) {
            takeScreenshot(result.getName());
        }

        if (getDriver() != null) {
            getDriver().quit();
            driver.remove();
        }
    }

    public void takeScreenshot(String testName) {
        try {
            TakesScreenshot ts = (TakesScreenshot) getDriver();
            File src = ts.getScreenshotAs(OutputType.FILE);

            String time = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            File dest = new File("target/screenshots/" + testName + "_" + time + ".png");

            dest.getParentFile().mkdirs();
            Files.copy(src.toPath(), dest.toPath());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}