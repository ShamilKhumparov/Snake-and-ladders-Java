package games;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        //Create frame
        JFrame mainWindow = new JFrame("Game Center");
        DiceGame dice = new DiceGame();
        MaxClicks clicks = new MaxClicks();
        SnakeAndLadders SAL = new SnakeAndLadders();

        //Buttons
        JButton btn1 = new JButton("Roll a Dice");
        btn1.setBounds(175, 50, 100, 30);
        JButton btn2 = new JButton("Max Clicks");
        btn2.setBounds(175, 100, 100, 30);
        JButton btn3 = new JButton("Snake and Ladders");
        btn3.setBounds(150, 150, 150, 30);
        JButton exit = new JButton("Exit");
        exit.setBounds(175, 250, 100, 30);

        //Add
        mainWindow.add(btn1);
        mainWindow.add(btn2);
        mainWindow.add(btn3);
        mainWindow.add(exit);

        mainWindow.setSize(450, 400);
        mainWindow.setLayout(null);
        mainWindow.setVisible(true);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height;
        int width = screenSize.width;
        mainWindow.setSize(width / 3, height / 2);
        mainWindow.setLocationRelativeTo(null);

        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Starts the "Roll a dice" game
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dice.diceGame();
            }
        });

        //Starts the "Maximum Clicks" game
        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clicks.maxClicks();
            }
        });

        //Starts the "Snake and Ladders" game
        btn3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SAL.mainFunc();
            }
        });
        //Closes all windows on exit
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainWindow.dispose();
                dice.dice.dispose();
                clicks.clicks.dispose();
                SAL.mainFrame.dispose();
            }
        });
    }
}

