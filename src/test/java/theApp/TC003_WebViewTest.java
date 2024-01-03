package theApp;

import org.testng.Assert;
import org.testng.annotations.Test;
import theApp.pages.DashBoard;
import theApp.pages.WebviewDemo;

public class TC003_WebViewTest extends BaseSetup {

    private DashBoard dashboard;
    private WebviewDemo webviewDemo;

    @Test(enabled = true)
    public void verify_to_open_the_web_view() {
        dashboard = new DashBoard(appiumDriver);
        webviewDemo = new WebviewDemo(appiumDriver);

        executionLogs("Select Webview Demo Menu");
        dashboard.selectWebViewDemo();

        executionLogs("Enter & navigate to the URL");
        webviewDemo.navigateURL("https://www.google.com");

        executionLogs("Verify whether the failure message is displayed?");
        Assert.assertTrue(webviewDemo.displayFailureMessage());
    }
}
