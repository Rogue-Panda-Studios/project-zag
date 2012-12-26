/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RoguePanda.Library;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

/**
 *
 * @author Oa10712
 */
public final class ImageManipulator {

    public static BufferedImage flipImage(BufferedImage i) {
        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-i.getWidth(null), 0);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        i = op.filter(i, null);
        return i;
    }

    public static BufferedImage selectFromSheet(BufferedImage sheet, int id, int width, int height) {
        BufferedImage sprite;
        int row;
        int col = 0;
        int perRow = sheet.getWidth() / width;
        while (id > perRow - 1) {
            id -= perRow;
            col++;
        }
        row = id;
        sprite = sheet.getSubimage(row * width, col * height, width, height);
        //System.out.println(new Point(row, col));
        return sprite;
    }

    public static BufferedImage scaleImage(BufferedImage img, int width, int height) {
        Image i = img.getScaledInstance(width, height, Image.SCALE_FAST);
        BufferedImage bi = new BufferedImage(i.getWidth(null), i.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics bg = bi.getGraphics();
        bg.drawImage(i, 0, 0, null);
        bg.dispose();
        return bi;
    }

    public static BufferedImage numberizeImage(BufferedImage img, int num) {
        Image i = img;
        BufferedImage bi = null;
        try {
            bi = new BufferedImage(i.getWidth(null), i.getHeight(null), BufferedImage.TYPE_INT_ARGB);
            Graphics bg = bi.getGraphics();
            bg.drawImage(i, 0, 0, null);
            bg.drawString(num + "", 0, 0);
            bg.dispose();
        } catch (Exception ex) {
        }
        return bi;
    }

    public static BufferedImage cropImage(BufferedImage source, double tolerance) {
        // Get our top-left pixel color as our "baseline" for cropping
        int baseColor = source.getRGB(0, 0);

        int width = source.getWidth();
        int height = source.getHeight();

        int topY = Integer.MAX_VALUE, topX = Integer.MAX_VALUE;
        int bottomY = -1, bottomX = -1;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (colorWithinTolerance(baseColor, source.getRGB(x, y), tolerance)) {
                    if (x < topX) {
                        topX = x;
                    }
                    if (y < topY) {
                        topY = y;
                    }
                    if (x > bottomX) {
                        bottomX = x;
                    }
                    if (y > bottomY) {
                        bottomY = y;
                    }
                }
            }
        }

        BufferedImage destination = new BufferedImage((bottomX - topX + 1),
                (bottomY - topY + 1), BufferedImage.TYPE_INT_ARGB);

        destination.getGraphics().drawImage(source, 0, 0,
                destination.getWidth(), destination.getHeight(),
                topX, topY, bottomX, bottomY, null);

        return destination;
    }

    private static boolean colorWithinTolerance(int a, int b, double tolerance) {
        int aAlpha = (a & 0xFF000000) >>> 24;   // Alpha level
        int aRed = (a & 0x00FF0000) >>> 16;   // Red level
        int aGreen = (a & 0x0000FF00) >>> 8;    // Green level
        int aBlue = a & 0x000000FF;            // Blue level

        int bAlpha = (b & 0xFF000000) >>> 24;   // Alpha level
        int bRed = (b & 0x00FF0000) >>> 16;   // Red level
        int bGreen = (b & 0x0000FF00) >>> 8;    // Green level
        int bBlue = b & 0x000000FF;            // Blue level

        double distance = Math.sqrt((aAlpha - bAlpha) * (aAlpha - bAlpha)
                + (aRed - bRed) * (aRed - bRed)
                + (aGreen - bGreen) * (aGreen - bGreen)
                + (aBlue - bBlue) * (aBlue - bBlue));

        // 510.0 is the maximum distance between two colors
        // (0,0,0,0 -> 255,255,255,255)
        double percentAway = distance / 510.0d;

        return (percentAway > tolerance);
    }

    public static BufferedImage adjustRGB(BufferedImage source, double ramount, double gamount, double bamount) {
        for (int x = 0; x < source.getWidth(); x++) {
            for (int y = 0; y < source.getHeight(); y++) {
                Color color = new Color(source.getRGB(x, y), true);
                Color brighter = new Color((int) (color.getRed() * ramount), (int) (color.getGreen() * gamount), (int) (color.getBlue() * bamount), color.getAlpha());
                source.setRGB(x, y, brighter.getRGB());
            }
        }
        return source;
    }

    public static BufferedImage adjustHSB(BufferedImage source, double hamount, double samount, double bamount) {
        for (int x = 0; x < source.getWidth(); x++) {
            for (int y = 0; y < source.getHeight(); y++) {
                Color color = new Color(source.getRGB(x, y), true);
                float[] hsb = new float[3];
                Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
                hsb[0] = (float) (hsb[0] * hamount);
                hsb[1] = (float) (hsb[1] * samount);
                hsb[2] = (float) (hsb[2] * bamount);
                Color temp = Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
                Color outc = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), color.getAlpha());
                source.setRGB(x, y, outc.getRGB());
            }
        }
        return source;
    }
}
