package logic.objects.active;

import java.awt.image.BufferedImage;

public interface Character {
    void move();
    void jump();
    void attack1();
    void attack2();
    BufferedImage getNextImage();
}
