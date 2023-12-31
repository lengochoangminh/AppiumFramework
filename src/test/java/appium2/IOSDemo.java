package appium2;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class IOSDemo {

    protected static IOSDriver driver = null;
    public WebDriverWait wait;

    @BeforeMethod
    public void setup() throws MalformedURLException {

        XCUITestOptions options = new XCUITestOptions();
        options.setDeviceName("iPhone 14");
        options.setPlatformVersion("16.4");
//        options.setUdid("00008110-000174EE1EF2801E");

        String appPath = System.getProperty("os.name").equals("Windows 10") ? "\\apps\\TheApp.app" : "/apps/TheApp.app";
        options.setApp(System.getProperty("user.dir") + String.format(appPath, ""));

        driver = new IOSDriver(new URL("http://127.0.0.1:4723"), options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test()
    public void TestScenario() throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(new AppiumBy.ByAccessibilityId("Photo Demo"))).click();
        Thread.sleep(3000);
    }

    @AfterMethod
    public void teardown() {
        driver.quit();
    }
}
