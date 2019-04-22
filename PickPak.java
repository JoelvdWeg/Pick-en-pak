import java.util.ArrayList;

public class PickPak {
    private ArrayList<Doos> volgorde;
    private ArrayList<Item> items;
    private static final int AANTAL_VAKKEN = 30;

    public PickPak() {
        items = new ArrayList<>();

        addItem("Banaan", 3, new Coordinate(3, 4));
        addItem("Peer", 9, new Coordinate(3, 4));
        addItem("Citroen", 2, new Coordinate(3, 4));
        addItem("Appel", 7, new Coordinate(3, 4));
        addItem("Druif", 4, new Coordinate(3, 4));
        addItem("Kers", 7, new Coordinate(3, 4));

        // TSP

        BPP bpp = new BPP(items);
        volgorde = bpp.getVolgorde();
    }

    public void addItem(String naam, int grootte, Coordinate coordinate) {
        Item i = new Item(naam, grootte, coordinate);
        items.add(i);
    }
}
