package theApp;

import constants.TheAppMessages;
import org.testng.Assert;
import org.testng.annotations.Test;
import theApp.pages.DashBoard;
import theApp.pages.LoginScreen;
import theApp.pages.WebviewDemo;

public class TheAppTest extends BaseSetup {
    private LoginScreen loginPage;
    private DashBoard dashboard;
    private WebviewDemo webviewDemo;

    @Test(enabled = false)
    public void loginScreen_verify_to_show_error_message_for_invalid_credential() {
        dashboard = new DashBoard(appiumDriver);
        dashboard.selectLoginScreen();

        loginPage = new LoginScreen(appiumDriver);
        loginPage.login("admin", "admin");

        logger.debug("Verify the failure message is displayed");
        if (executionOS == "ANDROID")
            Assert.assertEquals(loginPage.getFailureMessage(), TheAppMessages.INVALID_LOGIN_ERROR_MESSAGE);
        else if (executionOS == "IOS")
            Assert.assertTrue(loginPage.displayFailureMessage());
    }

    @Test(enabled = true)
    public void webView_Demo() {
        dashboard = new DashBoard(appiumDriver);
        webviewDemo = new WebviewDemo(appiumDriver);

        dashboard.selectWebViewDemo();
        webviewDemo.navigateURL("https://www.google.com");
        Assert.assertTrue(webviewDemo.displayFailureMessage());
    }
}
