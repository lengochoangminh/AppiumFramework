package utilities;

import lombok.Getter;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;

@Getter
public class LogicalScreenResolution {

    private static LogicalScreenResolution logicalScreen;

    private final int width;
    private final int height;
    private final Point centerPoint;
    private final Point upPoint;
    private final Point downPoint;

    private LogicalScreenResolution(WebDriver driver) {
        this.width = driver.manage().window().getSize().getWidth();
        this.height = driver.manage().window().getSize().getHeight();
        this.centerPoint = calculateCenterPoint();
        this.downPoint = calculateDownPoint();
        this.upPoint = calculateUpPoint();
    }

    public static LogicalScreenResolution of(WebDriver driver) {
        if (logicalScreen == null) {
            synchronized (LogicalScreenResolution.class) {
                logicalScreen = new LogicalScreenResolution(driver);
            }
        }

        return logicalScreen;
    }

    private Point calculateCenterPoint() {
        return new Point(width / 2, height / 2);
    }

    private Point calculateUpPoint() {
        return new Point(width / 2, height / 4);
    }

    private Point calculateDownPoint() {
        return new Point(width / 2, (height * 3) / 4);
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}
