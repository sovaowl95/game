package util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageLoader {
    public static BufferedImage loadImage(String name) {
        File file = new File("");
        BufferedImage bufferedImage;
        try {
            File file1 = new File(file.getAbsoluteFile() + "/resources/" + name);
            System.out.println(file1.getAbsolutePath());
            bufferedImage = ImageIO.read(file1);
            return bufferedImage;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
