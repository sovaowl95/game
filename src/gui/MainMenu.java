package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainMenu extends JPanel {
    private MenuGrid menuGrid;
    private BufferedImage img;

    public void init(int width, int height) {
        removeAll();
        File file = new File("");
        try {
            img = ImageIO.read(new File(file.getAbsoluteFile() + "/resources/menu/back.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.VERTICAL;
        menuGrid = new MenuGrid();
        menuGrid.init();

        setBackground(Color.BLUE);
        setSize(width, height);

        //setLayout(new BorderLayout());
        add(menuGrid, c);
        menuGrid.setAlignmentX(CENTER_ALIGNMENT);
        menuGrid.setAlignmentY(CENTER_ALIGNMENT);

        revalidate();
        repaint();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
    }
}

class MenuGrid extends JPanel {
    ArrayList<JButton> list;

    public void init() {
        list = new ArrayList<>();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);

        setVisible(true);

        addButton("Play", 30);
        addButton("Options", 30);
        addButton("About", 30);
        addButton("Exit", 30);
        //revalidate();
        //repaint();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getText().equals("Play")) {
                list.get(i).addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        MainFrame.getMainFrame().launchGame();
                    }
                });
            } else if (list.get(i).getText().equals("Options")) {
                list.get(i).addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        MainFrame.getMainFrame().launchOptions();
                    }
                });
            } else if (list.get(i).getText().equals("About")) {
                list.get(i).addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        MainFrame.getMainFrame().launchAbout();
                    }
                });
            } else if (list.get(i).getText().equals("Exit")) {
                list.get(i).addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.exit(0);
                    }
                });
            }

        }
    }

    private void addButton(String text, int fontSize) {
        JButton jButton = new JButton(text);
        jButton.setAlignmentX(CENTER_ALIGNMENT);
        jButton.setAlignmentY(CENTER_ALIGNMENT);
        jButton.setFont(new Font("Arial", Font.BOLD, fontSize));
        add(jButton);
        add(Box.createRigidArea(new Dimension(0, 10)));
        list.add(jButton);
    }
}

