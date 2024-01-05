package theApp.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import utilities.ImageComparison;
import utilities.ImageUtil;

import java.io.File;
import java.util.List;

public class PhotoDemo extends TheAppPageObject {

    public PhotoDemo(AppiumDriver driver) {
        super(driver);
    }

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Photo Library. Tap a photo!']")
    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeStaticText[`label == \"Photo Library. Tap a photo!\"`]")
    private WebElement txtInstruct;

    @AndroidFindBy(xpath = "//android.widget.ImageView")
    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeOther[`label == \"Photo Library. Tap a photo!\"`]/XCUIElementTypeOther/XCUIElementTypeOther")
    private List<WebElement> photos;

    public boolean isPhotoDisplayed() {
        File baseImage = ImageUtil.getImageFile("2_tanker_ships.PNG");
        for (int i = 0; i < photos.size(); i++) {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            ImageUtil.cropByTarget(driver, screenshot, photos.get(i));

            if (ImageComparison.similar(driver, screenshot, baseImage, 0.8)) {
                int pos = i + 1;
                System.out.println("The photo is found at the position " + pos);
                return true;
            }
        }
        return false;
    }

}
