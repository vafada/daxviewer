package org.vafada.daxviewer.ui;

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
        this.removeAll();
        for (BufferedImage bitmap : bitmaps) {
            PicturePanel panel = new PicturePanel(bitmap);
            this.add(panel);
        }
        this.revalidate();
    }
}
