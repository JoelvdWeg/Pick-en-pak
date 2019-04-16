package BPP;

import java.util.ArrayList;

public class Bestfit {
    private int[] objecten = {10, 9, 2};
    private ArrayList<Doos> dozen;

    public Bestfit() {
        dozen = new ArrayList<>();
        dozen.add(new Doos());

        for (int i = 0; i < objecten.length; i++) {
            // Mogelijke dozen zoeken
            ArrayList<Doos> mogelijkeDozen = new ArrayList<>();
            for (Doos doos: dozen) {
                if (doos.getOver() >= objecten[i]) {
                    mogelijkeDozen.add(doos);
                }
            }

            if (mogelijkeDozen.size() == 0) { // Als het opject in geen een doos past
                dozen.add(new Doos(objecten[i]));
            }
            else { //Als het opbject wel in één of meerdere dozen past.
                // Kleinste doos uit mogelijke dozen
                Doos kleinsteDoos = mogelijkeDozen.get(0);
                for (Doos doos : mogelijkeDozen) {
                    if (doos.getOver() < kleinsteDoos.getOver()) {
                        kleinsteDoos = doos;
                    }
                }

                // object in kleinste doos stoppen
                kleinsteDoos.aanvullen(objecten[i]);
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
