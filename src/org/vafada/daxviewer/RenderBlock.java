package org.vafada.daxviewer;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class RenderBlock {
    protected List<BufferedImage> bitmaps = new ArrayList<>();

    public List<BufferedImage> getBitmaps() {
        return bitmaps;
    }
}
