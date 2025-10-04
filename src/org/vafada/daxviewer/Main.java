package org.vafada.daxviewer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.List;

public class Main extends JPanel {
    private static final int PADDING = 20;

    public Main(List<BufferedImage> bitmaps) {
        this.setLayout(new FlowLayout(FlowLayout.LEFT, PADDING, PADDING));

        for (BufferedImage bitmap : bitmaps) {
            PicturePanel panel = new PicturePanel(bitmap);
            this.add(panel);
        }
    }

    static class PicturePanel extends JPanel {
        BufferedImage bitmap;

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