package logic.world;

import logic.Game;
import logic.objects.GameObject;
import util.MapExplaining;

import java.awt.image.BufferedImage;


abstract public class Environment extends GameObject {


    public Environment(String name, BufferedImage bufferedImage) {
        super(name, bufferedImage);
    }

    @Override
    public void update() {
        int width = MapExplaining.getImageByExp(name).getWidth();

        if (x <= -width) {
            x = Game.SIZE * EnvironmentCollection.map.length + width;
        } else {
            x = x - SPEED;
        }
    }

}
