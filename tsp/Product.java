package tsp;

public class Product {
    private Coordinate coord;
    private String name;

    public Product(Coordinate coord) {
        this.coord = coord;
        this.name = "NONAME";
    }
    
    public Product(Coordinate coord, String name){
        this.coord = coord;
        this.name = name;
    }

    public Coordinate getCoord() {
        return coord;
    }

    public void setCoord(Coordinate coord) {
        this.coord = coord;
    }
    
    public String toString(){
        return coord.toString();
    }
}
