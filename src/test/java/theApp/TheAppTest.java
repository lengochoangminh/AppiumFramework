package theApp;

import constants.TheAppMessages;
import org.testng.Assert;
import org.testng.annotations.Test;
import theApp.pages.DashBoard;
import theApp.pages.LoginScreen;

public class TheAppTest extends BaseSetup {
    private LoginScreen loginPage;

    @Test()
    public void loginScreen_verify_to_show_error_message_for_invalid_credential() {
        DashBoard dashboard = new DashBoard(appiumDriver);
        dashboard.selectLoginScreen();

        loginPage = new LoginScreen(appiumDriver);
        loginPage.login("admin", "admin");

        logger.debug("Verify the failure message is displayed");
        if (executionOS == "ANDROID")
            Assert.assertEquals(loginPage.getFailureMessage(), TheAppMessages.INVALID_LOGIN_ERROR_MESSAGE);
        else if (executionOS == "IOS")
            Assert.assertTrue(loginPage.displayFailureMessage());
    }
}
