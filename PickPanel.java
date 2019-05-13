
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

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
        
        g.drawRect(1, 1, breedte-20, hoogte-20);
    }
}
