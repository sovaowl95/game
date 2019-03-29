package util;

import logic.Game;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Animation {
    private String path;
    private int numOfFrames;
    private ArrayList<BufferedImage> imagesCharacterRun;
    private ArrayList<BufferedImage> imagesCharacterStay;
    private CharacterThreadAnimation threadRun;
    private CharacterThreadAnimation threadStay;

    public Animation(String path, int numOfFrames) {
        this.path = path;
        this.numOfFrames = numOfFrames;
    }

    public void init() {
        imagesCharacterRun = new ArrayList<>();
        loadCharacterMoveAnimations();

        imagesCharacterStay = new ArrayList<>();
        loadCharacterStayAnimations();

        threadRun = new CharacterThreadAnimation(imagesCharacterRun.size());
        threadRun.start();

        threadStay = new CharacterThreadAnimation(imagesCharacterStay.size());
        threadStay.start();
    }

    private void loadCharacterMoveAnimations() {
        for (int i = 0; i < numOfFrames; i++) {
            BufferedImage bufferedImage = ImageLoader.loadImage(String.format(path, "_run", i));
            imagesCharacterRun.add(bufferedImage);
        }
    }

    private void loadCharacterStayAnimations() {
        for (int i = 0; i < numOfFrames; i++) {
            BufferedImage bufferedImage = ImageLoader.loadImage(String.format(path, "_stay", i));
            imagesCharacterStay.add(bufferedImage);
        }
    }


    //false = left;
    //true = right;
    public BufferedImage getRunImage(boolean direction) {
        threadStay.setFrameNum(0);
        int frameNum = threadRun.getFrameNum();
        BufferedImage image;
        if (direction) {
            image = imagesCharacterRun.get(frameNum);
        } else {
            BufferedImage bufferedImage = imagesCharacterRun.get(frameNum);
            AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
            tx.translate(-bufferedImage.getWidth(null), 0);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            bufferedImage = op.filter(bufferedImage, null);
            image = bufferedImage;
        }

        AffineTransform tx = new AffineTransform();
        double xScale = Game.game.getHero().getCharacterWidth() * 1.0 / image.getWidth();
        double yScale = Game.game.getHero().getCharacterHeight() * 1.0 / image.getHeight();
        tx.scale(xScale, yScale);
        AffineTransformOp scale = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        BufferedImage res = new BufferedImage(
                Game.game.getHero().getCharacterWidth(),
                Game.game.getHero().getCharacterHeight(),
                BufferedImage.TYPE_INT_ARGB);
        res = scale.filter(image, res);
        return res;
    }

    public BufferedImage getStayImage(boolean direction) {
        threadRun.setFrameNum(0);
        int frameNum = threadStay.getFrameNum();
        BufferedImage image;
        if (direction) {
            image = imagesCharacterStay.get(frameNum);
        } else {
            BufferedImage bufferedImage = imagesCharacterStay.get(frameNum);
            AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
            tx.translate(-bufferedImage.getWidth(null), 0);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            bufferedImage = op.filter(bufferedImage, null);
            image = bufferedImage;
        }

        AffineTransform tx = new AffineTransform();
        double xScale = Game.game.getHero().getCharacterWidth() * 1.0 / image.getWidth();
        double yScale = Game.game.getHero().getCharacterHeight() * 1.0 / image.getHeight();
        tx.scale(xScale, yScale);
        AffineTransformOp scale = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        BufferedImage res = new BufferedImage(
                Game.game.getHero().getCharacterWidth(),
                Game.game.getHero().getCharacterHeight(),
                BufferedImage.TYPE_INT_ARGB);
        res = scale.filter(image, res);
        return res;
    }
}

class CharacterThreadAnimation extends Thread {
    private int frameNum;
    private long lastFrameChangeTime;
    private long nextFrameIn;

    private int frameTime;
    private int numberOfSprites;

    CharacterThreadAnimation(int size) {
        this.numberOfSprites = size;
        frameTime = 1000 / size;
    }

    int getFrameNum() {
        return frameNum;
    }
    void setFrameNum(int num){
        frameNum = num;
    }

    @Override
    public void run() {
        while (true) {
            lastFrameChangeTime = System.currentTimeMillis();
            while (!Game.game.isPause()) {
                if (nextFrameIn == 0) {
                    if (frameNum == numberOfSprites - 1) {
                        frameNum = 0;
                    } else {
                        frameNum++;
                    }
                    nextFrameIn = System.currentTimeMillis() + frameTime;
                } else {
                    if (System.currentTimeMillis() - lastFrameChangeTime >= frameTime) {
                        lastFrameChangeTime = System.currentTimeMillis();
                        nextFrameIn = 0;
                    }
                }
            }
            try {
                TimeUnit.MILLISECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
