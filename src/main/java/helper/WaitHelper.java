package helper;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitHelper {

    private AppiumDriver driver;

    public WaitHelper(AppiumDriver driver) {
        this.driver = driver;
    }

    //This method is used to wait an element until it is displayed
    public WebElement waitUntilElementDisplayed(WebElement element) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(60))
                .pollingEvery(Duration.ofMillis(200))
                .ignoring(StaleElementReferenceException.class);

        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    //This method is used to wait an element until it is clickable
    public WebElement waitUntilElementCanBeClicked(WebElement element) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(60))
                .pollingEvery(Duration.ofMillis(200))
                .ignoring(StaleElementReferenceException.class);
        return wait.until(ExpectedConditions.elementToBeClickable(element));

    }

    public void waitUntilElementVisibleByLocator(By byLocator) {
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(60), Duration.ofSeconds(200))
                .ignoring(StaleElementReferenceException.class);
        wait.until(ExpectedConditions.visibilityOfElementLocated(byLocator));
    }

    public void waitUntilElementDisappear(By by) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(60))
                .pollingEvery(Duration.ofMillis(200))
                .ignoring(StaleElementReferenceException.class);

        wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

}