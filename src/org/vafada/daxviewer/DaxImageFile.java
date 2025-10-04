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
            var renderBlock = new RenderBlockFactory().createBlock(block);
            bitmaps.addAll(renderBlock.getBitmaps());
        }
    }

    public List<BufferedImage> getBitmaps() {
        return bitmaps;
    }
}
