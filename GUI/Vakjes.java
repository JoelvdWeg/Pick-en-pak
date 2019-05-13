package GUI;

import java.awt.*;

public abstract class Vakjes {

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

    public abstract void tekenMij(Graphics g);
    
}
    
