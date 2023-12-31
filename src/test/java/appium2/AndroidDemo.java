package appium2;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
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
    public void loginScreen_verify_to_show_error_message_for_invalid_credential() throws InterruptedException {
        // Find element by AccessibilityId
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                new AppiumBy.ByAccessibilityId("Login Screen"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                new AppiumBy.ByAccessibilityId("username"))).sendKeys("admin");

        // Find element by AndroidUIAutomator
        driver.findElement(new AppiumBy.ByAndroidUIAutomator(
                "new UiSelector().text(\"Password\").className(\"android.widget.EditText\")")).sendKeys("admin");

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                new AppiumBy.ByAccessibilityId("loginBtn"))).click();

        // Find element by ID
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                new By.ById("android:id/message")));
        Assert.assertEquals(errorMessage.getText(), "Invalid login credentials, please try again");
    }

    @AfterMethod
    public void teardown() {
        driver.quit();
    }
}
