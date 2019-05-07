
import java.util.ArrayList;
import java.util.Random;

public class PickPak {

    private ArrayList<Doos> volgorde;
    public static ArrayList<Item> items;
    public ArrayList<Item> pakbon;

    public PickPak() {
        items = new ArrayList<>();
        Random r = new Random();
        int k = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                items.add(new Item(new Locatie(k, new Coordinate(i, j)),r.nextInt(12)+1));
                k++;
            }
        }
        items.add(new Item(new Locatie(k, new Coordinate(4, 0))));

        pakbon = new ArrayList<Item>();

        for(int i = 0; i < r.nextInt(25)+1; i++){
            pakbon.add(items.get(r.nextInt(26)));
        }

        
        
        BPP bpp = new BPP(pakbon);
        volgorde = bpp.getVolgorde();
        
        TSP tsp = new TSP(pakbon);
        System.out.println("Beste route: "+tsp.getBestRoute());
    }

    
}
