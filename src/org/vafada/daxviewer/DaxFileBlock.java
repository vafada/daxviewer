package org.vafada.daxviewer;

public record DaxFileBlock(String fullFilename, int id, byte[] data) {
}
