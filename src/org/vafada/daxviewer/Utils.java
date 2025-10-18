package org.vafada.daxviewer;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Utils {
    public enum GameList {
        PoolOfRadiance,
        CurseOfTheAzureBonds,
        SecretOfTheSilverBlades,
        PoolsOfDarkness,
        ForgottenRealmsUnlimitedAdventures,
        GatewayToTheSavageFrontier,
        TreasuresOfTheSavageFrontier,
        NeverwinterNights,
        ChampionsOfKrynn,
        DeathKnightsOfKrynn,
        DarkQueenOfKyrnn,
        CountdownToDoomsday,
        MatrixCubed,
        Unknown,
    }

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

    public static int ArrayToInt(byte[] data, int offset) {
        return (data[offset + 0] + (data[offset + 1] << 8) + (data[offset + 2] << 16) + (data[offset + 3] << 24));
    }

    public static int ArrayToShort(byte[] data, int offset) {
        return ((data[offset + 0] + (data[offset + 1] << 8))) & 0xFF;
    }

    public static void exportAsPNG(BufferedImage image) {
        String fileName = "daxviewer-" + System.currentTimeMillis() + ".png";
        File newPNG = new File(fileName);
        try {
            ImageIO.write(image, "png", newPNG);
            String message = "Image exported at " + newPNG.getAbsolutePath();
            JOptionPane.showMessageDialog(null, message, "Export Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BufferedImage scaleImage2x(BufferedImage original) {
        int width = original.getWidth() * 2;
        int height = original.getHeight() * 2;
        BufferedImage scaled = new BufferedImage(width, height, original.getType());
        Graphics2D g2d = scaled.createGraphics();
        AffineTransform at = AffineTransform.getScaleInstance(2.0, 2.0);
        g2d.drawRenderedImage(original, at);
        g2d.dispose();
        return scaled;
    }

    public static GameList determineGameFrom(String blockFileName) {
        // establish the directory from the file
        Path filePath = Paths.get(blockFileName);

        Path parentDirectory = filePath.getParent();

        if (Files.isRegularFile(Path.of(parentDirectory.toString(), "POOL.CFG"))) {
            return GameList.PoolOfRadiance;
        }

        if (Files.isRegularFile(Path.of(parentDirectory.toString(), "POOL4.CFG"))) {
            return GameList.PoolsOfDarkness;
        }

        if (Files.isRegularFile(Path.of(parentDirectory.toString(), "BLADES.CFG"))) {
            return GameList.SecretOfTheSilverBlades;
        }

        if (Files.isRegularFile(Path.of(parentDirectory.toString(), "CURSE.CFG"))) {
            return GameList.CurseOfTheAzureBonds;
        }

        if (Files.isRegularFile(Path.of(parentDirectory.toString(), "BUCK.CFG"))) {
            return GameList.CountdownToDoomsday;
        }

        if (Files.isRegularFile(Path.of(parentDirectory.toString(), "MATRIX.CFG"))) {
            return GameList.MatrixCubed;
        }

        if (Files.isRegularFile(Path.of(parentDirectory.toString(), "KRYNN.CFG"))) {
            return GameList.ChampionsOfKrynn;
        }

        if (Files.isRegularFile(Path.of(parentDirectory.toString(), "TREASURE.CFG"))) {
            return GameList.TreasuresOfTheSavageFrontier;
        }

        if (Files.isRegularFile(Path.of(parentDirectory.toString(), "DKK.CFG"))) {
            return GameList.DeathKnightsOfKrynn;
        }


        if (Files.isRegularFile(Path.of(parentDirectory.toString(), "GAME.CFG")) &&
                Files.isRegularFile(Path.of(parentDirectory.toString(), "8X8D6.DAX"))) {
            return GameList.GatewayToTheSavageFrontier;
        }

        if (Files.isRegularFile(Path.of(parentDirectory.toString(), "GAME.CFG")) &&
                Files.isRegularFile(Path.of(parentDirectory.toString(), "CPIC.DAX"))) {
            return GameList.NeverwinterNights;
        }

        return GameList.Unknown;
    }
}
