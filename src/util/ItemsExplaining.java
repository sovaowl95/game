package util;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.HashSet;

public class ItemsExplaining {
    private static HashMap<String, BufferedImage> itemExp;

    static {
        itemExp = new HashMap<>();
        itemExp.put("healingPotion_01", ImageLoader.loadImage("/cake_64/29.png"));
    }


    public static BufferedImage getImageByExp(String name) {
        return itemExp.get(name);
    }
}
