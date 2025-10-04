package org.vafada.daxviewer;

import java.nio.file.Path;
import java.nio.file.Paths;

public record DaxFileBlock(String fullFilename, int id, byte[] data) {
    public String getFilename() {
        Path path = Paths.get(fullFilename);
        return path.getFileName().toString();
    }
}
