package theApp.listener;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import theApp.BaseSetup;

import java.io.IOException;

public class ExtentReportListener extends BaseSetup implements ITestListener {
    String testFeature;

    @Override
    public void onTestStart(ITestResult result) {
        test = extent.createTest(testFeature + " > " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.log(Status.PASS, MarkupHelper.createLabel("Test case is PASSED", ExtentColor.GREEN));
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.log(Status.FAIL, result.getThrowable().getMessage());
        test.log(Status.FAIL, MarkupHelper.createLabel(result.getName().toUpperCase() + " FAIL", ExtentColor.RED));

        try {
            test.addScreenCaptureFromPath(CaptureScreenshot());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.log(Status.SKIP, MarkupHelper.createLabel(result.getName().toUpperCase() + " SKIPPED", ExtentColor.PURPLE));
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    @Override
    public void onStart(ITestContext context) {
        testFeature = context.getName();
    }

    @Override
    public void onFinish(ITestContext context) {
        if (extent != null) {
            extent.flush();
        }
    }
}
