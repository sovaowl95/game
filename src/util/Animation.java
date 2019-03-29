package util;

import logic.Game;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Animation {
    private ArrayList<BufferedImage> imagesCharacterWalk;
    private CharacterThreadAnimation thread;

    public void init() {
        imagesCharacterWalk = new ArrayList<>();
        loadCharacterMoveAnimations();
        thread = new CharacterThreadAnimation(imagesCharacterWalk.size());
        thread.start();
    }

    private void loadCharacterMoveAnimations() {
        for (int i = 0; i < 10; i++) {
            BufferedImage bufferedImage = ImageLoader.loadImage("hero2/" + i + ".png");
            imagesCharacterWalk.add(bufferedImage);
        }
    }


    //false = left;
    //true = right;
    public BufferedImage getImage(boolean direction) {
        int frameNum = thread.getFrameNum();
        BufferedImage image;
        if (direction) {
            image = imagesCharacterWalk.get(frameNum);
        } else {
            BufferedImage bufferedImage = imagesCharacterWalk.get(frameNum);
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
