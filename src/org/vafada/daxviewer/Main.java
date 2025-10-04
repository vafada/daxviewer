package org.vafada.daxviewer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;

public class Main extends JPanel {
    private static final int PADDING = 20;

    private List<BufferedImage> images;

    public Main(List<BufferedImage> bitmaps) {
        this.images = bitmaps;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int panelWidth = getWidth();

        int y = 0;
        int x = 0;

        int index = 0;
        int tallestImageHeight = 0;
        for (BufferedImage image : images) {
            if (image.getHeight() > tallestImageHeight) {
                tallestImageHeight = image.getHeight();
            }
            int imageWidth = image.getWidth();
            int imageHeight = image.getHeight();

            int nextX = x + imageWidth + PADDING;
            // move to the next row if the next image would exceed the panel width
            if (nextX > panelWidth && index > 0) {
                x = 0;
                y += tallestImageHeight + PADDING;
                nextX = x + imageWidth + PADDING;
                tallestImageHeight = imageHeight;
            }
            // Draw the BufferedImage onto the panel
            g.drawImage(image, x, y, this);
            x = nextX;
            index++;
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