package logic.world;

import logic.Game;
import util.MapExplaining;

import java.io.BufferedReader;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class EnviromentCollection {
    private List<Environment> allEnvironment;

    private int level;
    public static String[][] map;

    public EnviromentCollection(int level) {
        this.allEnvironment = new ArrayList<>();
        this.level = level;
        init();
    }

    private void init() {
        //todo: INITIALIZATION ENV BY LEVEL
        loadMap();
        showMap();
        setSpawnPosition();
        changeHeroPointToAir();
        initEnviroment();
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


    private void showMap() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                System.out.print(String.format("%2s ", map[i][j]));
            }
            System.out.println();
        }
    }

    private void changeHeroPointToAir() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j].equals("P")) {
                    map[i][j] = "O";
                    return;
                }
            }

        }
    }


    private void setSpawnPosition() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j].equals("P")) {
                    Game.game.getHero().setX(j * Game.SIZE + Game.SIZE / 2 - (Game.game.getHero().getWidth() / 2));
                    Game.game.getHero().setY(i * Game.SIZE);
//
//                    Game.game.getHero().setX(0);
//                    Game.game.getHero().setY(0);


//                    System.out.println("_--- ");
//                    System.out.println(Game.SIZE);
//                    System.out.println(j * Game.SIZE + Game.SIZE / 2 - (Game.game.getHero().getWidth() / 2));
//                    System.out.println(i * Game.SIZE);
                }
            }
        }
    }

    //
    public List<Environment> getAllEnvironment() {
        return allEnvironment;
    }

    public String[][] getMass() {
        return map;
    }

}
