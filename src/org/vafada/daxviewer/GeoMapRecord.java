package org.vafada.daxviewer;

import java.util.List;

public record GeoMapRecord(String name, List<GeoWallRecord> walls, int daxId) {
}
