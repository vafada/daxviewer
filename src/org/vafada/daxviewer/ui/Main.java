package org.vafada.daxviewer.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JPanel {
    public static void main(String[] args) {
        JFrame frame = new JFrame("DAX Viewer");
        MainPanel panel = new MainPanel();
        frame.add(panel);
        frame.setSize(1000, 800); // Adjust frame size to fit the image
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}