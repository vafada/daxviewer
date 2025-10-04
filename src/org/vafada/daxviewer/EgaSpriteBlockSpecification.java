package org.vafada.daxviewer;

public class EgaSpriteBlockSpecification implements IFileBlockSpecification {
    @Override
    public boolean isSatisfiedBy(DaxFileBlock block) {
        var data = block.data();
        int frames = data[0];

        int offset = 1;
        if (frames == 0 || frames > 8) return false;

        for (int frame = 0; frame < frames; frame++) {
            if (data.length < 21 + offset) return false;

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

            if (width_px < 1 || height_px < 1 || width_px > 320 || height_px > 200) return false;

            int egaDataSize = height * width * 4;

            if (data.length < egaDataSize + offset) return false;

            offset += egaDataSize;
        }

        if (data.length > offset) {
            return false;
        } else {
            return true;
        }
    }
}
