package logic.world;

import logic.Game;
import logic.objects.GameObject;
import logic.objects.Item;
import logic.objects.active.Enemy;
import util.Constants;
import util.ItemsExplaining;
import util.MapExplaining;

import java.io.BufferedReader;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EnvironmentCollection {
    private List<GameObject> allEnvironment;

    private int level;
    public static String[][] map;

    public EnvironmentCollection(int level) {
        this.allEnvironment = new ArrayList<>();
        this.level = level;
        init();
    }

    private void init() {
        //todo: INITIALIZATION ENV BY LEVEL
        loadMap();
//        showMap();
        setSpawnPosition();
        spawnEnemies();
        changeHeroPointToAir();
        initEnviroment();
        spawnItems();
    }


    //todo: META-INF
    private void initEnviroment() {
        allEnvironment.add(new EnvironmentStatic("back_1", MapExplaining.getImageByExp("back_1")));
        allEnvironment.add(new EnvironmentActive("back_cloud_1", MapExplaining.getImageByExp("back_cloud_1")));
        allEnvironment.add(new EnvironmentActive("back_cloud_2", MapExplaining.getImageByExp("back_cloud_2")));
    }

    public void update() {
        for (int i = 0; i < allEnvironment.size(); i++) {
            allEnvironment.get(i).update();
        }
    }

    private void loadMap() {
        try {
            File file = new File("");
            BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(file.getAbsolutePath() + "/resources/worldMap/level_" + level));
            String inputLine;
            int x = Integer.parseInt(bufferedReader.readLine());
            int y = Integer.parseInt(bufferedReader.readLine());
            map = new String[y][x];
            x = 0;
            while ((inputLine = bufferedReader.readLine()) != null) {
                String[] s = inputLine.split(" ");
                for (int i = 0; i < s.length; i++) {
                    map[x][i] = s[i];
                }
                x++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//    private void showMap() {
//        for (int i = 0; i < map.length; i++) {
//            for (int j = 0; j < map[i].length; j++) {
//                System.out.print(String.format("%2s ", map[i][j]));
//            }
//            System.out.println();
//        }
//    }

    private void changeHeroPointToAir() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j].equals(Constants.HERO)) {
                    map[i][j] = Constants.AIR;
                    return;
                }
            }

        }
    }


    private void setSpawnPosition() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j].equals(Constants.HERO)) {
                    Game.game.getHero().setX(j * Game.SIZE + Game.SIZE / 2 - (Game.game.getHero().getCharacterWidth() / 2));
                    Game.game.getHero().setY(i * Game.SIZE);
                }
            }
        }
    }

    private void spawnEnemies() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (MapExplaining.isEnemy(map[i][j])) {
                    Enemy enemy = new Enemy();
                    Game.game.getEnemiesArrayList().add(enemy);
                    enemy.setX(j * Game.SIZE + Game.SIZE / 2 - (enemy.getCharacterWidth() / 2));
                    enemy.setY(i * Game.SIZE);
                    map[i][j] = Constants.AIR;
                }
            }
        }
    }

    private void spawnItems() {
        Item item = new Item("healingPotion_01", ItemsExplaining.getImageByExp("healingPotion_01"));
        item.setX(Game.game.getHero().getX() + 100);
        item.setY(Game.game.getHero().getY());
        allEnvironment.add(item);

    }

    //
    public List<GameObject> getAllEnvironment() {
        return allEnvironment;
    }

    public String[][] getEnviroment() {
        return map;
    }

}
