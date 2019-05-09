
import java.util.ArrayList;

public class Pakbon {

    private String naam;
    private String adres1;
    private String adres2;
    private String land;
    private ArrayList<Item> items;

    public Pakbon() {
        items = new ArrayList<>();
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

    public ArrayList<Item> getItems() {
        return items;
    }

    public void voegItemToe(Item item) {
        items.add(item);
    }

    public String toString() {
        String s = "";
        s += "PAKBON\n\n";
        s += "----------------------------\n";
        s += naam + "\n";
        s += adres1 + "\n";
        s += adres2 + "\n";
        s += land + "\n";
        s += "----------------------------\n\n";
        s += "Producten:\n";

        for (Item item : items) {
            s += item + "\n";
        }
        return s;
    }
}
