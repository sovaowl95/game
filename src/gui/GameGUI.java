package gui;

import logic.Game;
import logic.objects.active.Enemy;
import logic.world.Environment;
import util.MapExplaining;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class GameGUI extends JPanel implements Runnable {
    private Game game;
    private Thread thread;
    public static GameGUI gameGUI;
    private int width;
    private int height;

    private int translateX;
    private int translateY;
    //private BufferedImage img;

    public void init(int width, int height) {
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
        long startTime = System.currentTimeMillis();
        long currTime = startTime;
        long elapsedTime;


        while (!game.isPause()) {
            elapsedTime = System.currentTimeMillis() - currTime;
            currTime += elapsedTime;
            repaint();
            //todo: frame time
            try {
                Thread.sleep(5);
            } catch (InterruptedException ex) {
                System.err.println("Failed to sleep...");
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
//        long time = System.currentTimeMillis();
        super.paintComponent(g);

        double x = game.getHero().getX() + game.getHero().getCharacterWidth() / 2;
//        double y = game.getHero().getY();

        int mapLength = game.getEnvironment().getMap()[0].length * Game.SIZE;

//        System.out.println("---");
//        System.out.println("width = " + width);
//        System.out.println("x = " + x);
//        System.out.println("translateX = " + translateX);
//        System.out.println("map length= " + mapLength);
//        System.out.println("res: " + (mapLength - x));
//        System.out.println("---");


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

//        System.out.println(System.currentTimeMillis() - time);

        //background
        drawBack(g);
//        System.out.println(System.currentTimeMillis() - time);

        //map
        drawMap(g);
//        System.out.println(System.currentTimeMillis() - time);

        //hero
        drawHeroes(g);

        //enemies
        drawEnemies(g);

        drawTopMap(g);
//        System.out.println(System.currentTimeMillis() - time);
//        System.out.println("-------------\n\n");
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
            Enemy enemy = game.getEnemiesArrayList().get(i);
            g.drawImage(
                    enemy.getNextImage(),
                    (int) enemy.getX(),
                    (int) enemy.getY(),
                    null);
        }
    }

    private void drawBack(Graphics g) {
        //https://stackoverflow.com/questions/658059/graphics-drawimage-in-java-is-extremely-slow-on-some-computers-yet-much-faster
//        long time = System.currentTimeMillis();
        List<Environment> allEnvironment = game.getEnvironment().getAllEnvironment();
        for (int i = 0; i < allEnvironment.size(); i++) {
            BufferedImage image = allEnvironment.get(i).getImage();
            int x = (int) allEnvironment.get(i).getX();
            int y = (int) allEnvironment.get(i).getY();
            if (translateX != 0) {
                x -= translateX;
            }
            g.drawImage(image, x, y, null);
        }
//        long exit = System.currentTimeMillis();
//        if (exit - time > 15){
//            System.out.println("!!!" + (exit - time));
//        }
    }

    private void drawMap(Graphics g) {
        String[][] mass = game.getEnvironment().getMap();
        for (int i = 0; i < mass.length; i++) {
            for (int j = 0; j < mass[i].length; j++) {
                BufferedImage imageByExp = MapExplaining.getImageByExp(mass[i][j]);
                g.drawImage(imageByExp, j * Game.SIZE, i * Game.SIZE, Game.SIZE, Game.SIZE, null);
            }
        }
    }

    private void drawTopMap(Graphics g) {
        String[][] mass = game.getEnvironment().getMap();
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
