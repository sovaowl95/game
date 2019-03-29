package logic.objects.active;

import util.Animation;
import logic.Game;
import util.MapExplaining;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Hero extends CharacterImpl {
    public Hero() {
        super();
        SPEED = 5;
        animation = new Animation("hero2%s/%d.png", 10);
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
        //todo:
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
                lastMoveTime = System.currentTimeMillis();
            }
        }

        if (keyMaps.get((int) 'd')) {
            if (canGoRight()) {
                x = x + SPEED;
                direction = true;
                lastMoveTime = System.currentTimeMillis();
            }
        }

        if (keyMaps.get((int) ' ') && canJump) {
            jump();
            inJump = true;
            canJump = false;
        }

        if (keyMaps.get(-1)) {
            attack1();
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

        //todo: collision
    }
}
