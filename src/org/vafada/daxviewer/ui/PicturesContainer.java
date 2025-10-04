package org.vafada.daxviewer.ui;

import org.vafada.daxviewer.Utils;

import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.util.List;

public class PicturesContainer extends JPanel {
    private static final int PADDING = 20;

    public PicturesContainer() {
        this.setLayout(new FlowLayout(FlowLayout.LEFT, PADDING, PADDING));
    }

    public void setBitmaps(List<BufferedImage> bitmaps) {
        int parentWidth = this.getParent().getWidth();

        int maxImageHeight = 0;
        int maxImageWidth = 0;

        this.removeAll();
        for (BufferedImage bitmap : bitmaps) {
            BufferedImage scaledBitMap = Utils.scaleImage2x(bitmap);
            if (scaledBitMap.getHeight() > maxImageHeight) {
                maxImageHeight = scaledBitMap.getHeight();
            }
            if (scaledBitMap.getWidth() > maxImageWidth) {
                maxImageWidth = scaledBitMap.getWidth();
            }
            PicturePanel panel = new PicturePanel(scaledBitMap);
            this.add(panel);
        }

        int imagePerRows = parentWidth / maxImageWidth;
        int rows = (int) Math.ceil(bitmaps.size() / (double) imagePerRows);

        this.setPreferredSize(new java.awt.Dimension(parentWidth, (rows * (maxImageHeight + PADDING))));

        this.revalidate();
        this.repaint();
    }
}
