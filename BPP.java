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

        try {
            // Algortiem kiezen
            //bepaalVolgordeBestfit();
            bepaalVolgordeFirstfit();
        }
        catch (NullPointerException npe) {
            System.out.println("Geen items gegeven aan het BPP-algoritme.");
        }
    }

    private void bepaalVolgordeBestfit() {
        System.out.println("BESTFIT --------------------\n");

        for (int i = 0; i < items.size(); i++) {
            Doos geselecteerd;

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

            // Toevoegen aan volgorde
            volgorde.add(geselecteerd);

            // Huidige ronde printen
            printRonde(i, geselecteerd);
        }

        printVolgorde();
    }

    public void bepaalVolgordeFirstfit() {
        System.out.println("FIRSTFIT --------------------\n");

        for (int i = 0; i < items.size(); i++) {
            Doos geselecteerd = null;

            // Bij elke doos kijken of er ruimte is. Zo ja: selecteer en stop de for loop.
            for (Doos doos : dozen) {
                if (doos.getRuimte() >= items.get(i).getGrootte()) {
                    geselecteerd = doos;
                    geselecteerd.aanvullen(items.get(i));
                    break;
                }
            }

            // Nieuwe doos maken als er nog geen een geselecteerd is.
            if (geselecteerd == null) {
                geselecteerd = new Doos(items.get(i));
                dozen.add(geselecteerd);
            }

            // Toevoegen aan volgorde
            volgorde.add(geselecteerd);

            // Alle dozen printen
            printRonde(i, geselecteerd);
        }

        printVolgorde();
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
