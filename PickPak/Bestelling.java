
package PickPak;

import java.util.ArrayList;


public class Bestelling {
    private String naam, adres1, adres2, land;
    private ArrayList<Item> besteldeItems;

    public Bestelling(String naam, String adres1, String adres2, String land, ArrayList<Item> besteldeItems) {
        this.naam = naam;
        this.adres1 = adres1;
        this.adres2 = adres2;
        this.land = land;
        this.besteldeItems = besteldeItems;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getAdres1() {
        return adres1;
    }

    public void setAdres1(String adres1) {
        this.adres1 = adres1;
    }

    public String getAdres2() {
        return adres2;
    }

    public void setAdres2(String adres2) {
        this.adres2 = adres2;
    }

    public String getLand() {
        return land;
    }

    public void setLand(String land) {
        this.land = land;
    }

    public ArrayList<Item> getBesteldeItems() {
        return besteldeItems;
    }

    public void setBesteldeItems(ArrayList<Item> besteldeItems) {
        this.besteldeItems = besteldeItems;
    }

    @Override
    public String toString() {
        return "Bestelling{" + "naam=" + naam + ", adres1=" + adres1 + ", adres2=" + adres2 + ", land=" + land + ", besteldeItems=" + besteldeItems + '}';
    }
    
    
}
