package logic;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class Animation {
    private static ArrayList<BufferedImage> imagesHeroWalk;
    private static HeroThreadAnimation thread;

    static {
        imagesHeroWalk = new ArrayList<>();
        loadHeroMoveAnimations();
        thread = new HeroThreadAnimation(imagesHeroWalk.size());
        thread.start();
    }

    public static void loadHeroMoveAnimations() {
        for (int i = 0; i < 10; i++) {
//            BufferedImage bufferedImage = loadImage("CharacterSprites/Run/" + i + ".png");
            BufferedImage bufferedImage = loadImage("hero2/" + i + ".png");
            imagesHeroWalk.add(bufferedImage);
        }
    }


    //false = left;
    //true = right;
    public static BufferedImage getImage(boolean direction, int heroHeight, int heroWidth) {
        int frameNum = thread.getFrameNum();
        BufferedImage image;
        if (direction) {
            image = imagesHeroWalk.get(frameNum);
        } else {
            BufferedImage bufferedImage = imagesHeroWalk.get(frameNum);
            AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
            tx.translate(-bufferedImage.getWidth(null), 0);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            bufferedImage = op.filter(bufferedImage, null);
            image = bufferedImage;
        }

        AffineTransform tx = new AffineTransform();
        double xScale = Game.game.getHero().getWidth() * 1.0 / image.getWidth();
        double yScale = Game.game.getHero().getHeight() * 1.0 /image.getHeight();
        System.out.println("xScale = " + xScale);
        System.out.println("yScale = " + yScale);
        tx.scale(xScale, yScale);
        AffineTransformOp scale = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
//        BufferedImage res = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        BufferedImage res = new BufferedImage(Game.game.getHero().getWidth(), Game.game.getHero().getHeight(), BufferedImage.TYPE_INT_ARGB);
//        BufferedImage res = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        res = scale.filter(image, res);
        return res;
    }


    private static BufferedImage loadImage(String name) {
        File file = new File("");
        BufferedImage bufferedImage;
        try {
            File file1 = new File(file.getAbsoluteFile() + "/resources/" + name);
            System.out.println(file1);
            bufferedImage = ImageIO.read(file1);
            return bufferedImage;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

class HeroThreadAnimation extends Thread {
    private int frameNum;
    private long lastFrameChangeTime;
    private long nextFrameIn;

    private int frameTime;
    private int numberOfSprites;

    public HeroThreadAnimation(int size) {
        this.numberOfSprites = size;
        frameTime = 1000 / size;
    }

    public int getFrameNum() {
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
        }
    }
}
