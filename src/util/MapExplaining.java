package util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

public class MapExplaining {
    static HashMap<String, BufferedImage> mapExp;

    static {
        mapExp = new HashMap<>();
        //map
        mapExp.put("G1", loadImage("/world/_leafy ground/leafy_ground01.png"));
        mapExp.put("X", loadImage("/world/_rocky/rocky01.png"));

        mapExp.put("W", loadImage("/world/_ground/ground08.png"));
        //mapExp.put("A", loadImage("/world/_rocky/rocky01.png"));
//        mapExp.put("S", loadImage("/world/_rocky/rocky01.png"));
        mapExp.put("L1", loadImage("/world/_lava/lava 1.png"));
        mapExp.put("U1", loadImage("/world/JungleTile.png"));
//        loadImage("/world/");
        mapExp.put("J1", loadImage("/world/UndergroundTile.png"));


        //back
        mapExp.put("back_1", loadImage("/PNG/game_background_3/game_background_3. 2.png"));
        mapExp.put("back_cloud_1", loadImage("/PNG/game_background_3/layers/clouds_1.png"));
        mapExp.put("back_cloud_2", loadImage("/PNG/game_background_3/layers/clouds_2.png"));

        //hero
//        mapExp.put("hero", loadImage("/CharacterSprites/Knight.png"));
                mapExp.put("hero", loadImage("/hero/hero.jpg"));
        mapExp.put("B1", loadImage("/hero/hero.jpg"));
    }


    private static BufferedImage loadImage(String name) {
        File file = new File("");
        BufferedImage bufferedImage;
        try {
            File file1 = new File(file.getAbsoluteFile() + "/resources/" + name);
            System.out.println(file1);
            bufferedImage = ImageIO.read(file1);
            return bufferedImage;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static BufferedImage getImageByExp(String name) {
        return mapExp.get(name);
    }
}
