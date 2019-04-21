public class Item {
    private String naam;
    private int grootte;
    private Coordinate coordinate;

    public Item(String naam, int grootte, Coordinate coordinate) {
        this.naam = naam;
        this.grootte = grootte;
        this.coordinate = coordinate;
    }

    public int getGrootte() {
        return grootte;
    }

    @Override
    public String toString() {
        return naam + " (" + grootte + ")";
    }
}
