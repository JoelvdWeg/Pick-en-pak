package tsp;

public class Coordinate {
    private double x,y;

    public Coordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public String toString() {
        return "Coordinate{" + "x=" + x + ", y=" + y + '}';
    }
    
    public double dist(Coordinate other){
        return Math.sqrt(((this.x-other.getX())*(this.x-other.getX())) + ((this.y-other.getY())*(this.y-other.getY())));
    }
}
