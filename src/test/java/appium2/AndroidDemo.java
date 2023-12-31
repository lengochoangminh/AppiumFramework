package appium2;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class AndroidDemo {

    protected static AndroidDriver driver = null;
    public WebDriverWait wait;

    @BeforeMethod
    public void setup() throws MalformedURLException {

        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName("emulator-5554");
        options.setPlatformVersion("13");
        options.autoGrantPermissions();
//        options.setCapability("appPackage", "");
//        options.setCapability("appActivity", "");

        String appPath = System.getProperty("os.name").equals("Windows 10") ? "\\apps\\TheApp-v1.12.0.apk" : "/apps/TheApp-v1.12.0.apk";
        options.setApp(System.getProperty("user.dir") + String.format(appPath, ""));

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test()
    public void testcase_001() throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(new AppiumBy.ByAccessibilityId("Photo Demo"))).click();
        Thread.sleep(3000);
    }

    @AfterMethod
    public void teardown() {
        driver.quit();
    }
}
