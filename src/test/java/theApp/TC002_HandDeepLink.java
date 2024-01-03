package theApp;

import org.testng.Assert;
import org.testng.annotations.Test;
import theApp.pages.DeepLinkSecretArea;

public class TC002_HandDeepLink extends BaseSetup {

    @Test
    public void verify_the_user_navigate_by_deep_link() throws InterruptedException {

        String AUTH_USER = "alice";
        String AUTH_PASS = "mypassword";
        String deepLink = "theapp://login/" + AUTH_USER + "/" + AUTH_PASS;

        executionLogs("Navigate by the deep link");
        DeepLinkSecretArea deepLinkSecretArea = new DeepLinkSecretArea(appiumDriver);
        appiumDriver.get(deepLink);

        executionLogs("Verify the Secrect Area is displayed");
        Assert.assertTrue(deepLinkSecretArea.isSecretAreaDisplayed());
    }

}
