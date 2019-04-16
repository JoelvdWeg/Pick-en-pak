package BPP;

import java.util.ArrayList;

public class Worstfit {
    private int[] objecten = {10, 9, 4, 2};
    private ArrayList<Doos> dozen;

    public Worstfit() {
        dozen = new ArrayList<>();
        dozen.add(new Doos());

        for (int i = 0; i < objecten.length; i++) {
            // Zoek minst gevulde doos
            Doos leegsteDoos = dozen.get(0);
            for (Doos doos : dozen) {
                if (doos.getOver() > leegsteDoos.getOver()) {
                    leegsteDoos = doos;
                }
            }

            // Stop item in minst gevulde doos als het past anders in nieuwe doos.
            if (leegsteDoos.getOver() >= objecten[i]) {
                leegsteDoos.aanvullen(objecten[i]);
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
