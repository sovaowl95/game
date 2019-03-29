package logic.objects;

import java.awt.image.BufferedImage;

public class Item extends GameObject {

    public Item(String name, BufferedImage bufferedImage) {
        super(name, bufferedImage);
    }

    @Override
    public void update() {
        //todo: grav   ity
//        gravity();
    }


}
