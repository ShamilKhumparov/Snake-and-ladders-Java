package games;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DiceGame {
    public JFrame dice = new JFrame("Roll a dice");

    int p1TotScore;
    int p2TotScore;
    int p1Rolls;
    int p2Rolls;
    public void diceGame() {
        JButton p1 = new JButton("Player 1");
        p1.setBounds(50,100,95,30);
        JButton p2 = new JButton("Player 2");
        p2.setBounds(250,100,95,30);

        JTextArea desc = new JTextArea("Each Player takes three alternate turns to roll a dice." +
                "The one who scores most after three turns wins the game!");
        desc.setEnabled(false);
        desc.setBounds(10,20,400,40);
        desc.setLineWrap(true);
        JTextField diceValue = new JTextField();
        diceValue.setEnabled(false);
        diceValue.setBounds(150,200,95,30);
        JTextField p1Score = new JTextField();
        p1Score.setEnabled(false);
        p1Score.setBounds(80,300,110,30);
        JTextField p2Score = new JTextField();
        p2Score.setEnabled(false);
        p2Score.setBounds(200,300,110,30);
        JTextField winner = new JTextField();
        winner.setEnabled(false);
        winner.setBounds(140,150,125,30);

        dice.add(p1);
        dice.add(p2);
        dice.add(diceValue);
        dice.add(p1Score);
        dice.add(p2Score);
        dice.add(desc);
        dice.add(winner);

        dice.setSize(450,400);
        dice.setLayout(null);
        dice.setVisible(true);

        //Set window mid of screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height;
        int width = screenSize.width;
        dice.setSize(width/3, height/2);
        dice.setLocationRelativeTo(null);
        winner.setVisible(false);

        dice.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Reset variables
        p1TotScore = 0;
        p1Rolls = 0;
        p2TotScore = 0;
        p2Rolls = 0;

        p1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Dice d = new Dice();
                int dice1 = d.roll();
                int dice2 = d.roll();
                p1TotScore += dice1;  //Adds this score to total score
                diceValue.setText(String.valueOf(dice1));
                p1Score.setText("Player 1 Score: " + p1TotScore);
                p1Rolls ++; //Plus one roll
                System.out.println(p1Rolls);

                p2.setEnabled(true);
                p1.setEnabled(false);

                //Check if game is finished and determines winner
                if(p2Rolls >=3 && p1Rolls >= 3) {
                    System.out.println("winner");
                    winner.setVisible(true);
                    p1.setEnabled(false);
                    p2.setEnabled(false);
                    if(p1TotScore > p2TotScore) {
                        winner.setText("Player 1 is the winner");
                    }
                    else {
                        winner.setText("Player 2 is the winner");
                    }
                }
            }
        });

        p2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Dice d = new Dice();
                int dice1 = d.roll();
                int dice2 = d.roll();
                p2TotScore += dice1;
                diceValue.setText(String.valueOf(dice1));
                p2Score.setText("Player 2 Score: " + p2TotScore);
                p2Rolls++;

                p2.setEnabled(false);
                p1.setEnabled(true);

                if(p2Rolls >=3 && p1Rolls >= 3) {
                    System.out.println("winner");
                    winner.setVisible(true);
                    p1.setEnabled(false);
                    p2.setEnabled(false);
                    if(p1TotScore > p2TotScore) {
                        winner.setText("Player 1 is the winner");
                    }
                    else {
                        winner.setText("Player 2 is the winner");
                    }
                }
            }
        });
    }
}
