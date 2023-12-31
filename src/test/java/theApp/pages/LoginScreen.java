package theApp.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;

public class LoginScreen extends TheAppPageObject {
    private static final Logger logger = LogManager.getLogger("myTestLog4j");
    public LoginScreen(AppiumDriver driver) {
        super(driver);
    }

    @AndroidFindBy(accessibility = "username")
    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeTextField[`label == \"username\"`]")
    private WebElement txtUsername;

    @AndroidFindBy(accessibility = "password")
    @iOSXCUITFindBy(iOSNsPredicate = "label == 'password' AND name == 'password' AND value == 'Password'")
    private WebElement txtPassword;

    @AndroidFindBy(accessibility = "loginBtn")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeOther[@name='loginBtn' and @accessible='true']")
    private WebElement btnLogin;

    @AndroidFindBy(id = "android:id/message")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name='Invalid login credentials, please try again']")
    private WebElement loginFailedMessage;

    public void login(String username, String pass) {
        logger.debug("Log in with account " + username + "/" + pass);
        elementHelper.inputText(txtUsername, username);
        elementHelper.inputText(txtPassword, pass);
        elementHelper.click(btnLogin);
    }

    public String getFailureMessage() {
        logger.debug("Get the failure message");
        waitHelper.waitUntilElementDisplayed(loginFailedMessage);
        return elementHelper.getText(loginFailedMessage);
    }

    public boolean displayFailureMessage() {
        logger.debug("Verify whether the failure message is displayed?");
        waitHelper.waitUntilElementDisplayed(loginFailedMessage);
        return loginFailedMessage.isDisplayed();
    }

}
