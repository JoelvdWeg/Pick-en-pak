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
public class GridPanel extends JPanel{
    PickPak pickpak;
    
    public GridPanel (PickPak pickpak) {
        setPreferredSize(new Dimension(900, 600));
        this.pickpak = pickpak;
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.WHITE);
   
        g.setColor(Color.BLACK);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(5));

        g.drawRect(200, 10, 500, 500);
        int x = 300;
        for (int i = 0; i < 4; i++) {

            g.drawLine(x, 10, x, 510);
            x = x + 100;
        }

        int y = 110;
        for (int i = 0; i < 4; i++) {

            g.drawLine(200, y, 700, y);
            y = y + 100;
        }

        pickpak.tekenKraanPositie(g);
        
        pickpak.tekenTSP(g);

    }
}
