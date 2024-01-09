package theApp;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import theApp.pages.DashBoard;
import theApp.pages.PhotoDemo;
import utilities.ImageUtil;

public class TC004_VisualTestWithImagePlugin extends BaseSetup {

    private DashBoard dashboard;
    private PhotoDemo photoDemo;

    @BeforeMethod
    public void setup() {
        dashboard = new DashBoard(appiumDriver);
        photoDemo = new PhotoDemo(appiumDriver);
    }

    @Test(enabled = false)
    public void findImageMatchingWithSpecificThreshold() throws InterruptedException {

        printExecutionLogs("Select Photo Demo");
        dashboard.selectPhotoDemo();

        Thread.sleep(2000);
        printExecutionLogs("Select the image matching with specific threshold");
        ImageUtil.tapOnImage(appiumDriver, ImageUtil.getImageFile("2_tanker_ships.PNG"), 0.8);
        Thread.sleep(3000);

        printExecutionLogs("Verify the alert text message");
        String alertText = appiumDriver.switchTo().alert().getText();
        Assert.assertTrue(alertText.contains("tanker ships"));
    }

    @Test
    public void findPhoto() {

        printExecutionLogs("Select Photo Demo");
        dashboard.selectPhotoDemo();

        printExecutionLogs("Verify that the photo is displayed");
        Assert.assertTrue(photoDemo.isPhotoDisplayed(), "The photo is NOT found");
    }
}
