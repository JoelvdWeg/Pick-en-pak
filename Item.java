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
    
    public Locatie getLocatie(){
        return locatie;
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
