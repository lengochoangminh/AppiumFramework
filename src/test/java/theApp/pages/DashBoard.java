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

    public DashBoard(AppiumDriver driver) {
        super(driver);
    }

    public void selectLoginScreen() {
        logger.debug("Select Login Screen from Dashboard");
        elementHelper.click(logoScreen);
    }

    public void selectWebViewDemo() {
        logger.debug("Select WebView Demo from Dashboard");
        elementHelper.click(webViewDemo);
    }
}
