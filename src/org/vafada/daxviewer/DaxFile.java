package org.vafada.daxviewer;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public abstract class DaxFile {
    protected List<DaxFileBlock> blocks;
    public String fileName;

    protected DaxFile(String path, boolean autoLoad) {
        if (autoLoad) {
            load(path);
        }
    }

    public void load(String file) {
        fileName = file;

        try (RandomAccessFile dis = new RandomAccessFile(fileName, "r")) {
            short headerSize = Utils.readInt16LE(dis);

            int dataOffset = headerSize + 2;
            List<DaxFileHeaderEntry> headers = new ArrayList<>();
            final int headerEntrySize = 9;

            int dataCount = ((dataOffset - 2) / headerEntrySize);

            for (int i = 0; i < dataCount; i++) {
                int id = dis.readByte();
                int offset = Utils.readInt32LE(dis);
                int rawSize = Utils.readInt16LE(dis);
                int compressedSize = Utils.readInt16LE(dis);
                DaxFileHeaderEntry dhe = new DaxFileHeaderEntry(id, offset, rawSize, compressedSize);
                System.out.println("dhe = " + dhe);
                headers.add(dhe);
            }

            blocks = new ArrayList<>(headers.size());

            for (DaxFileHeaderEntry dhe : headers) {
                byte[] compressedBytes = new byte[dhe.compressedSize()];
                if (dhe.rawSize() <= 0) {
                    dis.seek(dataOffset + dhe.offset());
                    dis.read(compressedBytes);
                    blocks.add(new DaxFileBlock(fileName, dhe.id(), compressedBytes));
                } else {
                    byte[] raw = new byte[dhe.rawSize()];
                    dis.seek(dataOffset + dhe.offset());
                    dis.read(compressedBytes);
                    decodeCompressedBytes(dhe.compressedSize(), raw, compressedBytes);
                    blocks.add(new DaxFileBlock(fileName, dhe.id(), raw));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void decodeCompressedBytes(int dataLength, byte[] outputPtr, byte[] inputPtr) {
        int inputIndex = 0;
        int outputIndex = 0;

        do {
            var runLength = inputPtr[inputIndex];

            if (runLength >= 0) {
                for (var i = 0; i <= runLength; i++) {

                    if (inputIndex + i + 1 >= inputPtr.length)
                        return; // some files seem to have faulty encoding and run over the limit
                    outputPtr[outputIndex + i] = inputPtr[inputIndex + i + 1];
                }

                inputIndex += runLength + 2;
                outputIndex += runLength + 1;
            } else {
                runLength = (byte) (-runLength);

                for (var i = 0; i < runLength; i++) {
                    outputPtr[outputIndex + i] = inputPtr[inputIndex + 1];
                }

                inputIndex += 2;
                outputIndex += runLength;
            }
        } while (inputIndex < dataLength);
    }
}
