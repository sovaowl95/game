package logic.objects.active;

import logic.Animation;
import logic.Game;
import util.MapExplaining;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Hero extends CharacterImpl {
    private boolean canJump = true;
    private boolean inJump = false;
    private int jumpHeight = 150;
    private int currentJumpHeight;

    //todo: WIDTH AND HEIGHT BUFFER
    private int heroHeight = 64;
    private int heroWidth = 64;

    private boolean direction = true;


    public Hero() {
        SPEED = 5;
        image = MapExplaining.getImageByExp("hero");
    }

    @Override
    public void move() {

    }

    @Override
    public void jump() {
        if (!onTop()) {
            if (currentJumpHeight - SPEED <= jumpHeight) {
                y = y - SPEED;
                currentJumpHeight = currentJumpHeight + SPEED;
            } else {
                y = y - Math.abs(jumpHeight - currentJumpHeight);
                currentJumpHeight = 0;
                inJump = false;
            }
        } else {
            inJump = false;
        }
    }

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

    private boolean canGoLeft() {
        String[][] mass = Game.game.getEnvironment().getMass();
        int line;
        int col;

        line = ((int) getY() + 1) / Game.SIZE;
        col = ((int) getX() - SPEED) / Game.SIZE;
        if (!mass[line][col].equals("O")) {
            return false;
        }

        line = (int) (getY() + heroHeight - 1) / Game.SIZE;
        col = ((int) getX() - SPEED) / Game.SIZE;
        if (!mass[line][col].equals("O")) {
            return false;
        }

        return true;
    }

    private boolean canGoRight() {
        String[][] mass = Game.game.getEnvironment().getMass();
        int line;
        int col;

        line = (int) (getY() + 1) / Game.SIZE;
        col = ((int) getX() + heroWidth + SPEED) / Game.SIZE;
        if (!mass[line][col].equals("O")) {
            return false;
        }

        line = (int) (getY() + heroHeight - 1) / Game.SIZE;
        col = ((int) getX() + heroWidth + SPEED) / Game.SIZE;
        if (!mass[line][col].equals("O")) {
            return false;
        }


        return true;
    }

    public boolean onFloor() {
        String[][] mass = Game.game.getEnvironment().getMass();
        int line;
        int col;
        line = (int) (getY() + heroHeight + SPEED + 1) / Game.SIZE;
        col = ((int) getX()) / Game.SIZE;
        if (!mass[line][col].equals("O")) {
            return true;
        }

        line = (int) (getY() + heroHeight + SPEED + 1) / Game.SIZE;
        col = ((int) getX() + heroWidth) / Game.SIZE;
        if (!mass[line][col].equals("O")) {
            return true;
        }
        return false;
    }

    public boolean onTop() {
        String[][] mass = Game.game.getEnvironment().getMass();
        int line;
        int col;
        line = (int) (getY() - SPEED - 1) / Game.SIZE;
        col = ((int) getX()) / Game.SIZE;
        if (!mass[line][col].equals("O")) {
            return true;
        }

        line = (int) (getY() - SPEED - 1) / Game.SIZE;
        col = ((int) getX() + heroWidth) / Game.SIZE;
        if (!mass[line][col].equals("O")) {
            return true;
        }
        return false;
    }

    private void gravity() {
        if (!onFloor()) {
            y = y + SPEED;
        } else {
            canJump = true;
            inJump = false;
        }
    }

    public int getWidth() {
        return heroWidth;
    }

    public int getHeight() {
        return heroHeight;
    }

    public BufferedImage getNextHeroImage() {
//        heroHeight = 64;
//        heroWidth = 64;
//        return MapExplaining.getImageByExp("hero");
        BufferedImage image = Animation.getImage(direction, heroHeight, heroWidth);
//        heroWidth = image.getWidth();
//        heroHeight = image.getHeight();
        return image;
    }


}
