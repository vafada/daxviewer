package org.vafada.daxviewer;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class EgaSpriteBlock extends RenderBlock {
    public EgaSpriteBlock(DaxFileBlock block) {
        var data = block.data();

        //setBlockId(block.Id);

        int frames = data[0];
        if (frames > 8) return;

        int offset = 1;


        Color[] colors = new Color[16];
        for (int i = 0; i < 16; i++) {
            colors[i] = new Color(EgaVgaPalette.EgaColors[i], true); // 'true' means use alpha
        }

        var filename = block.getFilename();
        boolean xorFrames = filename.startsWith("PIC");
        xorFrames |= filename.startsWith("FINAL");

        if (filename.startsWith("SPRI")) {
            colors[0] = new Color(0, 0, 0, 0);
            colors[13] = new Color(EgaVgaPalette.EgaColors[0], true);
        }


        byte[] first_frame_ega_layout = null;

        for (int frame = 0; frame < frames; frame++) {
            if (data.length < 21 + offset) return;

            offset += 4;
            int height = Utils.ArrayToShort(data, offset);
            offset += 2;
            int width = Utils.ArrayToShort(data, offset);
            offset += 2;
            int x_pos = Utils.ArrayToShort(data, offset);
            offset += 2;
            int y_pos = Utils.ArrayToShort(data, offset);
            offset += 3;
            offset += 8;

            // skip 1 byte
            // skip 8 bytes
            int width_px = width * 8;
            int height_px = height;
            int x_pos_px = x_pos * 8;
            int y_pos_px = y_pos * 8;

            if (width_px < 1 || height_px < 1 || width_px > 320 || height_px > 200) return;

            int egaDataSize = height * width * 4;

            if (data.length < egaDataSize + offset) return;

            if (xorFrames) {
                if (frame == 0) {
                    first_frame_ega_layout = new byte[egaDataSize + 1];

                    System.arraycopy(data, offset, first_frame_ega_layout, 0, egaDataSize);
                } else {
                    for (int i = 0; i < egaDataSize; i++) {
                        byte b = first_frame_ega_layout[i];
                        data[offset + i] ^= b;
                    }
                }
            }

            var bitmap = new BufferedImage((width_px + x_pos_px), (height_px + y_pos_px), BufferedImage.TYPE_USHORT_555_RGB);
            for (int y = 0; y < height_px; y++) {
                for (int x = 0; x < width_px; x += 2) {
                    byte b = data[(y * width * 4) + (x / 2) + offset];
                    int pxX = (x + x_pos_px);
                    int pxY = (y + y_pos_px);
                    {
                        int unsignedByte = b & 0xFF;
                        Color color = colors[unsignedByte >> 4];
                        bitmap.setRGB(pxX, pxY, color.getRGB());
                    }
                    {
                        int unsignedByte = b & 0xFF;
                        Color color = colors[unsignedByte & 0xF];
                        bitmap.setRGB(pxX + 1, pxY, color.getRGB());
                    }
                }
            }

            bitmaps.add(bitmap);

            offset += egaDataSize;
        }
    }
}
