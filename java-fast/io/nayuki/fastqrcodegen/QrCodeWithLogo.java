package io.nayuki.fastqrcodegen;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;

public class QrCodeWithLogo {
    public static BufferedImage drawLogo(QrCode qr, int scale, String logoPath) throws Exception {
        int qrSize = qr.size;
        int imageSize = qrSize * scale;
        BufferedImage image = new BufferedImage(imageSize, imageSize, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();

        // draw QR Code
        g.setColor(new Color(255, 102, 0));
        g.fillRect(0, 0, imageSize * 2, imageSize * 2);
        g.setColor(Color.BLACK);

        for (int y = 0; y < qrSize; y++) {
            for (int x = 0; x < qrSize; x++) {
                if (qr.getModule(x, y)) {
                    g.fillRect(x * scale, y * scale, scale, scale);
                }
            }
        }

        // add Logo
        BufferedImage logo = ImageIO.read(new File(logoPath));
        int logoSize = imageSize / 4;
        int x = (imageSize - logoSize) /2;
        int y = (imageSize - logoSize) /2;

        // make logo circle
        Shape clip = g.getClip();
        g.setClip(new Ellipse2D.Float(x, y, logoSize, logoSize));
        g.drawImage(logo, x, y, logoSize, logoSize, null);
        g.setClip(clip);

        g.dispose();

        return image;
    }
}
