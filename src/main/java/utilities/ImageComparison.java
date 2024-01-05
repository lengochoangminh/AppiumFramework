package utilities;

import io.appium.java_client.ComparesImages;
import io.appium.java_client.imagecomparison.OccurrenceMatchingOptions;
import io.appium.java_client.imagecomparison.OccurrenceMatchingResult;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageComparison {

    private final static Logger logger = Logger.getLogger(ImageComparison.class);

    private static Double default_threshold = 0.5;
    private static ComparesImages comparisonProvider;

    public static Boolean similar(WebDriver driver, File actualImage, File expectedImage, Double threshold) {
        comparisonProvider = (ComparesImages) driver;
        try {
            adjustImageSize(expectedImage, actualImage);
            double similar = comparisonProvider.getImagesSimilarity(actualImage, expectedImage).getScore();
            System.out.println("Similar of Image :" + similar);
            if(threshold != null && threshold > default_threshold)
                return similar > threshold;
            else
                return similar > default_threshold;

        } catch (Exception e) {
            logger.error("Comparison error", e);
            return false;
        }
    }

    public static Boolean contains(WebDriver driver, File fullImage, File partialImage, Double expectThreshold) {
        comparisonProvider = (ComparesImages) driver;

        OccurrenceMatchingResult result = null;
        double thresUnit = 0.1;
        double threshold = 0.0;
        double minCount;

        if(expectThreshold != null && expectThreshold > default_threshold)
            minCount = expectThreshold * 10;    // E.g expectThreshold = 0.8 => numberFind = 8 => Find the image with threshold is 1, 0.9 * 0.8
        else
            minCount = default_threshold * 10;

        for (int i = 10; i >= minCount; i--) {
            try {
                result = comparisonProvider.findImageOccurrence(
                        fullImage, partialImage, new OccurrenceMatchingOptions().withEnabledVisualization().withThreshold(thresUnit * i));
                threshold = thresUnit*i;
                break;
            } catch (Exception e) {
                continue;
            }
        }
        threshold = threshold > default_threshold ? threshold : default_threshold;

        if (result == null)
            throw new RuntimeException("Cannot find the contained image at a threshold as low as: " + threshold);
        logger.info("Image was found at the highest threshold of: " + threshold);

        return (result == null) ? false : true;
    }

    private static void adjustImageSize(File expectedImage, File actualImage) throws IOException {
        BufferedImage bufExpectedImage = ImageIO.read(expectedImage);
        BufferedImage bufActualImage = ImageIO.read(actualImage);

        BufferedImage resized = resize(bufActualImage, bufExpectedImage.getHeight(), bufExpectedImage.getWidth());

        ImageIO.write(resized, "png", actualImage);
    }

    public static BufferedImage resize(BufferedImage img, int height, int width) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }
}
