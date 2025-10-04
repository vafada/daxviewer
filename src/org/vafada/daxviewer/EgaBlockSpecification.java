package org.vafada.daxviewer;

public class EgaBlockSpecification implements IFileBlockSpecification {
    @Override
    public boolean isSatisfiedBy(DaxFileBlock block) {
        var data = block.data();
        var length = data.length;

        if (length < 9) return false;

        int height = Utils.ArrayToShort(data, 0);
        int width = Utils.ArrayToShort(data, 2);
        var widthPx = width * 8;

        if (widthPx == 0 || height == 0 || widthPx > 320 || height > 200)
            return false;

        int itemCount = data[8];

        final int egaDataOffset = 17;
        var egaDataSize = height * width * 4;

        if (length == (egaDataSize * (itemCount + 1)) + egaDataOffset) {
            // Death Knights of Krynn
            itemCount += 1;
        }

        return data.length == ((egaDataSize * itemCount) + egaDataOffset);
    }
}
