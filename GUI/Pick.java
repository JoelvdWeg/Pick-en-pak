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
private ArrayList<Vakjes> lijst;


    public Pick() {
        lijst = new ArrayList<>();
    }
    
    public void naarVakje(Vakjes element) {
        lijst.add(element);
}

    public void tekenGrid(Graphics g){
       
        for (Vakjes element : lijst){
            element.tekenMij(g);
        }
    }

}






