package gui;

import logic.Game;
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
//    private int width;
//    private int height;
    //private BufferedImage img;

    public void init(int width, int height) {
//        this.width = width;
//        this.height = height;
        gameGUI = this;
        setSize(width, height);
        game = new Game();
        game.init();
        new Thread(game).start();


//        File file = new File("");
//        try {
//            img = ImageIO.read(new File(file.getAbsoluteFile() + "/resources/menu/plx-5.png"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        setBackground(Color.CYAN);
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
        long time = System.currentTimeMillis();
        super.paintComponent(g);

        System.out.println(System.currentTimeMillis() - time);
        //background
        drawBack(g);
        System.out.println(System.currentTimeMillis() - time);
        //map
        drawMap(g);
        System.out.println(System.currentTimeMillis() - time);
        //hero
        g.drawImage(
                game.getHero().getNextHeroImage(),
                (int) game.getHero().getX(),
                (int) game.getHero().getY(),
                null);
        System.out.println(System.currentTimeMillis() - time);


        System.out.println("-------------\n\n");

    }

    private void drawBack(Graphics g) {
        //https://stackoverflow.com/questions/658059/graphics-drawimage-in-java-is-extremely-slow-on-some-computers-yet-much-faster
//        long time = System.currentTimeMillis();
        List<Environment> allEnvironment = game.getEnvironment().getAllEnvironment();
        for (int i = 0; i < allEnvironment.size(); i++) {
            g.drawImage(
                    allEnvironment.get(i).getImage(),
                    (int) allEnvironment.get(i).getX(),
                    (int) allEnvironment.get(i).getY(),
                    null
            );
        }
//        long exit = System.currentTimeMillis();
//        if (exit - time > 15){
//            System.out.println("!!!" + (exit - time));
//        }
    }

    private void drawMap(Graphics g) {
        String[][] mass = game.getEnvironment().getMass();
        for (int i = 0; i < mass.length; i++) {
            for (int j = 0; j < mass[i].length; j++) {
                BufferedImage imageByExp = MapExplaining.getImageByExp(mass[i][j]);
                g.drawImage(imageByExp, j * Game.SIZE, i * Game.SIZE, Game.SIZE, Game.SIZE, null);
            }
        }

//        System.exit(1);
    }


}
