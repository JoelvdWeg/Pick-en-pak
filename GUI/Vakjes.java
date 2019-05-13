package GUI;

import java.awt.*;

public class Vakjes {

    private int x;
    private int y;

    public Vakjes(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String toString() {
        return " (" + x + ", " + y + ")";
    }

    public void tekenMij(Graphics g){
        g.setColor(Color.BLUE);
        g.fillRect(203 + 100*getX(), 853 - 100*getY(), 95, 95);
    }
    
}
    
