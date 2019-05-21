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
import java.awt.Scrollbar;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

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
        g.drawRect(10, 10, 1900, 990);
        g.drawLine(950, 10, 950, 1000);
//          lijn op de horizontale helft
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
        g.drawRect(1100, 500, 50, 400);
        g.drawRect(1200, 500, 50, 400);
        g.drawRect(1300, 500, 50, 400);
        g.drawRect(1400, 500, 50, 400);
        g.drawRect(1500, 500, 50, 400);
        g.drawRect(1600, 500, 50, 400);

        g.setColor(Color.GRAY);
        g.fillRect(1100, 500, 50, 400);
        g.fillRect(1200, 500, 50, 400);
        g.fillRect(1300, 500, 50, 400);
        g.fillRect(1400, 500, 50, 400);
        g.fillRect(1500, 500, 50, 400);
        g.fillRect(1600, 500, 50, 400);

        g.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(4));
        g.drawRect(1100, 75, 700, 300);

        int x1 = 1250;
        for (int i = 0; i < 4; i++) {

            g.drawLine(x1, 75, x1, 375);
            x1 = x1 + 140;
        }

        int y1 = 140;
        for (int i = 0; i < 4; i++) {

            g.drawLine(1100, y1, 1800, y1);
            y1 = y1 + 60;

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
//       TITELS 
        g.setFont(new Font("default", Font.BOLD, 30));
        g.drawString("DOZEN", 1350, 440);
        g.drawString("GRID", 430, 450);
        g.drawString("1", 1045, 480);
        g.drawString("2", 1195, 480);
        g.drawString("3", 1345, 480);
        g.drawString("4", 1495, 480);
        g.drawString("5", 1645, 480);
        g.drawString("6", 1795, 480);

        g.drawString("12/", 1017, 950);
        g.drawString("12/", 1167, 950);
        g.drawString("12/", 1317, 950);
        g.drawString("12/", 1467, 950);
        g.drawString("12/", 1617, 950);



        pickpak.tekenTSP(g);

    }
}
