package utilities;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.imagecomparison.OccurrenceMatchingOptions;
import io.appium.java_client.imagecomparison.OccurrenceMatchingResult;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImageUtil {
    private final static Logger logger = Logger.getLogger(ImageUtil.class);
    private static final Pattern COMPILE = Pattern.compile(".", Pattern.LITERAL);

    public static File getImageFile(String key) {
        key = "/image_comparision/" + key;
        logger.info("located image at: " + key);
        URL resource = ImageUtil.class.getResource(key);
        if (resource == null) {
            key = COMPILE.matcher(key).replaceAll(
                    Matcher.quoteReplacement('_' + System.getProperty("platform").toLowerCase()) + '.');
            resource = ImageUtil.class.getResource(key);
        }

        return new File(resource.getFile());
    }

    public static void tapOnImage(AppiumDriver driver, File image, Double thresHold) {

        OccurrenceMatchingResult location = thresHold == 0.0
                ? findMatchedImageByThresholdRange(driver, image)
                : findMatchedImageBySpecificThreshold(driver, image, thresHold);

        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(screenshot);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Integer scaledFactor = calculateScaleFactor(driver, bufferedImage);
        int pointX = new Double(location.getRect().x / scaledFactor).intValue();
        int pointY = new Double(location.getRect().y / scaledFactor).intValue();
        int width = new Double(location.getRect().width / scaledFactor).intValue();
        int height = new Double(location.getRect().height / scaledFactor).intValue();

        TouchAction touchAction = new TouchAction((PerformsTouchActions) driver);
        touchAction.tap(PointOption.point(pointX + width / 2, pointY + height / 2)).perform();
    }

    public static OccurrenceMatchingResult findMatchedImageByThresholdRange(AppiumDriver driver, File partialImage) {
        OccurrenceMatchingResult result = null;
        double thresUnit = 0.1;
        double thresHold = 0.0;
        File screenshot = driver.getScreenshotAs(OutputType.FILE);
        for (int i = 10; i > 0; i--) {
            try {
                result = driver.findImageOccurrence(screenshot, partialImage, new OccurrenceMatchingOptions().withEnabledVisualization().withThreshold(thresUnit * i));
                thresHold = thresUnit * i;
                break;
            } catch (Exception e) {
                continue;
            }
        }
        if (result == null)
            throw new RuntimeException("Cannot find the matched image at a threshold as low as: " + thresHold);
        logger.info("Image was found at the highest threshold of: " + thresHold);
        return result;
    }

    public static OccurrenceMatchingResult findMatchedImageBySpecificThreshold(AppiumDriver driver, File partialImage, Double thresHold) {
        OccurrenceMatchingResult result = null;
        File screenshot = driver.getScreenshotAs(OutputType.FILE);
        try {
            result = driver.findImageOccurrence(screenshot, partialImage, new OccurrenceMatchingOptions().withEnabledVisualization().withThreshold(thresHold));
        } catch (Exception e) {
            throw new RuntimeException("Cannot find the matched image at a specific threshold: " + thresHold);
        }
        logger.info("Image was found at the specific threshold of: " + thresHold);
        return result;
    }

    public static Integer calculateScaleFactor(WebDriver driver, BufferedImage physicalScreenResolution) {
        final LogicalScreenResolution logicalScreenResolution = LogicalScreenResolution.of(driver);
        return System.getProperty("platform").toUpperCase().equals("IOS") ? physicalScreenResolution.getWidth() / logicalScreenResolution.getWidth() : 1;
    }

    /**
     * Crop the specified element in the source image
     */
    public static void cropByTarget(WebDriver driver, File source, WebElement element) {
        Point point = element.getLocation();
        try {
            BufferedImage bufferedImage = ImageIO.read(source);

            Integer scaledRatio = calculateScaleFactor(driver, bufferedImage);

            bufferedImage = bufferedImage.getSubimage(
                    new Double(point.getX() * scaledRatio).intValue(),
                    new Double(point.getY() * scaledRatio).intValue(),
                    new Double(element.getSize().getWidth() * scaledRatio).intValue(),
                    new Double(element.getSize().getHeight() * scaledRatio).intValue());
            ImageIO.write(bufferedImage, "png", source);

            // Print the screenshot for debugging
//            ImageIO.write(ImageIO.read(source), "png", new File("debug.png"));

        } catch (IOException ex) {
            throw new RuntimeException();
        }
    }
}
