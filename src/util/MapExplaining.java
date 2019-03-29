package util;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.HashSet;

public class MapExplaining {
    private static HashMap<String, BufferedImage> mapExp;
    private static HashSet<String> topTileLevel;
    private static HashSet<String> enemiesSet;
    private static HashSet<String> noCollisionSet;

    static {
        topTileLevel = new HashSet<>();
        enemiesSet = new HashSet<>();
        noCollisionSet = new HashSet<>();
        mapExp = new HashMap<>();

        //map
        mapExp.put("G1", ImageLoader.loadImage("/world/_leafy ground/leafy_ground01.png"));
        mapExp.put("X", ImageLoader.loadImage("/world/_rocky/rocky01.png"));
        mapExp.put("W", ImageLoader.loadImage("/world/_ground/ground08.png"));
        mapExp.put("L1", ImageLoader.loadImage("/world/_lava/lava 1.png"));
        mapExp.put("U1", ImageLoader.loadImage("/world/JungleTile.png"));
        mapExp.put("J1", ImageLoader.loadImage("/world/UndergroundTile.png"));

        mapExp.put("WF", ImageLoader.loadImage("/world/_water/water_full_2_transp.png"));
        topTileLevel.add("WF");
        noCollisionSet.add("WF");

        //back
        mapExp.put("back_1", ImageLoader.loadImage("/PNG/game_background_3/game_background_3. 2.png"));
        mapExp.put("back_cloud_1", ImageLoader.loadImage("/PNG/game_background_3/layers/clouds_1.png"));
        mapExp.put("back_cloud_2", ImageLoader.loadImage("/PNG/game_background_3/layers/clouds_2.png"));


        //hero
//        mapExp.put("B1", ImageLoader.loadImage("/hero/hero.jpg"));

        //enemies
        enemiesSet.add("E1");


        noCollisionSet.add("O");
    }


    public static BufferedImage getImageByExp(String name) {
        return mapExp.get(name);
    }

    public static boolean isTopLevelTile(String tileName) {
        return topTileLevel.contains(tileName);
    }

    public static boolean isEnemy(String name) {
        return enemiesSet.contains(name);
    }

    public static boolean isCollisionTile(String name) {
        return noCollisionSet.contains(name);
    }
}
