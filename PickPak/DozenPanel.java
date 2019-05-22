/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PickPak;

import PickPak. *;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

/**
 *
 * @author hylke
 */
public class DozenPanel extends JPanel{
    PickPak pickpak;
    
    public DozenPanel (PickPak pickpak) {
        setPreferredSize(new Dimension(1000, 500));
        this.pickpak = pickpak;
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.WHITE);
        
        
        
        g.setColor(Color.BLACK);
        Graphics2D g2 = (Graphics2D) g;
        
        //            DOZEN 
        g2.setStroke(new BasicStroke(2));
        g.drawRect(1100, 110, 50, 400);
        g.drawRect(1200, 110, 50, 400);
        g.drawRect(1300, 110, 50, 400);
        g.drawRect(1400, 110, 50, 400);
        g.drawRect(1500, 110, 50, 400);
        g.drawRect(1600, 110, 50, 400);

        g.setColor(Color.GRAY);
        g.fillRect(1100, 110, 50, 400);
        g.fillRect(1200, 110, 50, 400);
        g.fillRect(1300, 110, 50, 400);
        g.fillRect(1400, 110, 50, 400);
        g.fillRect(1500, 110, 50, 400);
        g.fillRect(1600, 110, 50, 400);

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
        
        pickpak.tekenDoosInhoud(g);
        
        pickpak.tekenDoosPositie(g);


    }
}