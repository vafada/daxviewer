package org.vafada.daxviewer;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Utils {
    public static short readInt16LE(RandomAccessFile dis) throws IOException {
        int b1 = dis.read();
        int b2 = dis.read();
        return (short) ((b2 << 8) | b1);
    }

    public static int readInt32LE(RandomAccessFile dis) throws IOException {
        int b1 = dis.read();
        int b2 = dis.read();
        int b3 = dis.read();
        int b4 = dis.read();
        return (b4 << 24) | (b3 << 16) | (b2 << 8) | b1;
    }

    public static int ArrayToInt(byte[] data, int offset)
    {
        return (data[offset + 0] + (data[offset + 1] << 8) + (data[offset + 2] << 16) + (data[offset + 3] << 24));
    }

    public static int ArrayToShort(byte[] data, int offset)
    {
        return ((data[offset + 0] + (data[offset + 1] << 8))) & 0xFF;
    }

    public static void exportAsPNG(BufferedImage image) {
        String fileName = "daxviewer-" +  System.currentTimeMillis() + ".png";
        //String currentFolder = System.getProperty("user.dir");
        //System.out.println("Current folder: " + currentFolder);
        File newPNG = new File(fileName);
        try {
            ImageIO.write(image, "png", newPNG);
            String message = "Image exported at " + newPNG.getAbsolutePath();
            JOptionPane.showMessageDialog(null, message, "Export Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
