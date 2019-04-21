import java.util.ArrayList;

public class BPP {
    private ArrayList<Doos> dozen;
    private ArrayList<Doos> volgorde;
    private ArrayList<Item> items;

    public BPP(ArrayList<Item> items) {
        dozen = new ArrayList<>();
        dozen.add(new Doos());
        volgorde = new ArrayList<>();
        this.items = items;

        bepaalVolgorde();
    }

    private void bepaalVolgorde() {
        try {
            for (int i = 0; i < items.size(); i++) {
                Doos geselecteerd = null;

                // Mogelijke dozen zoeken
                ArrayList<Doos> mogelijkeDozen = new ArrayList<>();
                for (Doos doos: dozen) {
                    if (doos.getRuimte() >= items.get(i).getGrootte()) {
                        mogelijkeDozen.add(doos);
                    }
                }

                if (mogelijkeDozen.size() == 0) { // Als het opject in geen enkele doos past
                    geselecteerd = new Doos(items.get(i));
                    dozen.add(geselecteerd);
                }
                else { // Als het object wel in één of meerdere dozen past.
                    // Kleinste doos uit mogelijke dozen.
                    Doos kleinsteDoos = mogelijkeDozen.get(0);
                    for (Doos doos : mogelijkeDozen) {
                        if (doos.getRuimte() < kleinsteDoos.getRuimte()) {
                            kleinsteDoos = doos;
                        }
                    }

                    // object in kleinste doos stoppen
                    geselecteerd = kleinsteDoos;
                    geselecteerd.aanvullen(items.get(i));
                }

                volgorde.add(geselecteerd);

                // Huidige ronde printen
                printRonde(i, geselecteerd);
            }

            printVolgorde();
        }
        catch (NullPointerException npe) {
            System.out.println("Geen items gegeven aan het BPP-algoritme.");
        }
    }

    public void printRonde(int i, Doos geselecteerd) {
        System.out.println("Grootte van object: " + items.get(i));

        for (int x = 0; x < dozen.size(); x++) {
            System.out.print(dozen.get(x));
            if (x + 1 == geselecteerd.getDoosID()) {
                System.out.println(" <--");
            }
            else {
                System.out.println();
            }
        }

        System.out.println();
    }

    public void printVolgorde() {
        System.out.println("\nPak volgorde:");

        for (Doos doos : volgorde) {
            System.out.print(doos.getDoosID() + " ");
        }

        System.out.println();
    }

    public ArrayList<Doos> getVolgorde() {
        return volgorde;
    }
}
