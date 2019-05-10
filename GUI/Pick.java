/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;
import java.awt.*;
import java.util.ArrayList;

/**
 *
 * @author hylke
 */
public class Pick {
private int vakjex;
private int vakjey;
private ArrayList<Vakjes> lijst;


    public Pick() {
        lijst = new ArrayList<>();
    }
    
public Pick(int vakjex, int vakjey) {
        this.vakjex = vakjex;
        this.vakjey = vakjey;
}

    public int getVakjex() {
        return vakjex;
    }

    public int getVakjey() {
        return vakjey;
    }

public void vakjex() {
    if (vakjex > 5) {
        vakjex--;
    }
    
    if (vakjex < 1 )
        vakjex++;
        }

public void vakjey(){
    if (vakjey > 5) {
        vakjey--;
    }
    if (vakjey < 1 )
        vakjey++;
}        
    public void naarVakje(Vakjes element) {
        lijst.add(element);
    }
    
    public void tekenMij(Graphics g) {
        g.fillRect(getVakjex() * 40, getVakjey() * 40, 40, 40);
    
    
    }


}



