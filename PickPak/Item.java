package PickPak;

public class Item {
    private String naam;
    private double grootte;
    private Locatie locatie;
    private int voorraad;
    private int id;

    public Item(Locatie locatie, double grootte, int id, String naam, int voorraad) {
        this.naam = naam;
        this.grootte = grootte;
        this.locatie = locatie;
        this.voorraad = voorraad;
        this.id = id;
    }
        
    public Item(Locatie locatie, int id){
        this.locatie = locatie;
        this.naam = "NONAME";
        this.grootte = -1;
        this.voorraad = -1;
        this.id = id;
    }
    
    public Item(Locatie locatie, double grootte, int id){
        this.locatie = locatie;
        this.naam = "NONAME";
        this.grootte = grootte;
        this.voorraad = -1;
        this.id = id;
    }

    public int getVoorraad() {
        return voorraad;
    }

    public Locatie getLocatie(){
        return locatie;
    }
    
    public void setGrootte(double grootte){
        this.grootte = grootte;
    }

    public double getGrootte() {
        return grootte;
    }
    
    public Coordinate getCoord(){
        return locatie.getCoord();
    }
    
    public int getID(){
        return id;
    }
    
    public String getNaam(){
        return naam;
    }

    @Override
    public String toString() {
        return id + ": " + naam + " (grootte: "+grootte+")" + "coordinaten:  "+locatie.getCoord().getX()+"      "+locatie.getCoord().getY(); 
    }
}
