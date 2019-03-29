package logic.world;

import logic.Game;
import logic.objects.GameObject;
import logic.objects.Item;
import logic.objects.active.Enemy;
import util.*;

import java.io.BufferedReader;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class EnvironmentCollection {
    private List<GameObject> backgroundEnv;
    private List<Item> itemList;

    private int level;
    public static String[][] map;

    public EnvironmentCollection(int level) {
        this.backgroundEnv = new ArrayList<>();
        this.itemList = new ArrayList<>();
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
        backgroundEnv.add(new EnvironmentStatic("back_1", MapExplaining.getImageByExp("back_1")));
        backgroundEnv.add(new EnvironmentActive("back_cloud_1", MapExplaining.getImageByExp("back_cloud_1")));
        backgroundEnv.add(new EnvironmentActive("back_cloud_2", MapExplaining.getImageByExp("back_cloud_2")));
    }

    public void update() {
        for (int i = 0; i < backgroundEnv.size(); i++) {
            backgroundEnv.get(i).update();
        }
        for (int i = 0; i < itemList.size(); i++) {
            itemList.get(i).update();
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
        List<String> strings = ItemLoader.loadItems(level);
        for (int i = 0; i < strings.size(); i++) {
            String itemName = strings.get(i).split(" ")[0];
            int x = Game.SIZE * Integer.parseInt(strings.get(i).split(" ")[1]);
            int y = Game.SIZE * Integer.parseInt(strings.get(i).split(" ")[2]);
            Item item = new Item("heal", ImageLoader.loadImage(itemName));
            item.setX(x);
            item.setY(y);
            itemList.add(item);
        }
    }

    public List<GameObject> getBackgroundEnv() {
        return backgroundEnv;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public String[][] getEnviroment() {
        return map;
    }

}
