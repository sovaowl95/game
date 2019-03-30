package logic;

import gui.GameGUI;
import gui.MainFrame;
import logic.objects.active.Enemy;
import logic.objects.active.Hero;
import logic.world.EnvironmentCollection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class Game implements Runnable {
    private boolean pause = true;
    private Hero hero;
    private ArrayList<Enemy> enemiesArrayList;
    private EnvironmentCollection environment;
    private HashMap<Integer, Boolean> keyMaps;
    public static Game game;
    private int level = 1;

    public static final int SIZE = GameGUI.gameGUI.getHeight() / 7;

    public Game() {
        game = this;
    }

    public void init() {
        keyMaps = new HashMap<>();
        keyMaps.put((int) 'a', false);
        keyMaps.put((int) 'd', false);
        keyMaps.put((int) ' ', false);
        keyMaps.put(-1, false);
        hero = new Hero();
        enemiesArrayList = new ArrayList<>();
        environment = new EnvironmentCollection(level);
//        unpause();
    }

    public void unpause() {
        pause = false;
    }

    public void pause() {
        pause = true;
    }


    public void setKeyStatus(int num, boolean status) {
        if ('w' == num || 'W' == num || 'ц' == num || 'Ц' == num) {
            keyMaps.put((int) 'w', status);
        }

        if ('a' == num || 'A' == num || 'Ф' == num || 'ф' == num) {
            keyMaps.put((int) 'a', status);
        }

        if ('s' == num || 'S' == num || 'ы' == num || 'Ы' == num) {
            keyMaps.put((int) 's', status);
        }

        if ('d' == num || 'D' == num || 'в' == num || 'В' == num) {
            keyMaps.put((int) 'd', status);
        }

        if (num == ' ') {
            keyMaps.put((int) ' ', status);
        }

        if (num == -1) {
            keyMaps.put(-1, status);
        }
    }

    public void updateAll() {
        hero.update();
        for (int i = 0; i < enemiesArrayList.size(); i++) {
            enemiesArrayList.get(i).update();
        }
        environment.update();

        if (enemiesArrayList.size() == 0) {
            level++;
            if (level == 3) {
//                pause();
                MainFrame.getMainFrame().launchMainMenu();
            } else {
//                pause();
//                hero = null;
//                enemiesArrayList.clear();
//                enemiesArrayList = null;
//                environment.getItemList().clear();
//                environment.getBackgroundEnv().clear();
//                environment = null;
                init();
            }
        }
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            while (!isPause() && !Thread.interrupted()) {
//                System.out.println(Thread.currentThread().getName());
                try {
                    long time = System.currentTimeMillis();
                    updateAll();
                    //todo: correct timeout
                    TimeUnit.MILLISECONDS.sleep(16 - (System.currentTimeMillis() - time));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public EnvironmentCollection getEnvironment() {
        return environment;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public HashMap<Integer, Boolean> getKeyMaps() {
        return keyMaps;
    }

    public void setKeyMaps(HashMap<Integer, Boolean> keyMaps) {
        this.keyMaps = keyMaps;
    }

    public ArrayList<Enemy> getEnemiesArrayList() {
        return enemiesArrayList;
    }

    public void setEnemiesArrayList(ArrayList<Enemy> enemiesArrayList) {
        this.enemiesArrayList = enemiesArrayList;
    }
}
