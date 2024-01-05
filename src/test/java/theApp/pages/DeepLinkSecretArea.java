package theApp.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;

public class DeepLinkSecretArea extends TheAppPageObject{

    private static final Logger logger = LogManager.getLogger("myTestLog4j");
    public DeepLinkSecretArea(AppiumDriver driver) {
        super(driver);
    }

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Secret Area']")
    @iOSXCUITFindBy(accessibility = "Secret Area")
    private WebElement txtSecretArea;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='You are logged in as alice']")
    @iOSXCUITFindBy(accessibility = "You are logged in as alice")
    private WebElement txtMessage;

    public boolean isSecretAreaDisplayed() {
        logger.debug("Verify whether the user navigates to Secret Area?");
        waitHelper.waitUntilElementDisplayed(txtSecretArea);
        waitHelper.waitUntilElementDisplayed(txtMessage);
        return txtSecretArea.isDisplayed() && txtMessage.isDisplayed();
    }
}
