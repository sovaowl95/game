package logic.objects.active;

import logic.Game;
import logic.objects.GameObject;
import util.Animation;
import util.MapExplaining;

import java.awt.image.BufferedImage;


abstract public class CharacterImpl extends GameObject implements Character {
    protected Animation animation;

    protected int characterHeight = 64;
    protected int characterWidth = 64;

    protected int jumpHeight = 150;
    protected int currentJumpHeight;

    protected boolean direction = true;
    protected boolean canJump = true;
    protected boolean inJump = false;

    public int getCharacterHeight() {
        return characterHeight;
    }

    public void setCharacterHeight(int characterHeight) {
        this.characterHeight = characterHeight;
    }

    public int getCharacterWidth() {
        return characterWidth;
    }

    public void setCharacterWidth(int characterWidth) {
        this.characterWidth = characterWidth;
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


    protected boolean canGoLeft() {
        String[][] mass = Game.game.getEnvironment().getMap();
        int line;
        int col;

        line = ((int) getY() + 1) / Game.SIZE;
        col = ((int) getX() - SPEED) / Game.SIZE;
        if (!canGoThrough(mass[line][col])) {
            return false;
        }

        line = (int) (getY() + characterHeight - 1) / Game.SIZE;
        col = ((int) getX() - SPEED) / Game.SIZE;
        if (!canGoThrough(mass[line][col])) {
            return false;
        }

        return true;
    }

    protected boolean canGoRight() {
        String[][] mass = Game.game.getEnvironment().getMap();
        int line;
        int col;

        line = (int) (getY() + 1) / Game.SIZE;
        col = ((int) getX() + characterWidth + SPEED) / Game.SIZE;
        if (!canGoThrough(mass[line][col])) {
            return false;
        }

        line = (int) (getY() + characterHeight - 1) / Game.SIZE;
        col = ((int) getX() + characterWidth + SPEED) / Game.SIZE;
        if (!canGoThrough(mass[line][col])) {
            return false;
        }
        return true;
    }

    protected boolean onFloor() {
        String[][] mass = Game.game.getEnvironment().getMap();
        int line;
        int col;
        line = (int) (getY() + characterHeight + SPEED + 1) / Game.SIZE;
        col = ((int) getX()) / Game.SIZE;
        if (!canGoThrough(mass[line][col])) {
            return true;
        }

        line = (int) (getY() + characterHeight + SPEED + 1) / Game.SIZE;
        col = ((int) getX() + characterWidth) / Game.SIZE;
        if (!canGoThrough(mass[line][col])) {
            return true;
        }
        return false;
    }

    protected boolean onTop() {
        String[][] mass = Game.game.getEnvironment().getMap();
        int line;
        int col;
        line = (int) (getY() - SPEED - 1) / Game.SIZE;
        col = ((int) getX()) / Game.SIZE;
        if (!canGoThrough(mass[line][col])) {
            return true;
        }

        line = (int) (getY() - SPEED - 1) / Game.SIZE;
        col = ((int) getX() + characterWidth) / Game.SIZE;
        if (!canGoThrough(mass[line][col])) {
            return true;
        }
        return false;
    }

    protected void gravity() {
        if (!onFloor()) {
            y = y + SPEED;
        } else {
            canJump = true;
            inJump = false;
        }
    }

    public BufferedImage getNextImage() {
        return animation.getImage(direction);
    }

    protected boolean canGoThrough(String cell) {
        return MapExplaining.isCollisionTile(cell);
    }

}
