package theApp.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;

public class DashBoard extends TheAppPageObject {
    private static final Logger logger = LogManager.getLogger("myTestLog4j");

    @AndroidFindBy(accessibility = "Login Screen")
    @iOSXCUITFindBy(accessibility = "Login Screen")
    private WebElement logoScreen;

    @AndroidFindBy(accessibility = "Webview Demo")
    @iOSXCUITFindBy(accessibility = "Webview Demo")
    private WebElement webViewDemo;

    @AndroidFindBy(accessibility = "Photo Demo")
    @iOSXCUITFindBy(accessibility = "Photo Demo")
    private WebElement photoDemo;

    public DashBoard(AppiumDriver driver) {
        super(driver);
    }

    public void selectLoginScreen() {
        waitHelper.waitUntilElementDisplayed(logoScreen);
        elementHelper.click(logoScreen);
    }

    public void selectWebViewDemo() {
        waitHelper.waitUntilElementDisplayed(webViewDemo).click();
    }

    public void selectPhotoDemo() {
        waitHelper.waitUntilElementDisplayed(photoDemo).click();
    }
}
