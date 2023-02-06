package gui;

import javax.swing.*;

public class Window {
    JFrame jf;

    public void create() {
        jf = new JFrame();

        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setResizable(false);
        jf.setTitle("Adventure 2D Game Project");

        GamePanel gamePanel = new GamePanel();
        jf.add(gamePanel);

        jf.pack();    //// sizes this window to fit the size of its subcomponents (like the GamePanel)


        jf.setLocationRelativeTo(null);
        jf.setVisible(true);
        gamePanel.startGameThread();

    }
}
