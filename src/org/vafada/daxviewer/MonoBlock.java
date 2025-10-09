package org.vafada.daxviewer;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class MonoBlock extends RenderBlock {
    public MonoBlock(DaxFileBlock block) {
        //setBlockId(block.Id);

        int[] monoBitMask = {0x80, 0x40, 0x20, 0x10, 0x08, 0x04, 0x02, 0x01};
        var count = block.data().length / 8;

        for (var ch = 0; ch < count; ch++) {
            var bitmap = new BufferedImage(8, 8, BufferedImage.TYPE_INT_ARGB);

            for (var y = 0; y < 8; y++) {
                for (var x = 0; x < 8; x++) {
                    var b = block.data()[(ch * 8) + y];
                    var c = ((b & monoBitMask[x]) != 0) ? Color.WHITE : Color.BLACK;
                    bitmap.setRGB(x, y, c.getRGB());
                }
            }

            bitmaps.add(bitmap);
        }
    }
}
