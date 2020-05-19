package com.shakhmin;

import javafx.scene.input.KeyCode;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.sql.Time;

public class Main {
    // 370 - 650
    static boolean frontSensors(BufferedImage screen, int blank, int mainY, int jumps) {
        int speedGain = 0;/*
        if(jumps < 100)
            speedGain = (int)(jumps * 0.002);
        else if (jumps < 200)
            speedGain = (int)(jumps * 0.047);
        else if (jumps < 250)
            speedGain = (int)(jumps * 0.065);
        else if (jumps < 300)
            speedGain = (int)(jumps * 0.1);
        else if (jumps < 350)
            speedGain = (int)(jumps * 0.13);
        else if (jumps < 400)
            speedGain = (int)(jumps * 0.17);
        else if (jumps < 500)
            speedGain = (int)(jumps * 0.2);
        else if (jumps < 550)
            speedGain = (int)(jumps * 0.22);
        else if (jumps < 600)
            speedGain = (int)(jumps * 0.24);
        else if (jumps < 650)
            speedGain = (int)(jumps * 0.27);
        else if (jumps < 700)
            speedGain = (int)(jumps * 0.3);
        else if (jumps < 750)
            speedGain = (int)(jumps * 0.31);
        else if (jumps < 800)
            speedGain = (int)(jumps * 0.33);
        else if (jumps < 900)
            speedGain = (int)(jumps * 0.35);
        else if (jumps < 1000)
            speedGain = (int)(jumps * 0.37);
        else*/
            speedGain = (int)(jumps * (0.01 + jumps/4000));

        for (int i = 0; i < 80 + speedGain; ++i) {
            if (screen.getRGB((int)(147 + i + speedGain * 0.5), mainY) != blank){
                return true;
            }
        }
        return false;
    }

    static boolean gameOver(BufferedImage screen, int sensorBlank){
        if ((screen.getRGB(455, 360) != sensorBlank)
                && (screen.getRGB(477, 355) != sensorBlank)
                && (screen.getRGB(477, 360) != sensorBlank))
            return true;
        return false;
    }

    public static void main(String[] args) {
        int jumps = 0;
        int mainY = 417;
        int sensor1, sensorBlank, sensorUnder, sensorDino;
        boolean inAir = false;
        Robot rb = null;
        try {
            rb = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        Rectangle rectangle = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        BufferedImage screen;


        int mousepos = MouseInfo.getPointerInfo().getLocation().x;

        while (mousepos == MouseInfo.getPointerInfo().getLocation().x) {

            screen = rb.createScreenCapture(rectangle);
            sensorBlank = screen.getRGB(100, 100);
            if (gameOver(screen, sensorBlank)){
                System.out.println("GMAE XVER");
                jumps = 0;
                rb.keyPress(10);
                rb.keyRelease(10);
            }
            if (frontSensors(screen, sensorBlank, mainY, jumps)) { //jump
                System.out.println(jumps);
                rb.keyRelease(40); // down
                rb.keyPress(38); // up
                jumps++;
            } else {
                rb.keyRelease(38);
                rb.keyPress(40); // down
            }
        }
    }
}
