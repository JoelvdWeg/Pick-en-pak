
import java.util.ArrayList;
import java.util.Random;

public class PickPak {

    private ArrayList<Doos> volgorde;
    public static ArrayList<Item> items;
    //public ArrayList<Item> pakbon;

    public PickPak(ArrayList<Item> pakbon) {
        

        //pakbon = new ArrayList<Item>();

        //for(int i = 0; i < r.nextInt(25)+1; i++){
        //    pakbon.add(items.get(r.nextInt(26)));
        //}

        BPP bpp = new BPP(pakbon);
        volgorde = bpp.getVolgorde();
        
        TSP tsp = new TSP(pakbon);
        System.out.println("Beste route: "+tsp.getBestRoute());
    }

    
}
