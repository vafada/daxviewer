package org.vafada.daxviewer;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class DaxImageFile extends DaxFile {
    private List<BufferedImage> bitmaps = new ArrayList<>();
    public DaxImageFile(String path, boolean autoLoad) {
        super(path, autoLoad);
        processBlocks();
    }

    protected void processBlocks() {
        for (DaxFileBlock block : blocks) {
            try {
                var renderBlock = new RenderBlockFactory().createBlock(block);
                System.out.println("Renderer = " + renderBlock);
                bitmaps.addAll(renderBlock.getBitmaps());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public List<BufferedImage> getBitmaps() {
        return bitmaps;
    }
}
