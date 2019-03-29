package logic.objects.active;

import util.Animation;
import logic.Game;
import util.MapExplaining;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Hero extends CharacterImpl {
    public Hero() {
        SPEED = 5;
        animation = new Animation();
        animation.init();
    }

    @Override
    public void move() {

    }

//    @Override
//    public void jump() {
//        if (!onTop()) {
//            if (currentJumpHeight - SPEED <= jumpHeight) {
//                y = y - SPEED;
//                currentJumpHeight = currentJumpHeight + SPEED;
//            } else {
//                y = y - Math.abs(jumpHeight - currentJumpHeight);
//                currentJumpHeight = 0;
//                inJump = false;
//            }
//        } else {
//            inJump = false;
//        }
//    }

    @Override
    public void attack1() {

    }

    @Override
    public void attack2() {

    }

    @Override
    public void update() {
        HashMap<Integer, Boolean> keyMaps = Game.game.getKeyMaps();
        if (keyMaps.get((int) 'a')) {
            if (canGoLeft()) {
                x = x - SPEED;
                direction = false;
            }
        }

        if (keyMaps.get((int) 'd')) {
            if (canGoRight()) {
                x = x + SPEED;
                direction = true;
            }
        }

        if (keyMaps.get((int) ' ') && canJump) {
            jump();
            inJump = true;
            canJump = false;
        }

//        if (keyMaps.get((int) 'w')) {
//            y = y - SPEED;
//            //todo: gravitation?
//        }
//
//        if (keyMaps.get((int) 's')) {
//            y = y + SPEED;
//            //todo: gravitation?
//        }

        if (inJump) {
            jump();
        } else {
            gravity();
        }
    }
}
