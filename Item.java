public class Item {
    private String naam;
    private int grootte;
    private Locatie locatie;

    public Item(String naam, int grootte, Locatie locatie) {
        this.naam = naam;
        this.grootte = grootte;
        this.locatie = locatie;
    }
        
    public Item(Locatie locatie){
        this.locatie = locatie;
        this.naam = "NONAME";
        this.grootte = -1;
    }
    
    public Item(Locatie locatie, int grootte){
        this.locatie = locatie;
        this.naam = "NONAME";
        this.grootte = grootte;
    }
    
    public Locatie getLocatie(){
        return locatie;
    }
    
    public void setGrootte(int grootte){
        this.grootte = grootte;
    }

    public int getGrootte() {
        return grootte;
    }
    
    public Coordinate getCoord(){
        return locatie.getCoord();
    }

    @Override
    public String toString() {
        return "Locatie: " + locatie.toString();
    }
}
