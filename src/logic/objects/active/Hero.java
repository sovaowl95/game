package logic.objects.active;

import logic.objects.Item;
import util.Animation;
import logic.Game;
import util.Constants;
import util.SoundsUtil;

import java.awt.*;
import java.util.HashMap;
import java.util.List;

public class Hero extends CharacterImpl {
    private long lastTimeSoundPlayed;
    private long timeSpaceSoundEff = 500;

    public Hero() {
        super();
        SPEED = 5;
        dmg = 26;
        currentHp = 10;
        maxHp = 100;
        animation = new Animation("hero2%s/%d.png", 10);
        animation.init();
    }

    @Override
    public void move() {
        List<Item> itemList = Game.game.getEnvironment().getItemList();
        for (int i = 0; i < itemList.size(); i++) {
            Item item = itemList.get(i);
            Rectangle rectangle = new Rectangle(
                    (int) item.getX(),
                    (int) item.getY(),
                    Constants.ITEM_SIZE,
                    Constants.ITEM_SIZE);
            if (rectangle.intersects(
                    getX(),
                    getY(),
                    getCharacterWidth(),
                    getCharacterHeight()
            )) {
                //instanceof Potion (class extended Item)
                itemList.remove(item);
                takePotion();
            }
        }
    }

    @Override
    public void attack1() {
        boolean hit = false;
        if (lastAttTime + timeoutBeforeNextAtt <= System.currentTimeMillis()) {
            lastAttTime = System.currentTimeMillis();
            for (int i = 0; i < Game.game.getEnemiesArrayList().size(); i++) {
                Enemy enemy = Game.game.getEnemiesArrayList().get(i);
                Rectangle hitbox = new Rectangle(
                        (int) enemy.getX(),
                        (int) enemy.getY(),
                        enemy.getCharacterWidth(),
                        enemy.getCharacterHeight());

                int x = (int) getX();
                int y = (int) getY();

                if (!direction) {
                    x -= getCharacterWidth();
                } else {
                    x += getCharacterWidth();
                }

                if (hitbox.intersects(x, y, getCharacterWidth(), getCharacterHeight())) {
                    hit = true;
                    enemy.takeDmg(dmg);
                    if (!enemy.isAlive()) {
                        Game.game.getEnemiesArrayList().remove(enemy);
                    }
                }
            }

            String soundName = "SwordSwoosh.wav";
            if (hit) {
                soundName = "SwordAttack.wav";
            }
            SoundsUtil soundsUtil = new SoundsUtil(soundName);
            soundsUtil.start();
        }


    }

    @Override
    public void attack2() {

    }

    @Override
    public void update() {
        HashMap<Integer, Boolean> keyMaps = Game.game.getKeyMaps();
        if (keyMaps.get((int) 'a')) {
            if (canGoLeft()) {
                x = x - SPEED;
                direction = false;
                lastMoveTime = System.currentTimeMillis();
                playFootstepSound();
            }
        }

        if (keyMaps.get((int) 'd')) {
            if (canGoRight()) {
                x = x + SPEED;
                direction = true;
                lastMoveTime = System.currentTimeMillis();
                playFootstepSound();
            }
        }

        if (keyMaps.get((int) ' ') && canJump) {
            jump();
            inJump = true;
            canJump = false;
        }

        if (keyMaps.get(-1)) {
            attack1();
        }

//        if (keyMaps.get((int) 'w')) {
//            y = y - SPEED;
//            //todo: gravitation?
//        }
//
//        if (keyMaps.get((int) 's')) {
//            y = y + SPEED;
//            //todo: gravitation?
//        }

        if (inJump) {
            jump();
        } else {
            gravity();
        }

        move();
    }

    private void playFootstepSound() {
        if (System.currentTimeMillis() - lastTimeSoundPlayed >= timeSpaceSoundEff) {
            lastTimeSoundPlayed = System.currentTimeMillis();
            SoundsUtil soundsUtil = new SoundsUtil("footstep.wav");
            soundsUtil.start();
        }
    }
}
