/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PickPak;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
        setPreferredSize(new Dimension(900, 700));
        this.pickpak = pickpak;
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.WHITE);
   
        g.setColor(Color.BLACK);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(5));

        g.drawRect(200, 50, 500, 500);
        int x = 300;
        for (int i = 0; i < 4; i++) {

            g.drawLine(x, 50, x, 550);
            x = x + 100;
        }

        int y = 150;
        for (int i = 0; i < 4; i++) {

            g.drawLine(200, y, 700, y);
            y = y + 100;
        }

        //      Titels
        g.setColor(Color.BLACK);
        g.setFont(new Font("default", Font.BOLD, 50));
        

        g.drawString("0", 130, 520);
        g.drawString("1", 130, 420);
        g.drawString("2", 130, 320);
        g.drawString("3", 130, 220);
        g.drawString("4", 130, 120);

        g.drawString("0", 230, 600);
        g.drawString("1", 330, 600);
        g.drawString("2", 430, 600);
        g.drawString("3", 530, 600);
        g.drawString("4", 630, 600);
        

        
        pickpak.tekenKraanPositie(g);
        
        pickpak.tekenTSP(g);

    }
}
