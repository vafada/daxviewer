package org.vafada.daxviewer;

public record DaxFileHeaderEntry(int id, int offset, int rawSize, int compressedSize) {
}
