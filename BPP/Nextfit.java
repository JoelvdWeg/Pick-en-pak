package BPP;

import java.util.ArrayList;

public class Nextfit {
    private int[] objecten = {10, 9, 2};
    private ArrayList<Doos> dozen;

    public Nextfit() {
        dozen = new ArrayList<>();
        dozen.add(new Doos());

        for (int i = 0; i < objecten.length; i++) {
            if (dozen.get(dozen.size() - 1).getOver() >= objecten[i]) { // Als het object in de laatste doos past.
                dozen.get(dozen.size() - 1).aanvullen(objecten[i]); // object in doos stoppen.
            }
            else {
                dozen.add(new Doos(objecten[i]));
            }

            // Alle dozen printen
            System.out.println("Grootte van object: " + objecten[i]);
            int count = 1;
            for (Doos doos : dozen) {
                System.out.println(Integer.toString(count) + " " + doos);
                count++;
            }
            System.out.println("------");
        }
    }
}
