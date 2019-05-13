
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
        
        g.setColor(Color.BLACK);
        
        for(int i = 0; i < 5; i++){
            g.drawLine(0,i*100,500,i*100);
        }
        for(int j = 0; j < 5; j++){
            g.drawLine(j*100, 0, j*100, 500);
        }
        
        g.setColor(Color.BLUE);
        
        int k = 0;
        for(Lijn l: run.lijnen){
            int startx = l.getStart().getX();
            int starty = l.getStart().getY();
            int eindx = l.getEind().getX();
            int eindy = l.getEind().getY();
            g.drawLine(startx*100+50,starty*100+50,eindx*100+50,eindy*100+50);
            if(k == 0){
                g.setColor(Color.GREEN);
            } else if(k == run.lijnen.size()-1){
                g.setColor(Color.RED);
            }
            g.fillOval(startx*100+45,starty*100+45,10,10);
            g.setColor(Color.BLUE);
            k++;
        }
        
    }
}
