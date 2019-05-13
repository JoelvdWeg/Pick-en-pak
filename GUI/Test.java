/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

/**
 *
 * @author hylke
 */
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Test extends JPanel {

    public static void main (String[] args) {      
        PickFrame frame = new PickFrame();
        frame.setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );
    }
}
