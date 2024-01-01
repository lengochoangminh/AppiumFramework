package theApp;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import constants.LogConstants;
import dataHandler.ConfigurationPropertiesReader;
import dataHandler.EnvironmentYAMLReader;
import enumerations.EnvironmentType;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import utilities.ConvertUtil;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class BaseSetup {
    public static final Logger logger = LogManager.getLogger("myTestLog4j");
    public static String executionOS;
    public static AppiumDriver appiumDriver = null;

    public static EnvironmentYAMLReader environmentYAMLReader;
    public static ConfigurationPropertiesReader configurationPropertiesReader;

    protected void setupDriver() throws MalformedURLException {
        logger.debug("setupDriver");
        if (appiumDriver != null) {
            return;
        }
        try {
            executionOS = System.getProperty("platform").toUpperCase();
        } catch (NullPointerException e) {
            executionOS = "IOS";
        }

        DesiredCapabilities capabilities = new DesiredCapabilities();
        switch (executionOS) {
            case "ANDROID":
                String appPath = System.getProperty("os.name").equals("Windows 10")
                        ? configurationPropertiesReader.getInstance().getProperty("apkPathInWindows")
                        : configurationPropertiesReader.getInstance().getProperty("apkPathInMacOS");

                UiAutomator2Options options = new UiAutomator2Options();
                options.setDeviceName("emulator-5554");
                options.setPlatformVersion("13");
                options.autoGrantPermissions();
                options.setApp(System.getProperty("user.dir") + String.format(appPath, ""));
                appiumDriver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
                break;
            case "IOS":
                String APP_IOS = System.getProperty("os.name").equals("Windows 10")
                        ? configurationPropertiesReader.getInstance().getProperty("ipaPathInWindows")
                        : configurationPropertiesReader.getInstance().getProperty("ipaPathInMacOS");

                XCUITestOptions xCUITestOptions = new XCUITestOptions();
                xCUITestOptions.setDeviceName("iPhone 14");
                xCUITestOptions.setPlatformVersion("16.4");
                xCUITestOptions.setApp(System.getProperty("user.dir") + String.format(APP_IOS, ""));
                appiumDriver = new IOSDriver(new URL("http://127.0.0.1:4723"), xCUITestOptions);
                break;
        }

        appiumDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }

    public void quitDriver() {
        if (appiumDriver != null) {
            appiumDriver.quit();
            appiumDriver = null;
        }
    }

    // Extent Report
    public static ExtentSparkReporter htmlReporter;
    public static ExtentReports extent;
    public static ExtentTest test;

    public void initializeReport() {
        logger.debug("Initialize Extent Report");
        LocalDateTime d = LocalDateTime.now();
        String extentReportName = "extentReport_" + d.toString().replace(":", "_").replace(" ", "_") + ".html";

        htmlReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "/Reports/" + extentReportName);
        htmlReporter.config().setDocumentTitle("Automation Report");
        htmlReporter.config().setReportName("Test Report");
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    public static String CaptureScreenshot() throws IOException {
        logger.debug("CaptureScreenshot");
        String FileSeparator = System.getProperty("file.separator");
        String Extent_report_path = "." + FileSeparator + "Reports";
        String ScreenshotPath = Extent_report_path + FileSeparator + "screenshots";

        File src = ((TakesScreenshot) appiumDriver).getScreenshotAs(OutputType.FILE);
        String screenshotName = "screenshot" + Math.random() + ".png";
        String screenshotpath = ScreenshotPath + FileSeparator + screenshotName;

        FileUtils.copyFile(src, new File(screenshotpath));
        return "." + FileSeparator + "screenshots" + FileSeparator + screenshotName;
    }

    /**
     * Data Handler - Load the environment configurations & Test data based on the specific environment
     */
    protected static Map<Object, Object> data;

    private static void setupTestData() throws IOException {
        String currentEnvParam = System.getProperty("env");
        if (currentEnvParam != null) {
            logger.info("Loading the configurations & Test data for the environment " + currentEnvParam.toUpperCase());
            switch (currentEnvParam.toLowerCase()) {
                case "demo":
                    environmentYAMLReader = EnvironmentYAMLReader.getInstance(EnvironmentType.DEMO);
                    data = ConvertUtil.convertJsonFileToMap(LogConstants.DEMO_TEST_DATA_NAME);
                    break;
                case "prod":
                    environmentYAMLReader = EnvironmentYAMLReader.getInstance(EnvironmentType.PRODUCTION);
                    data = ConvertUtil.convertJsonFileToMap(LogConstants.PROD_TEST_DATA_NAME);
                    break;
                default:
                    environmentYAMLReader = EnvironmentYAMLReader.getInstance(EnvironmentType.STAGING);
                    data = ConvertUtil.convertJsonFileToMap(LogConstants.STAGE_TEST_DATA_NAME);
                    break;
            }
        } else {
            logger.info("Loading the configurations & Test data for the default environment " + LogConstants.STAGE_TEST_DATA_NAME);
            data = ConvertUtil.convertJsonFileToMap(LogConstants.STAGE_TEST_DATA_NAME);
            environmentYAMLReader = EnvironmentYAMLReader.getInstance(EnvironmentType.STAGING);
        }
    }

    @BeforeSuite
    public void startServer() throws IOException {
        initializeReport();
        setupTestData();
    }

    @BeforeMethod
    public void setUp() throws Exception {
        setupDriver();
    }

    @AfterMethod
    public void TearDown() {
        logger.debug("TearDown");
        quitDriver();
    }
}
