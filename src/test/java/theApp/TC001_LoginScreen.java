package theApp;

import constants.TheAppMessages;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import theApp.pages.DashBoard;
import theApp.pages.LoginScreen;

public class TC001_LoginScreen extends BaseSetup {
    private LoginScreen loginPage;
    private DashBoard dashboard;

    @BeforeMethod
    public void setup() {
        dashboard = new DashBoard(appiumDriver);
        loginPage = new LoginScreen(appiumDriver);
    }

    @Test(enabled = true)
    public void verify_the_user_navigate_to_login_screen() {

        executionLogs("Select Login Screen from Dashboard");
        dashboard.selectLoginScreen();

        executionLogs("Verify Login Screen is displayed");
        Assert.assertTrue(loginPage.isOnLoginScreen());
    }

    @Test(enabled = true)
    public void verify_to_show_error_message_for_invalid_credential() {

        executionLogs("Select Login Screen from Dashboard");
        dashboard.selectLoginScreen();

        executionLogs("Login with the username & password");
        loginPage.login("admin", "admin");

        executionLogs("Verify the failure message is displayed");
        if (executionOS == "ANDROID") {
            Assert.assertEquals(loginPage.getFailureMessage(), TheAppMessages.INVALID_LOGIN_ERROR_MESSAGE);
        } else if (executionOS == "IOS")
            Assert.assertTrue(loginPage.displayFailureMessage());
    }
}
