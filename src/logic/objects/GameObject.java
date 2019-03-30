package logic.objects;

import logic.Game;
import util.MapExplaining;

import java.awt.image.BufferedImage;

abstract public class GameObject {
    protected String name;
    protected double x;
    protected double y;
    protected BufferedImage image;
    protected int SPEED = 10;

    protected boolean canJump = true;
    protected boolean inJump = false;

    protected int characterHeight = 64;
    protected int characterWidth = 64;

    public  GameObject(){

    }

    public GameObject(String name, BufferedImage bufferedImage) {
        this.name = name;
        image = bufferedImage;
    }

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

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    abstract public void update();


    protected void gravity() {
        if (!onFloor()) {
            y = y + SPEED;
        } else {
            canJump = true;
            inJump = false;
        }
    }

    protected boolean onFloor() {
        String[][] mass = Game.game.getEnvironment().getEnviroment();
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



    protected boolean canGoThrough(String cell) {
        return MapExplaining.isCollisionTile(cell);
    }


}
