package BPP;

import java.util.ArrayList;

public class Firstfit {
    private int[] objecten = {10, 9, 2};
    private ArrayList<Doos> dozen;

    public Firstfit() {
        dozen = new ArrayList<>();
        dozen.add(new Doos());

        for (int i = 0; i < objecten.length; i++) {
            boolean gevuld = false;

            for (Doos doos : dozen) { // Bij elke doos kijken of er ruimte is. Zo ja stopt de for loop.
                if (doos.getOver() >= objecten[i]) {
                    doos.aanvullen(objecten[i]);
                    gevuld = true;
                    break;
                }
            }

            if (!gevuld) { // Als er geen enkele doos gevuld is
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
