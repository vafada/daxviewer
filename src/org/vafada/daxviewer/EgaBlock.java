package org.vafada.daxviewer;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class EgaBlock {
    private int[] EgaColors;
    private List<BufferedImage> bitmaps = new ArrayList<>();

    public EgaBlock(DaxFileBlock block) {
        // setBlockId(block.Id);
        var data = block.data();

        int height = Utils.ArrayToShort(data, 0);
        int width = Utils.ArrayToShort(data, 2);
        int xPos = Utils.ArrayToShort(data, 4);
        int yPos = Utils.ArrayToShort(data, 6);
        int itemCount = data[8];

        int widthPx = width * 8;
        int heightPx = height;
        int xPosPx = xPos * 8;
        int yPosPx = yPos * 8;
        final int egaDataOffset = 17;
        int egaDataSize = height * width * 4;

        System.out.println("egaDataSize = " + egaDataSize);


        EgaColors = EgaVgaPalette.EgaColors;
/*
        var filename = block.FileNameUpperCase;
        if (filename.Contains("CPIC") || filename.Contains("CHEAD") || filename.Contains("CBODY")
                || filename.Contains("DUNGCOM") || filename.Contains("WILDCOM") || filename.Contains("RANDCOM")
                || filename.Contains("COMSPR"))
        {
            isCombatPicture = true;
            EgaColors = EgaVgaPalette.EgaCombatColors;
        }

        if (data.Length == (egaDataSize * (itemCount + 1)) + egaDataOffset)
        {
            // Death Knights of Krynn
            itemCount += 1;
        }
*/
        if (data.length == (egaDataSize * itemCount) + egaDataOffset) {
            var offset = 0;
            for (var i = 0; i < itemCount; i++, offset += egaDataSize) {
                var bitmap = new BufferedImage((widthPx + xPosPx), (heightPx + yPosPx),
                        BufferedImage.TYPE_USHORT_555_RGB);
                for (var y = 0; y < heightPx; y++) {
                    for (var x = 0; x < widthPx; x += 2) {
                        var b = data[egaDataOffset + (y * width * 4) + (x / 2) + offset];
                        var pxX = (x + xPosPx);
                        var pxY = (y + yPosPx);
                        {
                            int unsignedByte = b & 0xFF;
                            Color color = new Color(EgaColors[unsignedByte >> 4], true); // 'true' means use alpha
                            bitmap.setRGB(pxX, pxY, color.getRGB());
                        }
                        {
                            int unsignedByte = b & 0xFF;
                            Color color = new Color(EgaColors[unsignedByte & 0xF], true); // 'true' means use alpha
                            bitmap.setRGB(pxX + 1, pxY, color.getRGB());
                        }

                    }
                }

                bitmaps.add(bitmap);
            }
        }
    }

    public List<BufferedImage> getBitmaps() {
        return bitmaps;
    }
}
