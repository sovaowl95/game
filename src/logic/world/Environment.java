package logic.world;

import logic.Game;
import logic.objects.GameObject;
import util.MapExplaining;

import java.awt.image.BufferedImage;


abstract public class Environment extends GameObject {
    String name;


    public Environment(String name, BufferedImage bufferedImage) {
        this.name = name;
        image = bufferedImage;
    }

    @Override
    public void update() {
        int width = MapExplaining.getImageByExp(name).getWidth();

        if (x <= -width) {
            x = Game.SIZE * EnviromentCollection.map.length + width;
        } else {
            x = x - SPEED;
        }
    }

}
