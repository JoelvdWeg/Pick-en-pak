package PickPak;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;


public class DozenPanel extends JPanel{
    PickPak pickpak;

    public DozenPanel (PickPak pickpak) {
        setPreferredSize(new Dimension(750, 700));
        this.pickpak = pickpak;
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
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


        pickpak.tekenDoosInhoud(g);
        pickpak.tekenDoosPositie(g);


    }
}
