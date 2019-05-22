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
import java.awt.Font;
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
        setPreferredSize(new Dimension(750, 700));
        this.pickpak = pickpak;
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.WHITE);
        
        
        
        g.setColor(Color.BLACK);
        Graphics2D g2 = (Graphics2D) g;
        
        //            DOZEN 
        g2.setStroke(new BasicStroke(2));
        g.drawRect(20, 110, 50, 400);
        g.drawRect(120, 110, 50, 400);
        g.drawRect(220, 110, 50, 400);
        g.drawRect(320, 110, 50, 400);
        g.drawRect(420, 110, 50, 400);
        g.drawRect(520, 110, 50, 400);

        g.setColor(Color.GRAY);
        g.fillRect(20, 110, 50, 400);
        g.fillRect(120, 110, 50, 400);
        g.fillRect(220, 110, 50, 400);
        g.fillRect(320, 110, 50, 400);
        g.fillRect(420, 110, 50, 400);
        g.fillRect(520, 110, 50, 400);

        g.setColor(Color.BLACK);
        g.setFont(new Font("default", Font.BOLD, 30));
        g.drawString("DOZEN", 1350, 20);
        
        g.drawString("1", 35, 90);
        g.drawString("2", 135, 90);
        g.drawString("3", 235, 90);
        g.drawString("4", 335, 90);
        g.drawString("5", 435, 90);
        g.drawString("6", 535, 90);
        
        g.drawString("12/12", 5, 550);
        g.drawString("12/12", 105, 550);
        g.drawString("12/12", 205, 550);
        g.drawString("12/12", 305, 550);
        g.drawString("12/12", 405, 550);
        g.drawString("12/12", 505, 550);
        
        
        
        
        
        
        
        
//        g.setColor(Color.BLACK);
//        g2.setStroke(new BasicStroke(4));
//        g.drawRect(1100, 75, 700, 300);
//
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
        
        pickpak.tekenDoosInhoud(g);
        
        pickpak.tekenDoosPositie(g);


    }
}