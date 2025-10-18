package org.vafada.daxviewer;

import org.vafada.daxviewer.ui.PicturePanel;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.List;

public class MapsContainer extends JPanel {
    private final static int RoomSize = 32;
    private final static int WallThickness = 2;
    private final static int BaseMapSize = 256 * 2;
    private final static int GutterSize = 32;
    private final static int FullMapWidth = BaseMapSize + GutterSize;
    private final static int FullMapHeight = BaseMapSize + GutterSize;

    private List<GeoMapRecord> maps;

    public MapsContainer(List<GeoMapRecord> maps) {
        this.maps = maps;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.drawMaps();
    }

    private void drawMaps() {
        for (var map : this.maps) {
            var bitmap = new BufferedImage(FullMapWidth, FullMapHeight,
                    BufferedImage.TYPE_INT_ARGB);

            Graphics2D g = bitmap.createGraphics();
            g.setColor(java.awt.Color.WHITE);
            g.fillRect(0, 0, FullMapWidth, FullMapHeight);
            drawGutter(g);
            drawGrid(g);

            g.dispose();

            PicturePanel panel = new PicturePanel(bitmap);
            this.add(panel);
            add(Box.createVerticalStrut(20));
        }
    }

    private void drawGutter(Graphics2D g) {
        g.setFont(new Font("Courier New", Font.BOLD, 14));
        g.setColor(new Color(85, 85, 85));
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON); // Enable anti-aliasing for smoother text

        for (int row = 0; row < 16; row++) {
            int x = 12;
            if (row >= 10) {
                x = 10;
            }
            g.drawString(String.valueOf(row), x, (row * GutterSize + GutterSize) + 22);
        }

        for (var col = 0; col < 16; col++) {
            int x = 12;
            if (col >= 10) {
                x = 10;
            }
            g.drawString(String.valueOf(col), (col * GutterSize + GutterSize) + x, 22);
        }
    }

    private void drawGrid(Graphics2D g) {
        g.setColor(new Color(30, 144, 255));

        for (int row = 0; row < 18; row++) {
            var x1 = 0;
            var y1 = row * RoomSize;
            var x2 = BaseMapSize + GutterSize;
            var y2 = row * RoomSize;
            g.drawLine(x1, y1, x2, y2);

            for (int col = 0; col < 18; col++) {
                x1 = col * RoomSize;
                y1 = row;
                x2 = col * RoomSize;
                y2 = row + BaseMapSize + GutterSize;
                g.drawLine(x1, y1, x2, y2);
            }
        }
    }
}
