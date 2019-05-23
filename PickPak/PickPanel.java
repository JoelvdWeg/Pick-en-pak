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
        g.drawRect(10, 0, 1900, 990);
        //g.drawLine(950, 10, 950, 1000);
//          lijn op de horizontale helft
        //g.drawLine(10, 400, 1920, 400);
        g.drawRect(200, 50, 500, 500);

//        GRID 
        int x = 300;
        for (int i = 0; i < 4; i++) {

            g.drawLine(x, 50, x, 950);
            x = x + 100;
        }

        int y = 150;
        for (int i = 0; i < 4; i++) {

            g.drawLine(200, y, 700, y);
            y = y + 100;
        }

//            DOZEN 
        g2.setStroke(new BasicStroke(2));
        g.drawRect(1100, 100, 50, 400);
        g.drawRect(1200, 100, 50, 400);
        g.drawRect(1300, 100, 50, 400);
        g.drawRect(1400, 100, 50, 400);
        g.drawRect(1500, 100, 50, 400);
        g.drawRect(1600, 100, 50, 400);
        


        g.setColor(Color.GRAY);
        g.fillRect(1100, 100, 50, 400);
        g.fillRect(1200, 100, 50, 400);
        g.fillRect(1300, 100, 50, 400);
        g.fillRect(1400, 100, 50, 400);
        g.fillRect(1500, 100, 50, 400);
        g.fillRect(1600, 100, 50, 400);

        g.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(4));
        //g.drawRect(1100, 75, 700, 300);

//        int x1 = 1250;
//        for (int i = 0; i < 4; i++) {
//
//            g.drawLine(x1, 75, x1, 375);
//            x1 = x1 + 140;
//        }
//
//        int y1 = 140;
//        for (int i = 0; i < 4; i++) {
//
//            g.drawLine(1100, y1, 1800, y1);
//            y1 = y1 + 60;
//
//        }
        
        //pickpak.tekenDoos(g);

        pickpak.tekenKraanPositie(g);
        
        pickpak.tekenTSP(g);
        
        pickpak.tekenDoosInhoud(g);
        
        pickpak.tekenDoosPositie(g);

    }
}
