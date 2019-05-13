import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.*;
import javax.swing.*;

public class PickFrame extends JFrame implements ActionListener{
    
    public PickFrame(int breedte, int hoogte){
        setTitle("Pickpanel");
        setSize(breedte, hoogte);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        
        JLabel jlX = new JLabel("X: ");
        JLabel jlY = new JLabel("Y: ");
        JTextField jtfX = new JTextField(5);
        JTextField jtfY = new JTextField(5);
        JButton jbOk = new JButton("OK");
        
        PickPanel panel = new PickPanel(1200, 600);
        
        add(jlX);
        add(jtfX);
        add(jlY);
        add(jtfY);
        add(jbOk);
        add(panel);
        
        setVisible(true);
        
        repaint();
    }
    
    public void actionPerformed(ActionEvent ae){
        
    }
}
