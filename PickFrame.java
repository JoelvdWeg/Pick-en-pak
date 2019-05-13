import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.*;
import javax.swing.*;

public class PickFrame extends JFrame implements ActionListener{
    JTextField jtfX,jtfY,jtfX2,jtfY2;
    run r;
    
    public PickFrame(int breedte, int hoogte){
        setTitle("Pickpanel");
        setSize(breedte, hoogte);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        
        this.r = r;
        
        JLabel jlX = new JLabel("X: ");
        JLabel jlY = new JLabel("Y: ");
        JLabel jlX2 = new JLabel("X2: ");
        JLabel jlY2 = new JLabel("Y2: ");
        jtfX2 = new JTextField(5);
        jtfY2 = new JTextField(5);
        jtfX = new JTextField(5);
        jtfY = new JTextField(5);
        JButton jbOk = new JButton("OK");
        jbOk.addActionListener(this);
        
        PickPanel panel = new PickPanel(600, 600);
        
        //add(jlX);
        //add(jtfX);
        //add(jlY);
        //add(jtfY);
        //add(jlX2);
        //add(jtfX2);
        //add(jlY2);
        //add(jtfY2);
        add(jbOk);
        add(panel);
        
        setVisible(true);
        
    }
    
    public void actionPerformed(ActionEvent ae){
        //run.voegLijnToe(new Lijn(new Coordinate(Integer.parseInt(jtfX.getText()),Integer.parseInt(jtfY.getText())),new Coordinate(Integer.parseInt(jtfX2.getText()),Integer.parseInt(jtfY2.getText()))));
        
        repaint();
    }
}
