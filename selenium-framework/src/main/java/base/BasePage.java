package base;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // click element
    public void waitAndClick(By locator){
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    // type text
    public void waitAndType(By locator, String text){
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(text);
    }

    // get text
    public String getText(By locator){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
    }

    // check visible
    public boolean isElementVisible(By locator){
        try{
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
        }catch(StaleElementReferenceException e){
            return false;
        }
    }

    // scroll
    public void scrollToElement(By locator){
        WebElement element = driver.findElement(locator);
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    // wait page load
    public void waitForPageLoad(){
        wait.until(webDriver ->
                ((JavascriptExecutor)webDriver).executeScript("return document.readyState").equals("complete"));
    }

    // get attribute
    public String getAttribute(By locator, String attribute){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getAttribute(attribute);
    }
}