package logic.objects;

import java.awt.image.BufferedImage;

abstract public class GameObject {
    protected double x;
    protected double y;
    protected BufferedImage image;
    protected int SPEED = 10;

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
}
