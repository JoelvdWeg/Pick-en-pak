package PickPak;

public class Locatie {
    private Coordinate coordinate;
    private int voorraad;
    private int id;

    public Locatie(int id, Coordinate coordinate) {
        this.coordinate = coordinate;
        this.id = id;
    }

    public int getVoorraad() {
        return voorraad;
    }
    
    public int getID(){
        return id;
    }
    
    public Coordinate getCoord(){
        return coordinate;
    }
    
    public String toString(){
        return "   id  :  "+id + coordinate.toString();
    }
}
