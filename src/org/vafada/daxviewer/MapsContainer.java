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
            drawRooms(map.walls(), g);
            drawGrid(g);

            g.dispose();

            PicturePanel panel = new PicturePanel(bitmap);
            this.add(panel);
            add(Box.createVerticalStrut(20));
        }
    }

    private void drawRooms(List<GeoWallRecord> walls, Graphics2D g) {
        for (GeoWallRecord wall : walls) {
            int x = wall.col() * RoomSize + GutterSize;
            int y = wall.row() * RoomSize + GutterSize;

            // DrawRoomBase
            g.setColor(new Color(85, 85, 85));
            g.fillRect(x, y, RoomSize, RoomSize);

            // DrawRoomEvent
            {
                g.setFont(new Font("Courier New", Font.BOLD, 14));

                if (wall.event() != 0) {
                    g.setColor(new Color(34, 139, 34));
                    g.fillRect(x, y, RoomSize, RoomSize);
                    if ((wall.event() & 127) > 0) {
                        g.setColor(Color.WHITE);
                        g.drawString(String.valueOf(wall.event() & 127), x + 10, y + 15);
                    }
                }
            }

            // DrawRoomWalls
            {
                g.setColor(new Color(170, 170, 170));
                if (wall.north() != 0) {
                    g.fillRect(x, y, RoomSize, WallThickness);
                }
                if (wall.east() != 0) {
                    g.fillRect(x + RoomSize - WallThickness, y, WallThickness, RoomSize);
                }
                if (wall.south() != 0) {
                    g.fillRect(x, y + RoomSize - WallThickness, RoomSize, WallThickness);
                }
                if (wall.west() != 0) {
                    g.fillRect(x, y, WallThickness, RoomSize);
                }
            }

            // DrawRoomDoors
            {
                var doorX = 0;
                var doorY = 0;
                var doorW = 0;
                var doorH = 0;

                var northDoor = wall.door() & 3;
                var eastDoor = ((wall.door() >> 2) & 3);
                var southDoor = ((wall.door() >> 4) & 3);
                var westDoor = ((wall.door() >> 6) & 3);

                Color[] doorBrushes = new Color[]{null, Color.WHITE, Color.YELLOW, Color.RED};

                if (northDoor > 0) {
                    doorX = x + (WallThickness * 2);
                    doorY = y;
                    doorW = RoomSize - (WallThickness * 4);
                    doorH = WallThickness * 2;
                    g.setColor(doorBrushes[northDoor]);
                    g.fillRect(doorX, doorY, doorW, doorH);
                }
                if (eastDoor > 0) {
                    doorX = x + RoomSize - (WallThickness * 2);
                    doorY = y + (WallThickness * 2);
                    doorW = WallThickness * 2;
                    doorH = RoomSize - (WallThickness * 4);
                    g.setColor(doorBrushes[eastDoor]);
                    g.fillRect(doorX, doorY, doorW, doorH);
                }

                if (southDoor > 0) {
                    doorX = x + (WallThickness * 2);
                    doorY = y + RoomSize - (WallThickness * 2);
                    doorW = RoomSize - (WallThickness * 4);
                    doorH = WallThickness * 2;
                    g.setColor(doorBrushes[southDoor]);
                    g.fillRect(doorX, doorY, doorW, doorH);
                }

                if (westDoor > 0) {
                    doorX = x;
                    doorY = y + (WallThickness * 2);
                    doorW = WallThickness * 2;
                    doorH = RoomSize - (WallThickness * 4);
                    g.setColor(doorBrushes[westDoor]);
                    g.fillRect(doorX, doorY, doorW, doorH);
                }
            }
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
