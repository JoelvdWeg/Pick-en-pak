package PickPak;

public class Item {

    private String naam;
    private double grootte;
    private Locatie locatie;
    private int id;
    private double prijs;

    public Item(Locatie locatie, double grootte, int id, String naam, double prijs) {
        this.naam = naam;
        this.grootte = grootte;
        this.locatie = locatie;
        this.id = id;
        this.prijs = prijs;
    }

    public Item(Locatie locatie, int id) {
        this.locatie = locatie;
        this.naam = "NONAME";
        this.grootte = -1;
        this.id = id;
    }

    public Item(Locatie locatie, double grootte, int id, String naam) {
        this.locatie = locatie;
        this.naam = "NONAME";
        this.grootte = grootte;
        this.id = id;

    }

    public Locatie getLocatie() {
        return locatie;
    }

    public void setGrootte(double grootte) {
        this.grootte = grootte;
    }

    public double getGrootte() {
        return grootte;
    }

    public Coordinate getCoord() {
        return locatie.getCoord();
    }

    public int getID() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public double getPrijs() {
        return prijs;
    }

    @Override
    public String toString() {
        return id + ": " + naam + "," + prijs;
    }
}
