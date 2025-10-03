package org.vafada.daxviewer;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DaxImageFile extends DaxFile {
    //private List<Map.Entry<Integer, List<BufferedImage>>> bitmaps = new ArrayList<>();
    private List<BufferedImage> bitmaps = new ArrayList<>();
    public DaxImageFile(String path, boolean autoLoad) {
        super(path, autoLoad);
        processBlocks();
    }

    protected void processBlocks() {
        for (DaxFileBlock block : blocks) {
            EgaBlock egaBlock = new EgaBlock(block);
            //var renderBlock = new RenderBlockFactory().CreateUsing(block);

            //bitmaps.add(new Node<Integer, List<BufferedImage>>(block.id(), egaBlock.getBitmaps());

            bitmaps.addAll(egaBlock.getBitmaps());
        }
    }

    public List<BufferedImage> getBitmaps() {
        return bitmaps;
    }
}
