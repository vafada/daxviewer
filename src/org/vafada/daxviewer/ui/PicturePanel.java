package org.vafada.daxviewer.ui;

import org.vafada.daxviewer.Utils;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class PicturePanel extends JPanel {
    private BufferedImage bitmap;

    public PicturePanel(BufferedImage bitmap) {
        this.bitmap = bitmap;
        this.setPreferredSize(new Dimension(bitmap.getWidth(), bitmap.getHeight()));

        JPanel that = this;

        JPopupMenu tooltipPopup = new JPopupMenu();
        JMenuItem menuItem = new JMenuItem("Export as PNG");
        menuItem.addActionListener(e -> {
            Utils.exportAsPNG(bitmap);
        });

        tooltipPopup.add(menuItem);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Check if right mouse button was pressed
                if (SwingUtilities.isRightMouseButton(e)) {
                    tooltipPopup.show(that, e.getX(), e.getY());
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bitmap, 0, 0, null);
    }
}
