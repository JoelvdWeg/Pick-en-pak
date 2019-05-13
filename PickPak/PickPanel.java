/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PickPak;

import PickPak.*;
/**
 *
 * @author hylke
 */
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class PickPanel extends JPanel {

    PickPak pickpak;

    public PickPanel(PickPak pickpak) {
        setPreferredSize(new Dimension(1920, 1080));
        this.pickpak = pickpak;
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.WHITE);

        g.setColor(Color.BLACK);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(5));

//        grote vierkant
        g.drawRect(10, 10, 1900, 1000);
        g.drawLine(950, 400, 950, 1010);
//          lijn op de verticaal helft
        g.drawLine(10, 400, 1920, 400);
        g.drawRect(200, 450, 500, 500);

//        GRID 
        int x = 300;
        for (int i = 0; i < 4; i++) {

            g.drawLine(x, 450, x, 950);
            x = x + 100;
        }

        int y = 550;
        for (int i = 0; i < 4; i++) {

            g.drawLine(200, y, 700, y);
            y = y + 100;
        }

//            DOZEN 
        g2.setStroke(new BasicStroke(2));
//        g.drawRect(1100, 500, 50, 400);
//        g.drawRect(1250, 500, 50, 400);
//        g.drawRect(1350, 500, 50, 400);
//        g.drawRect(1450, 500, 50, 400);
//        g.drawRect(1550, 500, 50, 400);
//        g.drawRect(1650, 500, 50, 400);

//        g.fillRect(1100, 500, 50, 400);
//        g.fillRect(1200, 500, 50, 400);
//        g.fillRect(1300, 500, 50, 400);
//        g.fillRect(1400, 500, 50, 400);
//        g.fillRect(1500, 500, 50, 400);
//        g.fillRect(1600, 500, 50, 400);

        int x2 = 1030;
        for (int i = 0; i < 6; i++) {

            g.drawRect(x2, 500, 50, 400);
            x2 = x2 + 150;
        }
        g.setColor(Color.GRAY);
        int x3 = 1030;
        for (int i = 0; i < 6; i++) {

            g.fillRect(x3, 500, 50, 400);
            x3 = x3 + 150;
        }

        

//      Titels
        g.setColor(Color.BLACK);
        g.setFont(new Font("default", Font.BOLD, 50));
        

        g.drawString("0", 130, 920);
        g.drawString("1", 130, 820);
        g.drawString("2", 130, 720);
        g.drawString("3", 130, 620);
        g.drawString("4", 130, 520);

        g.drawString("0", 230, 1000);
        g.drawString("1", 330, 1000);
        g.drawString("2", 430, 1000);
        g.drawString("3", 530, 1000);
        g.drawString("4", 630, 1000);
        
        g.setFont(new Font("default", Font.BOLD, 30));
        g.drawString("DOZEN", 1350, 440);
        
        g.drawString("1", 1045, 480);
        g.drawString("2", 1195, 480);
        g.drawString("3", 1345, 480);
        g.drawString("4", 1495, 480);
        g.drawString("5", 1645, 480);
        g.drawString("6", 1795, 480);
        
        
        
        



        pickpak.tekenTSP(g);

    }
}