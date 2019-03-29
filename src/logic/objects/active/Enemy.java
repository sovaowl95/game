package logic.objects.active;

import util.Animation;

import java.awt.image.BufferedImage;

public class Enemy extends CharacterImpl {

    public Enemy() {
        SPEED = 5;
        animation = new Animation("golem%s/%d.png", 11);
        animation.init();
    }

    @Override
    public void move() {

    }

    @Override
    public void attack1() {

    }

    @Override
    public void attack2() {

    }

    @Override
    public void update() {
        gravity();

        if (canGoRight() && direction) {
            x += SPEED;
        } else {
            direction = false;
        }

        if (canGoLeft() && !direction) {
            x -= SPEED;
        } else {
            direction = true;
        }
    }
}
