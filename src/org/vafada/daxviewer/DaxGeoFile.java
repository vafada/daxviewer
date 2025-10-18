package org.vafada.daxviewer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DaxGeoFile extends DaxFile {
    private List<GeoMapRecord> maps;

    private static final Map<Utils.GameList, Map<Integer, String>> GAME_LIST_MAP_MAP = new HashMap<>();

    static {
        GAME_LIST_MAP_MAP.put(Utils.GameList.PoolOfRadiance, Map.ofEntries(
                Map.entry(0, "Civilized Area, New Phlan"),
                Map.entry(1, "Buccaneer Base"),
                Map.entry(2, "Cadorna Textile House"),
                Map.entry(3, "Valjevo Castle, North West"),
                Map.entry(4, "Valjevo Castle, North East"),
                Map.entry(5, "Valjevo Castle, South East"),
                Map.entry(6, "Valjevo Castle, South West"),
                Map.entry(7, "Valjevo Castle, Inner Tower"),
                Map.entry(9, "Stojanow Gate"),
                Map.entry(10, "Valhingen Graveyard"),
                Map.entry(13, "Kobold Caves"),
                Map.entry(14, "Kovel Mansion"),
                Map.entry(15, "Mendor's Library"),
                Map.entry(16, "Lizard Men Keep"),
                Map.entry(17, "Nomad Camp"),
                Map.entry(18, "Podal Plaza"),
                Map.entry(20, "Slums"),
                Map.entry(21, "Sokal Keep"),
                Map.entry(22, "Sorcerer's Island, Level 1"),
                Map.entry(23, "Sorcerer's Island, Level 2 and 3"),
                Map.entry(24, "Temple of Bane"),
                Map.entry(25, "Unknown Lair"),
                Map.entry(26, "Unknown Zone"),
                Map.entry(27, "Unknown Lair"),
                Map.entry(28, "Outpost of Zhentil Keep"),
                Map.entry(29, "Kuto's Well"),
                Map.entry(30, "Lizard Men Catacombs"),
                Map.entry(31, "Wealthy Area"),
                Map.entry(32, "Kuto's Well Catacombs")
        ));


        GAME_LIST_MAP_MAP.put(Utils.GameList.CurseOfTheAzureBonds, Map.ofEntries(
                Map.entry(1, "Tilverton City, Thieves' Guild"),
                Map.entry(3, "Tilverton Sewers"),
                Map.entry(4, "The Fire Knife Hideout"),
                Map.entry(16, "Yulash"),
                Map.entry(17, "The Pit of Moander, Levels 1-2"),
                Map.entry(32, "Zhentil Keep, The Shrine of Bane"),
                Map.entry(33, "The Cave of the Beholder"),
                Map.entry(37, "Oxam's Tower, Dungeon, Cavern"),
                Map.entry(50, "Village of Haptooth, Cave of the Dracolich"),
                Map.entry(51, "The Wizard's Tower"),
                Map.entry(64, "The Burial Glen"),
                Map.entry(66, "The Ruins of Myth Drannor"),
                Map.entry(67, "The Grand Ruined Temple, Levels 2-1")
        ));

        GAME_LIST_MAP_MAP.put(Utils.GameList.SecretOfTheSilverBlades, Map.ofEntries(
                Map.entry(16, "New Verdigris"),
                Map.entry(32, "Old Verdigris Ruins"),
                Map.entry(33, "Well of Knowledge"),
                Map.entry(34, "Black Circle Headquarters"),
                Map.entry(48, "The Mines, Level 1"),
                Map.entry(49, "The Mines, Level 2"),
                Map.entry(50, "Temple of Tyr"),
                Map.entry(80, "Crevasses, Level 1"),
                Map.entry(81, "Frost Giant Village"),
                Map.entry(82, "Crevasses, Level 2"),
                Map.entry(96, "Castle Entrance"),
                Map.entry(97, "Middle Level of the Castle"),
                Map.entry(98, "Dreadlord's Sanctum"),
                Map.entry(64, "Dungeon Levels 10-7"),
                Map.entry(65, "Dungeon Levels 4-3"),
                Map.entry(66, "Dungeon Levels 2-1"),
                Map.entry(68, "Drider Base")
        ));

        GAME_LIST_MAP_MAP.put(Utils.GameList.PoolsOfDarkness, Map.ofEntries(
                Map.entry(1, "Playtester Town"),
                Map.entry(16, "Phlan (East)"),
                Map.entry(17, "Phlan (West)"),
                Map.entry(18, "Limbo"),
                Map.entry(19, "Mulmaster, Zhentil Keep"),
                Map.entry(20, "Overland Minis"),
                Map.entry(21, "Hill Giant Steading"),
                Map.entry(22, "Sasha and the Lands of Thar"),
                Map.entry(32, "Fire Giant Cave"),
                Map.entry(33, "Dragons' Aerie"),
                Map.entry(34, "Thorne's Cave"),
                Map.entry(35, "Descent To The Depths"),
                Map.entry(36, "Cave of the Beholder"),
                Map.entry(37, "Vala vs. Vaasa"),
                Map.entry(39, "Dave's Challenge"),
                Map.entry(40, "Manshoon's Tower"),
                Map.entry(48, "Manshoon's Tower"),
                Map.entry(49, "Dragon's Aerie"),
                Map.entry(50, "Temple of Tyr"),
                Map.entry(52, "Moander Minis"),
                Map.entry(53, "Moander's Heart"),
                Map.entry(64, "Dark Phlan"),
                Map.entry(65, "The Palace of Gothmenes"),
                Map.entry(66, "Nacacia and Myth Drannor"),
                Map.entry(67, "Tower of Marcus Levels 9-11"),
                Map.entry(68, "Silk and the Slaves of the Drow"),
                Map.entry(69, "Kalistes' Temple"),
                Map.entry(70, "Tower of Marcus Levels 0-5"),
                Map.entry(71, "Tower of Marcus Levels 6-8"),
                Map.entry(81, "Drow Testing Ground"),
                Map.entry(82, "Kalistes' Parlor, Upper Level"),
                Map.entry(83, "Web Dimension Mines"),
                Map.entry(84, "Kalistes' Parlor, Lower Level")
        ));

        GAME_LIST_MAP_MAP.put(Utils.GameList.GatewayToTheSavageFrontier, Map.ofEntries(
                Map.entry(1, "Yartar"),
                Map.entry(2, "Kraken Complex"),
                Map.entry(3, "Nesme"),
                Map.entry(4, "Silverymoon"),
                Map.entry(5, "Everlund"),
                Map.entry(6, "Neverwinter"),
                Map.entry(7, "Port Llast and Gallant Prince"),
                Map.entry(8, "Luskan"),
                Map.entry(9, "Hosttower of the Arcane"),
                Map.entry(10, "Tuern"),
                Map.entry(11, "Gundarlun"),
                Map.entry(12, "Purple Rocks"),
                Map.entry(13, "Kraken Headquarters"),
                Map.entry(14, "Secomber"),
                Map.entry(15, "Loudwater"),
                Map.entry(16, "Llorkh"),
                Map.entry(17, "The Arena Beneath Llorkh"),
                Map.entry(18, "The Star Mounts"),
                Map.entry(19, "Sundabar"),
                Map.entry(20, "Ascore"),
                Map.entry(21, "Outdoors"),
                Map.entry(27, "Vaalgamon Taunts At Ascore Maze"),
                Map.entry(28, "Victory at Ascore"),
                Map.entry(99, "Playtester Town")
        ));

        GAME_LIST_MAP_MAP.put(Utils.GameList.TreasuresOfTheSavageFrontier, Map.ofEntries(
                Map.entry(16, "Llorkh"),
                Map.entry(17, "Lord Geildarr's Keep"),
                Map.entry(18, "Loudwater"),
                Map.entry(19, "Secomber"),
                Map.entry(20, "Leilon"),
                Map.entry(21, "Smuggler's Dock"),
                Map.entry(22, "Waterdeep and the Caverns"),
                Map.entry(23, "Yartar"),
                Map.entry(24, "Triboar"),
                Map.entry(25, "Longsaddle"),
                Map.entry(26, "Mintarn"),
                Map.entry(27, "Orlumbor"),
                Map.entry(28, "Neverwinter"),
                Map.entry(29, "Port Llast"),
                Map.entry(30, "Mirabar"),
                Map.entry(31, "Luskan"),
                Map.entry(32, "Ruathym"),
                Map.entry(33, "Fireshear"),
                Map.entry(34, "Fireshear Mines"),
                Map.entry(35, "Aurilssbaarg"),
                Map.entry(36, "Bjorn's Hold"),
                Map.entry(37, "Icewolf"),
                Map.entry(38, "Freezefire's Lair"),
                Map.entry(39, "Daggerford"),
                Map.entry(40, "Way Inn"),
                Map.entry(47, "Far Windward"),
                Map.entry(48, "Trackless Sea Tours"),
                Map.entry(49, "Tower of Twilight"),
                Map.entry(50, "The Crossroads"),
                Map.entry(51, "Luskan, Port Llast, Neverwinter Area"),
                Map.entry(52, "Mirabar, Longsaddle Area"),
                Map.entry(53, "Neverinter, Leilon Area"),
                Map.entry(54, "Triboar, Yartar Area"),
                Map.entry(55, "Waterdeep Area"),
                Map.entry(56, "Secomber Area"),
                Map.entry(57, "Loudwater, Llorkh Area"),
                Map.entry(58, "Fireshear"),
                Map.entry(59, "Freezefire's Lair"),
                Map.entry(60, "Orlumbor"),
                Map.entry(61, "Luskan"),
                Map.entry(62, "Luscan")
        ));

        GAME_LIST_MAP_MAP.put(Utils.GameList.NeverwinterNights, Map.ofEntries(
                Map.entry(1, "Neverwinter"),
                Map.entry(2, "Warehouse"),
                Map.entry(3, "Wharves"),
                Map.entry(4, "Southwall"),
                Map.entry(5, "Windy Cliffs"),
                Map.entry(6, "Port Llast"),
                Map.entry(7, "Gallant Prince"),
                Map.entry(8, "Luskan"),
                Map.entry(9, "Vilnask"),
                Map.entry(10, "Crossergate"),
                Map.entry(11, "Lost Hills"),
                Map.entry(12, "Triboar"),
                Map.entry(13, "Chargen"),
                Map.entry(14, "Triboar Lower"),
                Map.entry(15, "Berun's Hill"),
                Map.entry(16, "Longsaddle"),
                Map.entry(17, "Longsaddle Lower"),
                Map.entry(18, "NW Woods"),
                Map.entry(19, "NE Woods"),
                Map.entry(20, "W Woods"),
                Map.entry(21, "E Woods"),
                Map.entry(22, "SW Woods"),
                Map.entry(23, "SE Woods"),
                Map.entry(24, "Floodblest"),
                Map.entry(25, "Nightsedge"),
                Map.entry(26, "Nightsedge Caverns"),
                Map.entry(27, "Sewers"),
                Map.entry(28, "Utheraal"),
                Map.entry(29, "Trisk"),
                Map.entry(30, "Port Llast")
        ));

        GAME_LIST_MAP_MAP.put(Utils.GameList.ChampionsOfKrynn, Map.ofEntries(
                Map.entry(32, "Throtl, Throtl Temple"),
                Map.entry(34, "Throtl Catacombs"),
                Map.entry(48, "Gargath"),
                Map.entry(49, "Gargath Keep"),
                Map.entry(50, "Jalek"),
                Map.entry(64, "Nereka City and Base"),
                Map.entry(66, "Nereka Prison"),
                Map.entry(67, "Tomb of Sir Dargaard"),
                Map.entry(68, "Southern Outpost"),
                Map.entry(80, "Sanction Docks"),
                Map.entry(81, "Temple of Huerzyd"),
                Map.entry(82, "Temple of Duerghast"),
                Map.entry(96, "Citadels"),
                Map.entry(97, "Kernen Square"),
                Map.entry(99, "Ogre Base")
        ));

        GAME_LIST_MAP_MAP.put(Utils.GameList.DeathKnightsOfKrynn, Map.ofEntries(
                Map.entry(32, "Kalaman"),
                Map.entry(33, "Vingaard Keep"),
                Map.entry(34, "Cekos"),
                Map.entry(36, "Gargath Outpost"),
                Map.entry(49, "High Clerist's Tower"),
                Map.entry(50, "Dragon Pit"),
                Map.entry(51, "Throtl Keep"),
                Map.entry(64, "Graveyard"),
                Map.entry(65, "Kuo-Toa Slave Ship"),
                Map.entry(66, "Gnome Village (Quazle)"),
                Map.entry(67, "Turef"),
                Map.entry(80, "Cerberus, Dulcimer"),
                Map.entry(82, "Voice Wood"),
                Map.entry(83, "Shipwreck, Cursed Village, Fun House, Father of Trees"),
                Map.entry(96, "Dargaard Keep, First Floor"),
                Map.entry(97, "Dargaard Keep, Second Floor"),
                Map.entry(98, "Dargaard Keep, Third Floor"),
                Map.entry(99, "Challenge")
        ));

        GAME_LIST_MAP_MAP.put(Utils.GameList.DarkQueenOfKyrnn, Map.ofEntries());

        GAME_LIST_MAP_MAP.put(Utils.GameList.CountdownToDoomsday, Map.ofEntries(
                Map.entry(16, "Chicagorg"),
                Map.entry(32, "Spy Ship"),
                Map.entry(35, "Asteroid Base"),
                Map.entry(48, "Asteroid Base, Level 1"),
                Map.entry(49, "Asteroid Base, Level 2"),
                Map.entry(50, "Pirate Ship, Levels 1-5"),
                Map.entry(51, "Pirate Ship, Levels 6-10"),
                Map.entry(52, "Pirate Ship, Levels 11-15"),
                Map.entry(65, "Desert Runner Village"),
                Map.entry(66, "Mars Base Gradiuvs Mons"),
                Map.entry(67, "More Asteroid Bases"),
                Map.entry(81, "Lowlander Village, Venusian Space Elevator Ruins"),
                Map.entry(82, "Venus RAM Base"),
                Map.entry(96, "Mercury Merchants Area"),
                Map.entry(97, "Mariposa Core"),
                Map.entry(98, "Mercurian Finale, Weapons Control Level"),
                Map.entry(99, "Enemy Ships")
        ));

        GAME_LIST_MAP_MAP.put(Utils.GameList.MatrixCubed, Map.ofEntries(
                Map.entry(1, "Luna Base"),
                Map.entry(17, "Caloris Space Port"),
                Map.entry(18, "Asteroid"),
                Map.entry(33, "Losangelorg Sprawls"),
                Map.entry(34, "Historical Museum, Levels 2-1"),
                Map.entry(35, "Asteroid Base"),
                Map.entry(38, "Losangelorg Sprawls"),
                Map.entry(49, "Lowlander Village"),
                Map.entry(50, "Venus Laboratory, Level 1"),
                Map.entry(51, "Venus Laboratory, Level 2"),
                Map.entry(52, "Lowlander Mines"),
                Map.entry(64, "Luna Base"),
                Map.entry(65, "Tsai Weaponry Labs"),
                Map.entry(66, "RAM Battler, Deimos Level 19-41"),
                Map.entry(80, "Mars Prison, Level 1"),
                Map.entry(81, "PURGE Headquarters, Floors Ground-Upper"),
                Map.entry(82, "NEO Installation"),
                Map.entry(84, "Mars Prison, Level 2"),
                Map.entry(96, "Living Ship"),
                Map.entry(97, "Living Ship"),
                Map.entry(112, "Stormrider University"),
                Map.entry(113, "Genetics Foundation Building, Levels 1-4"),
                Map.entry(114, "Jupiter Finale"),
                Map.entry(115, "Jupiter Aircar")
        ));
    }

    public DaxGeoFile(String fileName) {
        super(fileName, true);
        processBlocks();
    }

    public List<GeoMapRecord> getMaps() {
        return maps;
    }

    private void processBlocks() {
        maps = new ArrayList<>();
        for (DaxFileBlock block : blocks) {
            List<GeoWallRecord> walls = new ArrayList<>();

            for (var i = 0; i < 256; i++) {
                var row = i >> 4;
                var col = i & 0x0f;

                byte[] data = block.data();

                var neWallType = data[i + 2];
                var north = (byte) (neWallType >> 4) & 0x0f;
                var east = (byte) (neWallType & 0x0f);

                var swWallType = data[i + 258];
                var south = (byte) (swWallType >> 4) & 0x0f;
                var west = (byte) (swWallType & 0x0f);

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

            maps.add(new GeoMapRecord(getMapNameFrom(block), walls, block.id()));
        }
    }

    private String getMapNameFrom(DaxFileBlock block) {
        var mapname = String.valueOf(block.id());

        Utils.GameList game = Utils.determineGameFrom(block.fullFilename());
        if (game != Utils.GameList.Unknown) {
            // do we have a map with that id?
            if (GAME_LIST_MAP_MAP.containsKey(game)) {
                var names = GAME_LIST_MAP_MAP.get(game);
                if (names.containsKey(block.id())) {
                    mapname = names.get(block.id());
                }
            }
        }
        return mapname;
    }
}
