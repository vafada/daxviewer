package org.vafada.daxviewer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;

public class Main extends JPanel {

    private List<BufferedImage> images;

    public Main(List<BufferedImage> bitmaps) {
        this.images = bitmaps;
        /*
        // Define the dimensions of the bitmap
        int width = 200;
        int height = 150;

        // Create a 2D array to store pixel data (ARGB format)
        int[][] pixelData = new int[height][width];

        // Populate the array with some color patterns
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Example: Create a gradient from red to blue
                int red = (int) (255.0 * x / width);
                int blue = (int) (255.0 * y / height);
                int green = 0; // Keep green at 0 for a red-blue gradient

                // Combine RGB into a single ARGB integer (alpha opaque)
                pixelData[y][x] = (0xFF << 24) | (red << 16) | (green << 8) | blue;
                //pixelData[y][x] = Color.RED.getRGB();
            }
        }

        // Create a BufferedImage from the pixel data
        //image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        image = new BufferedImage(width, height, BufferedImage.TYPE_USHORT_555_RGB);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                image.setRGB(x, y, pixelData[y][x]);
            }
        }

         */
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int y = 0;

        for (BufferedImage image : images) {
            // Draw the BufferedImage onto the panel
            g.drawImage(image, 0, y, this);
            y += image.getHeight() + 50;
        }
    }

    public static void main(String[] args) {
        System.out.println(args[0]);
        DaxImageFile daxImageFile = new DaxImageFile(args[0], true);

        JFrame frame = new JFrame("DAX Viewer");
        Main panel = new Main(daxImageFile.getBitmaps());
        frame.add(panel);
        frame.setSize(800, 800); // Adjust frame size to fit the image
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}