package helper;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

public class ElementHelper {

    private AppiumDriver driver;
    private WaitHelper waitUtil;

    public ElementHelper(AppiumDriver driver) {
        this.driver = driver;
        this.waitUtil = new WaitHelper(driver);
    }

    public void click(WebElement element) {
        waitUtil.waitUntilElementCanBeClicked(element).click();
    }

//    public void replaceText(WebElement element, String text) {
//        ( waitUtil.waitUntilElementDisplayed(element)).replaceValue(text);
//    }

    public void inputText(WebElement element, String text) {
        waitUtil.waitUntilElementDisplayed(element).sendKeys(text);
    }

    public String getText(WebElement element) {
        return waitUtil.waitUntilElementDisplayed(element).getText();
    }

    public String getAttribute(WebElement element, String atr) {
        return waitUtil.waitUntilElementDisplayed(element).getAttribute(atr);
    }

    public boolean waitAndCheckElementDisplayed(WebElement element) {
        try {
            return waitUtil.waitUntilElementDisplayed(element).isDisplayed();
        } catch (NoSuchElementException ex) {
            return false;
        }

    }
}