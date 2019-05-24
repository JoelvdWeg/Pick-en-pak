package PickPak;

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;

public class nieOpVoorraadDialoog extends JDialog {

    public nieOpVoorraadDialoog(String item) {
        setTitle("Geavanceerde instellingen");
        setSize(new Dimension(600, 240));
        setLayout(new FlowLayout());
        
        JLabel l = new JLabel(item+" is niet op voorraad");
        add(l);
        
        
    }
}
