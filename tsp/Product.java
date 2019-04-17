package tsp;

public class Product {
    private Coordinate coord;
    private String name;
    private int grootte;
    private final int DEFAULT_GROOTTE = 0;
    private final String DEFAULT_NAME = "NONAME";

    public Product(Coordinate coord, int grootte) {
        this.coord = coord;
        this.name = DEFAULT_NAME;
        this.grootte = grootte;
    }
    
    public Product(Coordinate coord, String name){
        this.coord = coord;
        this.name = name;
        this.grootte = DEFAULT_GROOTTE;
    }
    
    public Product(Coordinate coord, String name, int grootte){
        this.coord = coord;
        this.name = name;
        this.grootte = grootte;
    }
    
    public Product(Coordinate coord){
        this.coord = coord;
        this.name = DEFAULT_NAME;
        this.grootte = DEFAULT_GROOTTE;
    }

    public Coordinate getCoord() {
        return coord;
    }

    public void setCoord(Coordinate coord) {
        this.coord = coord;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGrootte() {
        return grootte;
    }

    public void setGrootte(int grootte) {
        this.grootte = grootte;
    }
    
    public String toString(){
        return coord.toString();
    }
}
