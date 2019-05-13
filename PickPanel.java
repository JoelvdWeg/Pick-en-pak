
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
//1100 600

public class PickPanel extends JPanel{
    int hoogte,breedte;
    
    public PickPanel(int breedte, int hoogte){
        setPreferredSize(new Dimension(breedte, hoogte));
        this.breedte = breedte;
        this.hoogte = hoogte;
    }
    
    @Override
    public void paintComponent(Graphics g){
        setBackground(Color.GRAY);
        
        //g.drawRect(0,0,599,599);
        
        for(int i = 0; i < 5; i++){
            g.drawLine(0,i*100,600,i*100);
        }
        for(int j = 0; j < 5; j++){
            g.drawLine(j*100, 0, j*100, 600);
        }
        //repaint();
    }
}
