package games;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class SnakeAndLadders {
    final static int BLOCKS = 37;

    //Create JFrame
    public JFrame mainFrame = new JFrame("SnakeAndLadders");

    //Labels
    JLabel[] Board = new JLabel[BLOCKS];

    JLabel player1 = new JLabel();
    JLabel player2 = new JLabel();
    JLabel info = new JLabel();
    JLabel info2 = new JLabel();
    JLabel equalsDiceText = new JLabel("Two like dice, roll again");

    //Text Fields
    JTextField diceValueText = new JTextField();

    //Board variables
    public int player1pos, player2pos;
    final static int [] bottomOfLadder = new int[]{6,12,15};
    final static int [] topOfLadder = new int[]{18, 19, 27};
    final static int [] bottomOfSnake = new int[]{3, 16, 20, 28};
    final static int [] topOfSnake = new int[]{11, 23, 25, 35};
    final Rectangle[] rec = {new Rectangle()};
    double[] x = new double[1];
    double[] y = new double[1];
    final int [] xpos = new int[1];
    final int [] ypos = new int[1];

    //Buttons
    JButton player1Btn = new JButton("Player 1");
    JButton player2Btn = new JButton("Player 2");
    JButton resetBtn = new JButton("RESET");
    JButton resignBtn = new JButton("RESIGN");
    JButton resignPlayer1Btn = new JButton("Player 1");
    JButton resignPlayer2Btn = new JButton("Player 2");
    JButton exitBtn = new JButton("EXIT");

    //Main function in Snake And Ladders game
    public void mainFunc() {
        //MenuBar
        menuBar();
        //Game Board
        playerStart();
        board();

        //Font
        Font font = new Font("Verdana", Font.BOLD, 12);

        //If two like dices
        equalsDiceText.setFont(font);
        equalsDiceText.setBackground(Color.RED);
        equalsDiceText.setVisible(false);

        //Buttons bounds
        player1Btn.setBounds(750,200,90,30);
        player2Btn.setBounds(950,200,90,30);
        exitBtn.setBounds(870, 670, 100, 30);
        resetBtn.setBounds(870, 590, 100, 30);
        resignBtn.setBounds(870, 630, 100, 30);
        resignPlayer1Btn.setBounds(760, 610, 100, 30);
        resignPlayer2Btn.setBounds(760, 650, 100, 30 );

        //Text field bounds
        info.setBounds(750, 270, 300, 40);
        info2.setBounds(750, 310, 300, 40 );
        diceValueText.setBounds(850, 160, 90, 30);
        equalsDiceText.setBounds(750, 230, 180, 40);

        //Resign buttons
        resignPlayer1Btn.setEnabled(false);
        resignPlayer1Btn.setVisible(false);
        resignPlayer2Btn.setEnabled(false);
        resignPlayer2Btn.setVisible(false);

        //Info
        diceValueText.setEnabled(false);
        diceValueText.setBackground(Color.RED);
        info.setVisible(false);
        info.setFont(font);
        info2.setVisible(false);
        info2.setFont(font);

        //Exit Button
        exitBtn.addActionListener((event)->mainFrame.dispose());

        //Add buttons
        mainFrame.add(exitBtn);
        mainFrame.add(resetBtn);
        mainFrame.add(resignBtn);
        mainFrame.add(player1Btn);
        mainFrame.add(player2Btn);
        mainFrame.add(resignPlayer1Btn);
        mainFrame.add(resignPlayer2Btn);

        //Add Text fields
        mainFrame.add(info);
        mainFrame.add(info2);
        mainFrame.add(diceValueText);
        mainFrame.add(equalsDiceText);

        //JFrame size
        mainFrame.setSize(1100, 780);
        mainFrame.setVisible(true);

        //Set window mid of screen
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        //ActionListener
        player1Btn.addActionListener(e -> {
            int dice1 = Dice.roll();
            int dice2 = Dice.roll();
            int TotScore = (dice1 + dice2);  //Total score
            diceValueText.setText(String.valueOf(TotScore));

            if(dice1 == dice2){
                player1Btn.setEnabled(true);
                player2Btn.setEnabled(false);
                equalsDiceText.setVisible(true);
            }
            else{
                player1Btn.setEnabled(false);
                player2Btn.setEnabled(true);
                equalsDiceText.setVisible(false);
            }
            movePlayer1(TotScore);
        });

        player2Btn.addActionListener(e -> {
            int dice1 = Dice.roll();
            int dice2 = Dice.roll();
            int TotScore = (dice1 + dice2);  //Total score
            diceValueText.setText(String.valueOf(TotScore));
            if(dice1 == dice2){
                player2Btn.setEnabled(true);
                player1Btn.setEnabled(false);
                equalsDiceText.setVisible(true);
            }
            else{
                player2Btn.setEnabled(false);
                player1Btn.setEnabled(true);
                equalsDiceText.setVisible(false);
            }
            movePlayer2(TotScore);
        });

        resetBtn.addActionListener(e -> {
            resetGame();
        });

        resignBtn.addActionListener(e -> {
            resignGame();
        });

    }
    //Function to move player 1
    public void movePlayer1(int totScore) {
        //Run if its more blocks left
        if (player1pos + totScore < BLOCKS) {
            player1pos = player1pos + totScore;
            rec[0] = Board[player1pos].getBounds();
            x[0] = rec[0].getX();
            y[0] = rec[0].getY();
            xpos[0] = (int) Math.round(x[0]);
            ypos[0] = (int) Math.round(y[0]);
            player1.setBounds((xpos[0] + 10), (ypos[0] + 20), 40, 40);
        }

        //If dice score more than finish, go back on board
        else if(player1pos + totScore > 36){
            info.setText("Player1 is over finish block! Moving back");
            info.setVisible(true);
            int overGoal = player1pos + totScore;
            player1pos = 36;
            int dif = overGoal - 36;
            player1pos -= dif;
            rec[0] = Board[player1pos].getBounds();
            x[0] = rec[0].getX();
            y[0] = rec[0].getY();
            xpos[0] = (int)Math.round(x[0]);
            ypos[0] = (int)Math.round(y[0]);
            player1.setBounds((xpos[0]+10),(ypos[0]+20), 40, 40);
        }

        //For loop ladder movement
        for (int i = 0; i < 3; i++) {
            //If Player landed on bottom of Ladder
            if (player1pos == bottomOfLadder[i]) {
                int i2 = i;
                player1Btn.setEnabled(false);
                player2Btn.setEnabled(false);
                info.setText("Player1 landed on the ladder");
                info2.setText("press the bottom ladder to move on");
                info.setVisible(true);
                info2.setVisible(true);
                Board[player1pos].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        info.setVisible(false);
                        info2.setVisible(false);
                        rec[0] = Board[topOfLadder[i2]].getBounds();
                        x[0] = rec[0].getX();
                        y[0] = rec[0].getY();
                        xpos[0] = (int) Math.round(x[0]);
                        ypos[0] = (int) Math.round(y[0]);
                        player1.setBounds((xpos[0] + 10), (ypos[0] + 20), 40, 40);
                        player1pos = topOfLadder[i2];
                        player2Btn.setEnabled(true);
                        ((Component) e.getSource()).removeMouseListener(this);
                    }
                });
            }
        }
        //For loop snake movement
        for (int i = 0; i < 4; i++) {
            //If Player landed on snake head
            if (player1pos == topOfSnake[i]) {
                int i2 = i;
                player1Btn.setEnabled(false);
                player2Btn.setEnabled(false);
                info.setVisible(true);
                info.setText("Player1 landed on the snake");
                info2.setText("press the snake head to move on");
                info2.setVisible(true);
                Board[player1pos].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        info.setVisible(false);
                        info2.setVisible(false);
                        rec[0] = Board[bottomOfSnake[i2]].getBounds();
                        x[0] = rec[0].getX();
                        y[0] = rec[0].getY();
                        xpos[0] = (int) Math.round(x[0]);
                        ypos[0] = (int) Math.round(y[0]);
                        player1.setBounds((xpos[0] + 10), (ypos[0] + 10), 40, 40);
                        player1pos = bottomOfSnake[i2];
                        player2Btn.setEnabled(true);
                        ((Component) e.getSource()).removeMouseListener(this);
                    }
                });
            }
        }

        //Player 1 landed on finish and win the game
        if (player1pos == 36){
            player1Btn.setEnabled(false);
            player2Btn.setEnabled(false);
            resignBtn.setEnabled(false);
            info.setText("PLAYER1 WOM THE GAME");
            info.setVisible(true);
        }
    }

    //Function to move player 2
    public void movePlayer2(int totScore){
        //Run if its more blocks left
        if(player2pos + totScore < BLOCKS){
            player2pos = player2pos + totScore;
            rec[0] = Board[player2pos].getBounds();
            x[0] = rec[0].getX();
            y[0] = rec[0].getY();
            xpos[0] = (int)Math.round(x[0]);
            ypos[0] = (int)Math.round(y[0]);
            player2.setBounds((xpos[0]+40),(ypos[0]+60), 40, 40);
        }

        //If dice score more than finish, go back on board
        else if(player2pos + totScore > 36){
            info.setText("Player2 is over finish block! Moving back");
            info.setVisible(true);
            int overGoal = player2pos + totScore;
            player2pos = 36;
            int dif = overGoal - 36;
            player2pos -= dif;
            rec[0] = Board[player2pos].getBounds();
            x[0] = rec[0].getX();
            y[0] = rec[0].getY();
            xpos[0] = (int)Math.round(x[0]);
            ypos[0] = (int)Math.round(y[0]);
            player2.setBounds((xpos[0]+10),(ypos[0]+20), 40, 40);
        }
        //For loop ladder movement
        for(int i = 0; i<3; i++){
            //If Player landed bottom of Ladder
            if(player2pos == bottomOfLadder[i]) {
                int i2 = i;
                player1Btn.setEnabled(false);
                player2Btn.setEnabled(false);
                info.setText("Player2 landed on the ladder");
                info2.setText("press the ladder to move on");
                info.setVisible(true);
                info2.setVisible(true);
                Board[player2pos].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        info.setVisible(false);
                        info2.setVisible(false);
                        rec[0] = Board[topOfLadder[i2]].getBounds();
                        x[0] = rec[0].getX();
                        y[0] = rec[0].getY();
                        xpos[0] = (int)Math.round(x[0]);
                        ypos[0] = (int)Math.round(y[0]);
                        player2.setBounds((xpos[0]+40), (ypos[0]+60), 40, 40);
                        player2pos = topOfLadder[i2];
                        player1Btn.setEnabled(true);
                        ((Component) e.getSource()).removeMouseListener(this);
                    }
                });

            }
        }
        //For loop snake movement
        for(int i = 0; i<4;i++ ){
            //If Player landed on snake head
            if(player2pos == topOfSnake[i]){
                int i2 = i;
                player1Btn.setEnabled(false);
                player2Btn.setEnabled(false);
                info.setText("Player2 landed on the snake");
                info.setVisible(true);
                info2.setText("press the snake head to move on");
                info2.setVisible(true);
                Board[player2pos].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        info.setVisible(false);
                        info2.setVisible(false);
                        rec[0] = Board[bottomOfSnake[i2]].getBounds();
                        x[0] = rec[0].getX();
                        y[0] = rec[0].getY();
                        xpos[0] = (int)Math.round(x[0]);
                        ypos[0] = (int)Math.round(y[0]);
                        player2.setBounds((xpos[0]+40), (ypos[0]+60), 40, 40);
                        player2pos = bottomOfSnake[i2];
                        player1Btn.setEnabled(true);
                        ((Component) e.getSource()).removeMouseListener(this);
                    }
                });
            }
        }

        //Player 2 landed on finish and win the game
        if (player2pos == 36){
            player1Btn.setEnabled(false);
            player2Btn.setEnabled(false);
            resignBtn.setEnabled(false);
            info.setText("PLAYER2 WON THE GAME");
            info.setVisible(true);
        }
    }

    //Start players at start position
    public void playerStart(){
        setPlayerToStart();
        player1.setIcon(new ImageIcon(ClassLoader.getSystemResource("green.jpg")));
        player2.setIcon(new ImageIcon(ClassLoader.getSystemResource("yellow.jpg")));
        mainFrame.add(player1);
        mainFrame.add(player2);
    }

    //Create game board with images
    public void board(){
        //this.SnakeAndLadders = Snake;
        int j = 0;
        for(int i=31;i<=36;i++) {
            Board[i] = new JLabel();
            Board[i].setBounds((j*117),0,120,120);
            Board[i].setIcon(new ImageIcon(ClassLoader.getSystemResource(i+".png")));
            Board[i].setVisible(true);
            mainFrame.add(Board[i]);
            j++;
        }

        j = 0;
        for(int i=25;i<=30;i++) {
            Board[i] = new JLabel();
            Board[i].setBounds((j*117),117,120,120);
            Board[i].setIcon(new ImageIcon(ClassLoader.getSystemResource(i+".png")));
            Board[i].setVisible(true);
            mainFrame.add(Board[i]);
            j++;
        }

        j = 0;
        for(int i=19;i<=24;i++) {
            Board[i] = new JLabel();
            Board[i].setBounds((j*117),234,120,120);
            Board[i].setIcon(new ImageIcon(ClassLoader.getSystemResource(i+".png")));
            Board[i].setVisible(true);
            mainFrame.add(Board[i]);
            j++;
        }

        j = 0;
        for(int i=13;i<=18;i++) {
            Board[i] = new JLabel();
            Board[i].setBounds((j*117),351,120,120);
            Board[i].setIcon(new ImageIcon(ClassLoader.getSystemResource(i+".png")));
            Board[i].setVisible(true);
            mainFrame.add(Board[i]);
            j++;
        }

        j = 0;
        for(int i=12;i>=7;i--) {
            Board[i] = new JLabel();
            Board[i].setBounds((j*117),468, 120, 120);
            Board[i].setIcon(new ImageIcon(ClassLoader.getSystemResource(i+".png")));
            Board[i].setVisible(true);
            mainFrame.add(Board[i]);
            j++;
        }

        j = 0;
        for(int i=1;i<=6;i++) {
            Board[i] = new JLabel();
            Board[i].setBounds((j*117),585, 120, 120);
            Board[i].setIcon(new ImageIcon(ClassLoader.getSystemResource(i+".png")));
            Board[i].setVisible(true);
            mainFrame.add(Board[i]);
            j++;
        }

    }

    //Reset player bricks on start position
    public void setPlayerToStart(){
        player1.setBounds(15,640,40,40);
        player2.setBounds(50,640,40,40);
    }

    //Function to reset the game
    public void resetGame(){
        setPlayerToStart();
        info.setVisible(false);
        diceValueText.setText(String.valueOf(0));
        equalsDiceText.setVisible(false);
        resignBtn.setEnabled(true);
        player1Btn.setEnabled(true);
        player2Btn.setEnabled(true);
        resignPlayer1Btn.setVisible(false);
        resignPlayer1Btn.setEnabled(false);
        resignPlayer2Btn.setVisible(false);
        resignPlayer2Btn.setEnabled(false);
        player1pos = 0; player2pos = 0;
    }

    //Function if a player resign a game
    public void resignGame(){
        diceValueText.setText(String.valueOf(0));
        player1Btn.setEnabled(false);
        player1Btn.setEnabled(false);
        equalsDiceText.setVisible(false);
        resignPlayer1Btn.setVisible(true);
        resignPlayer1Btn.setEnabled(true);
        resignPlayer2Btn.setVisible(true);
        resignPlayer2Btn.setEnabled(true);

        resignPlayer1Btn.addActionListener(e -> {
            info.setText("PLAYER1 WOM THE GAME");
            info.setVisible(true);
            player1Btn.setEnabled(false);
            player2Btn.setEnabled(false);
            resignBtn.setEnabled(false);
            resignPlayer1Btn.setEnabled(false);
            resignPlayer2Btn.setEnabled(false);
        });

        resignPlayer2Btn.addActionListener(e -> {
            info.setText("PLAYER1 WON THE GAME");
            info.setVisible(true);
            player1Btn.setEnabled(false);
            player2Btn.setEnabled(false);
            resignBtn.setEnabled(false);
            resignPlayer1Btn.setEnabled(false);
            resignPlayer2Btn.setEnabled(false);
        });

    }

    public void menuBar(){
        // Create a new menu bar
        JMenuBar menuBar = new JMenuBar();
        mainFrame.setJMenuBar(menuBar);

        //Create menus
        JMenu file = new JMenu("File");
        file.addSeparator();
        JMenu help = new JMenu("Help");
        JMenu about = new JMenu("About");

        //Add menuBar items
        menuBar.add(file);
        menuBar.add(help);
        menuBar.add(about);

        //Creates MenuItems under File
        JMenuItem newGame = new JMenuItem("New game");
        JMenuItem reset = new JMenuItem("Reset");
        JMenuItem resign = new JMenuItem("Resign");
        JMenuItem exit = new JMenuItem("Exit");

        JMenuItem resignPlayer1 = new JMenuItem("Player1");
        JMenuItem resignPlayer2 = new JMenuItem("Player2");
        //resign.add(resignPlayer1);
        //resign.add(resignPlayer2);
        //Add items to file menu
        file.add(newGame);
        file.addSeparator();
        file.add(reset);
        file.addSeparator();
        file.add(resign);
        file.addSeparator();
        file.add(exit);

        //ACTION LISTENER
        //reset game
        reset.addActionListener(e -> {
            resetGame();
        });

        resign.addActionListener(e -> {

        });

        help.addActionListener(e -> {
            readDoc();
        });

        //Set KeyStroke
        newGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));
        reset.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK));
        resign.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_MASK));

        //exit part
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
        exit.addActionListener((event)->mainFrame.dispose());
    }

    public void readDoc(){
        if(Desktop.isDesktopSupported()){
            try{
                File rules = new File("src\\SnakeAndLadders\\rules.txt");
                Desktop.getDesktop().open(rules);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}