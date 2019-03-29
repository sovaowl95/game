package util;

import logic.objects.Item;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ItemLoader {
    public static List<String> loadItems(int level) {
        File file = new File("");
        try {
            File file1 = new File(file.getAbsoluteFile() + "/resources/worldMap/level_" + level + "_items");
            BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(file1.getAbsolutePath()));
            List<String> items = new ArrayList<>();
            String input;
            while ((input = bufferedReader.readLine()) != null) {
                items.add(input);
            }
            return items;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
