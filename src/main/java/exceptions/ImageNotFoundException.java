package exceptions;

import java.awt.*;

public class ImageNotFoundException extends RuntimeException {
    Double accuracy;

    public Rectangle foundRect;

    public ImageNotFoundException(String message, Rectangle foundRect, Double accuracy) {
        super(message);

        this.accuracy = accuracy;
        this.foundRect = foundRect;
    }
}