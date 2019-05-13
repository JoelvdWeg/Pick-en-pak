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
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class PickFrame extends JFrame implements ActionListener{
private JTextField  jtfx, jtfy;
private JLabel      jlx, jly; 
private JButton     jbbevestig;



    public PickFrame() {
        setTitle("GUI");
        setSize(1200, 800);
        setLayout(new FlowLayout());
        
        
        jlx = new JLabel("x-as");
        add(jlx);
        
        jtfx = new JTextField(4);
//        jtfx.addActionListener(this);
        add(jtfx);
        
        jly  = new JLabel ("y-as");
        add(jly);
        
        jtfy = new JTextField(4);
//        jtfx.addActionListener(this);
        add(jtfy);
        
        jbbevestig = new JButton ("bevestig");
        add(jbbevestig);
        
        setVisible(true);
        PickPanel panel = new PickPanel();
        add(panel);
        
        
    }
    
    public void actionPerformed(ActionEvent ae){
        
    }

}
