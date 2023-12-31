package theApp.pages;

import helper.ElementHelper;
import helper.WaitHelper;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;

public class TheAppPageObject {
    protected AppiumDriver driver;
    protected WaitHelper waitHelper;
    protected ElementHelper elementHelper;

    public TheAppPageObject(AppiumDriver driver) {
        this.driver = driver;
        waitHelper = new WaitHelper(driver);
        elementHelper = new ElementHelper(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(Long.parseLong("10"))), this);
    }
}
