package GUI;

import java.awt.*;

public abstract class Vakjes extends Pick {
    private String naam;
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
        return naam + " (" + x + ", " + y + ")";
    }

    public void tekenMij(Graphics g){
        g.fillRect(getVakjex() * 40, getVakjey() * 40, 40, 40);  
    }
    
}