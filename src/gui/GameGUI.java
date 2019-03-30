package gui;

import logic.Game;
import logic.objects.GameObject;
import logic.objects.Item;
import logic.objects.active.Enemy;
import util.Constants;
import util.ItemLoader;
import util.MapExplaining;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GameGUI extends JPanel implements Runnable {
    private Game game;
    private Thread thread;
    public static GameGUI gameGUI;
    private int width;
    private int height;

    private int translateX;
    private int translateY;


    public void init(int width, int height) {
        removeAll();
        this.width = width;
        this.height = height;
        gameGUI = this;
        setSize(width, height);
        game = new Game();
        game.init();
        new Thread(game).start();
        setBackground(Color.black);
        setFocusable(true);
        requestFocusInWindow();
//        setCursor(Cursor.getPredefinedCursor(Cursor.));
    }

    public void unpause() {
        game.unpause();
        thread = new Thread(this);
        thread.start();
    }

    public void pause() {
        game.pause();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        long startFrameTime;
        long endFrameTime;
        while (!game.isPause()) {
            System.out.println(Thread.currentThread().getName());
            startFrameTime = System.currentTimeMillis();
            repaint();
            endFrameTime = System.currentTimeMillis();
            long sleepTime;
            sleepTime = Constants.FRAME_TIME - (endFrameTime - startFrameTime);
            try {
                TimeUnit.MILLISECONDS.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {

        System.out.println(Thread.currentThread().getName());
        long time = System.currentTimeMillis();

        super.paintComponent(g);
        System.out.println("SUPER TIME: " +(System.currentTimeMillis() - time));


        double x = game.getHero().getX() + game.getHero().getCharacterWidth() / 2;
        double y = game.getHero().getY();
        int mapLength = game.getEnvironment().getEnviroment()[0].length * Game.SIZE;
        boolean rightSide = x > width / 2;
        boolean leftSide = mapLength - x - width / 2 > 0;
        if (rightSide && leftSide) {
            translateX = (int) (-x + width / 2);
        } else if (rightSide) {
            translateX = (int) (-mapLength + width);
        } else if (leftSide) {
            translateX = 0;
        }
        g.translate(translateX, translateY);

        System.out.println("TRANSLATE TIME: " +(System.currentTimeMillis() - time));

        //background
        drawBack(g);
        System.out.println("BACK TIME: " +(System.currentTimeMillis() - time));


        //map
        drawMap(g);
        System.out.println("MAP TIME: " +(System.currentTimeMillis() - time));

        //hero
        drawHeroes(g);
        System.out.println("HERO TIME: " +(System.currentTimeMillis() - time));

        //enemies
        drawEnemies(g);
        System.out.println("ENEMIES TIME: " +(System.currentTimeMillis() - time));

        drawItems(g);
        System.out.println("ITEMS TIME: " +(System.currentTimeMillis() - time));

        drawTopMap(g);
        System.out.println("TOP MAP TIME: " +(System.currentTimeMillis() - time));

        //todo: DMG
        drawDmg(g);
        System.out.println("DMG TIME: " +(System.currentTimeMillis() - time));
    }


    private void drawHeroes(Graphics g) {
        g.drawImage(
                game.getHero().getNextImage(),
                (int) game.getHero().getX(),
                (int) game.getHero().getY(),
                null);
    }

    private void drawEnemies(Graphics g) {
        for (int i = 0; i < game.getEnemiesArrayList().size(); i++) {
            System.out.println("i = " + i);
            Enemy enemy = game.getEnemiesArrayList().get(i);
            long time = System.currentTimeMillis();
            BufferedImage nextImage = enemy.getNextImage();
            System.out.println("time to get img" + (System.currentTimeMillis() - time));
            g.drawImage(
                    nextImage,
                    (int) enemy.getX(),
                    (int) enemy.getY(),
                    null);
            System.out.println("time to draw enemy" + (System.currentTimeMillis() - time));
        }
    }

    private void drawDmg(Graphics g) {

    }

    //https://stackoverflow.com/questions/658059/graphics-drawimage-in-java-is-extremely-slow-on-some-computers-yet-much-faster
//        long time = System.currentTimeMillis();
    private void drawBack(Graphics g) {
        List<GameObject> allEnvironment = game.getEnvironment().getBackgroundEnv();
        for (int i = 0; i < allEnvironment.size(); i++) {
            BufferedImage image = allEnvironment.get(i).getImage();
            int x = (int) allEnvironment.get(i).getX();
            int y = (int) allEnvironment.get(i).getY();
            if (translateX != 0) {
                x -= translateX;
            }
            g.drawImage(image, x, y, null);
        }
    }

    private void drawItems(Graphics g) {
        List<Item> items = game.getEnvironment().getItemList();
        for (int i = 0; i < items.size(); i++) {
            BufferedImage image = items.get(i).getImage();
            int x = (int) items.get(i).getX();
            int y = (int) items.get(i).getY();
            g.drawImage(image, x, y, null);
        }
    }

    private void drawMap(Graphics g) {
        String[][] mass = game.getEnvironment().getEnviroment();
        for (int i = 0; i < mass.length; i++) {
            for (int j = 0; j < mass[i].length; j++) {
                BufferedImage imageByExp = MapExplaining.getImageByExp(mass[i][j]);
                g.drawImage(imageByExp, j * Game.SIZE, i * Game.SIZE, Game.SIZE, Game.SIZE, null);
            }
        }
    }

    private void drawTopMap(Graphics g) {
        String[][] mass = game.getEnvironment().getEnviroment();
        for (int i = 0; i < mass.length; i++) {
            for (int j = 0; j < mass[i].length; j++) {
                BufferedImage imageByExp = MapExplaining.getImageByExp(mass[i][j]);
                if (MapExplaining.isTopLevelTile(mass[i][j])) {
                    g.drawImage(imageByExp, j * Game.SIZE, i * Game.SIZE, Game.SIZE, Game.SIZE, null);
                }
            }
        }
    }
}
