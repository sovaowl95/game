package gui;

import logic.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MainFrame extends JFrame{
    private int width;
    private int height;
    private boolean fullscreen;

    private static final String TITLE = "MY GAME_GUI 1.0";
    private static final MainMenu MAIN_MENU = new MainMenu();
    private static final GameGUI GAME_GUI = new GameGUI();
    private static MainFrame MAIN_FRAME;

    public MainFrame() {
        this(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height, true);
    }

    public MainFrame(int width, int height, boolean fullscreen) {
        this.width = width;
        this.height = height;
        this.fullscreen = fullscreen;
    }

    public void init() {
        MAIN_FRAME = this;
        setSize(width, height);
        if (fullscreen) {
            setUndecorated(true);
        }
        setTitle(TITLE);
        setResizable(true);
//        GAME_GUI.init(width, height);
        MAIN_MENU.init(width, height);
        add(MAIN_MENU);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                Game.game.setKeyStatus(e.getKeyCode(), true);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                Game.game.setKeyStatus(e.getKeyCode(), false);
            }
        });

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                Game.game.setKeyStatus(-1, true);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                Game.game.setKeyStatus(-1, true);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                Game.game.setKeyStatus(-1, false);
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

    }

    public static MainFrame getMainFrame() {
        return MAIN_FRAME;
    }

    public void launchGame() {
        remove(MAIN_MENU);
        GAME_GUI.init(width, height);
        add(GAME_GUI);
        revalidate();
        repaint();
        GAME_GUI.unpause();
    }

    public void launchMainMenu() {
        remove(GAME_GUI);
        MAIN_MENU.init(width, height);
        add(MAIN_MENU);
        revalidate();
        repaint();
        GAME_GUI.pause();
    }

    public void launchOptions() {
        //todo:
    }

    public void launchAbout() {
        //todo:
    }


}
