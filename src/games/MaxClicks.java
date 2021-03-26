package games;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;


public class MaxClicks {
    public JFrame clicks = new JFrame("Maximum Clicks");

    int playerRecord;
    int clickCounter;
    int sec = 60;
    public void maxClicks() {

        clicks.setSize(450,400);
        clicks.setLayout(null);
        clicks.setVisible(true);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height;
        int width = screenSize.width;
        clicks.setSize(width/3, height/2);
        clicks.setLocationRelativeTo(null);

        playerRecord = 0;
        clickCounter = 0;
        getRecord();    //Gets previous record from file

        JButton start = new JButton("Start");
        start.setBounds(100,100,100,30);
        JButton click = new JButton("Click Me!");
        click.setBounds(100, 150, 200,30);

        JTextArea desc = new JTextArea("See how many clicks you can do in a minute!");
        desc.setEnabled(false);
        desc.setBounds(10,20,400,30);
        JTextField record = new JTextField("Record: " + playerRecord);
        record.setEnabled(false);
        record.setBounds(10,45,100,30);
        JTextField counter = new JTextField();
        counter.setEnabled(false);
        counter.setBounds(200,100,100,30);
        JTextField time = new JTextField();
        time.setEnabled(false);
        time.setBounds(150,200,100,30);


        clicks.add(start);
        clicks.add(click);
        clicks.add(desc);
        clicks.add(record);
        clicks.add(counter);
        clicks.add(time);

        clicks.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        click.setEnabled(false);

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickCounter = 0; //Reset click counter and timer
                sec = 60;
                click.setEnabled(true);
                start.setEnabled(false);
                Timer t = new Timer();

                //Timer that updates every second
                t.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        time.setText(String.valueOf(sec--));

                        if(sec == -1) { //Timer finished - stop timer and disable click button
                            t.cancel();
                            start.setEnabled(true);
                            click.setEnabled(false);
                        }

                        System.out.println(sec);
                    }
                }, 0, 1000);


            }
        });

        //Adds one to the counter every time user clicks the button
        click.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickCounter++;
                counter.setText(String.valueOf(clickCounter));
                if(clickCounter > playerRecord) { //Check if current score is higher than record
                    playerRecord = clickCounter; //Updates record and writes to file
                    record.setText("Record: " + playerRecord);
                    WriteToFile();
                }
            }
        });

    }

    public void WriteToFile() {
        try {
            File file = new File("record.txt");
            if (file.createNewFile()) {
                System.out.println("File Created: " + file.getName());
            } else {
                System.out.println("File already exists");
            }
            try {
                FileWriter writer = new FileWriter("record.txt");
                writer.write(String.valueOf(playerRecord));
                writer.close();
                System.out.println("Successfully wrote to file");
            }catch (IOException e) {
                System.out.println("An error occurred while updating the file ");
            }

        } catch (IOException e) {
            System.out.println("An error occurred while creating the file");
        }
    }

    public void getRecord () {
        try {
            File file = new File("record.txt");
            Scanner reader = new Scanner(file);
            while(reader.hasNextLine()) {
                String data = reader.nextLine();
                System.out.println(data);
                playerRecord = Integer.parseInt(data);
                System.out.println(playerRecord);
            }

        }catch (FileNotFoundException e) {
            System.out.println("An error occurred getting data from file" +
                    " it probably doesn't exist");
        }
    }
}
