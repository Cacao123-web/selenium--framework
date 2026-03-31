package base;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseTest {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public WebDriver getDriver(){
        return driver.get();
    }

    @Parameters({"browser","env"})
    @BeforeMethod
    public void setUp(@Optional("chrome") String browser,
                      @Optional("dev") String env){

        if(browser.equalsIgnoreCase("chrome")){
            driver.set(new ChromeDriver());
        }

        getDriver().manage().window().maximize();
    }

    @AfterMethod
    public void tearDown(ITestResult result) {

        if(result.getStatus() == ITestResult.FAILURE){
            takeScreenshot(result.getName());
        }

        if(getDriver()!=null){
            getDriver().quit();
        }
    }

    public void takeScreenshot(String testName){
        try{

            TakesScreenshot ts = (TakesScreenshot)getDriver();
            File src = ts.getScreenshotAs(OutputType.FILE);

            String time = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

            File dest = new File("target/screenshots/"+testName+"_"+time+".png");

            dest.getParentFile().mkdirs();

            Files.copy(src.toPath(), dest.toPath());

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}