/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author hylke
 */
public class Coordinaten extends Vakjes {
    public Coordinaten(int x, int y){
        super(x, y);
    }
    @Override
    public void tekenMij(Graphics g){
        g.setColor(Color.BLUE);
        g.fillRect(getX() * 40, getY() * 225, 100, 100);
    }
}
