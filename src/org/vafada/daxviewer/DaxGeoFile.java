package org.vafada.daxviewer;

import java.util.ArrayList;
import java.util.List;

public class DaxGeoFile extends DaxFile {
    private List<GeoMapRecord> maps;

    public DaxGeoFile(String fileName) {
        super(fileName, true);
        processBlocks();
    }

    public List<GeoMapRecord> getMaps() {
        return maps;
    }

    private void processBlocks() {
        maps = new ArrayList<GeoMapRecord>();
        for (DaxFileBlock block : blocks) {
            List<GeoWallRecord> walls = new ArrayList<>();

            for (var i = 0; i < 256; i++)
            {
                var row = i >> 4;
                var col = i & 0x0f;

                byte[] data = block.data();

                var neWallType = data[i + 2];
                var north = (byte) (neWallType >> 4) & 0x0f;
                var east = (byte) (neWallType & 0x0f);

                var swWallType = data[i + 258];
                var south = (byte)(swWallType >> 4) & 0x0f;
                var west = (byte)(swWallType & 0x0f);

                var eventdata = data[i + 514];
                var doorinfo = data[i + 770];

                //recordHighestEvent(block.Id, eventdata & 127);
                GeoWallRecord wall = new GeoWallRecord(
                        row,
                        col,
                        north,
                        south,
                        east,
                        west,
                        eventdata,
                        doorinfo
                );
                walls.add(wall);
            }

            maps.add(new GeoMapRecord(walls, block.id()));
        }
    }
}
