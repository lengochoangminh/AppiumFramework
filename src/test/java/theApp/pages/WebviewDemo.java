package theApp.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;

public class WebviewDemo extends TheAppPageObject {

    private static final Logger logger = LogManager.getLogger("myTestLog4j");

    public WebviewDemo(AppiumDriver driver) {
        super(driver);
    }

    @AndroidFindBy(accessibility = "urlInput")
    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeTextField[`label == \"urlInput\"`]")
    private WebElement urlInput;

    @AndroidFindBy(accessibility = "navigateBtn")
    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeOther[`label == \"navigateBtn\"`][2]")
    private WebElement navigateBtn;

    @AndroidFindBy(accessibility = "clearBtn")
    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeOther[`label == \"clearBtn\"`][2]")
    private WebElement clearBtn;

    @AndroidFindBy(id = "android:id/alertTitle")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name='Sorry, you are not allowed to visit that url']")
    private WebElement alertTitle;

    public void navigateURL(String url) {
        logger.debug("Enter the URL and click Go to navigate");
        elementHelper.inputText(urlInput, url);
        elementHelper.click(navigateBtn);
    }

    public boolean displayFailureMessage() {
        logger.debug("Verify whether the failure message is displayed?");
        waitHelper.waitUntilElementDisplayed(alertTitle);
        return alertTitle.isDisplayed();
    }
}
