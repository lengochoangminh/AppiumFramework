package theApp;

import org.testng.Assert;
import org.testng.annotations.Test;
import theApp.pages.DeepLinkSecretArea;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;

public class TC003_HandDeepLinkAndQRCode extends BaseSetup {

    @Test
    public void verify_the_user_read_qr_code() throws IOException {
        printExecutionLogs("Read the information from QRCode image");
        String decodedText = decodeQRCode(new File("src/test/resources/Kitty_QRCode.png"));
        if (decodedText == null) {
            printExecutionLogs("No QR Code found in the image");
            System.out.println("No QR Code found in the image");
        } else {
            printExecutionLogs("Decoded text = " + decodedText);
            System.out.println("Decoded text = " + decodedText);
        }
    }

    private static String decodeQRCode(File qrCodeimage) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(qrCodeimage);
        LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

        try {
            Result result = new MultiFormatReader().decode(bitmap);
            return result.getText();
        } catch (NotFoundException e) {
            System.out.println("There is no QR code in the image");
            return null;
        }
    }

    @Test
    public void verify_the_user_navigate_by_deep_link() throws InterruptedException {

        String AUTH_USER = "alice";
        String AUTH_PASS = "mypassword";
        String deepLink = "theapp://login/" + AUTH_USER + "/" + AUTH_PASS;

        DeepLinkSecretArea deepLinkSecretArea = new DeepLinkSecretArea(appiumDriver);

        printExecutionLogs("Navigate by the deep link");
        appiumDriver.get(deepLink);

        printExecutionLogs("Verify the Secrect Area is displayed");
        Assert.assertTrue(deepLinkSecretArea.isSecretAreaDisplayed());
    }
}
