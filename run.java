
import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class run {

    public static ArrayList<Item> items;
    private static ArrayList<Item> picks;
    private static ArrayList<Integer> volgorde;
    private static ArrayList<Integer> route;
    private static Pakbon pakbon;

    public static void main(String[] args) {
        maakItems();

        maakPakbon();

        BPP bpp = new BPP(picks);
        volgorde = bpp.getVolgorde();
        System.out.println("Doos volgorde bepaald:");
        System.out.println(volgorde+"\n...");

        TSP tsp = new TSP(picks);
        route = tsp.getBestRoute();
        System.out.println("Route bepaald:");
        System.out.println(route+"\n...");

        System.out.println("\n\n" + pakbon + "\n\n");

        System.out.println("Done!");
    }

    private static void maakPakbon() {
        picks = new ArrayList<>();
        pakbon = new Pakbon();

        pakbon.setNaam("Lukas van Elten");
        pakbon.setAdres1("Molenmakerslaan 58");
        pakbon.setAdres2("3781DD Voorthuizen");
        pakbon.setLand("NEDERLAND");

        try {
            FileReader file = new FileReader("newfile.txt");
            Scanner parser = new Scanner(file);
            int k = 1;
            while (parser.hasNextLine()) {
                String nextLine = parser.nextLine();
                voegToeAanPakbon(nextLine);
                System.out.println("Totaal: " + k + " items\n...");
                k++;
            }
        } catch (FileNotFoundException fe) {
            System.out.println(fe);
        }
    }

    private static void voegToeAanPakbon(String line) {
        int nextItemID = Integer.parseInt(line);

        for (Item item : items) {
            if (item.getLocatie().getID() == nextItemID) {
                picks.add(item);
                pakbon.voegItemToe(item);
                System.out.println("Item met locatie-id " + item.getLocatie().getID() + " toegevoegd aan picklijst");
                break;
            }
        }

    }

    private static void maakItems() {
        items = new ArrayList<>();

        try {
            FileReader file = new FileReader("fruit.txt");
            Scanner parser = new Scanner(file);
            int k = 0;
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    String nextLine = parser.nextLine();
                    items.add(new Item(new Locatie(k, new Coordinate(i, j)), Integer.parseInt(nextLine.split(" ")[2]), Integer.parseInt(nextLine.split(" ")[0]),nextLine.split(" ")[1]));
                    k++;
                }
            }
            items.add(new Item(new Locatie(k, new Coordinate(4, 0)), 0, 0, "LOSPUNT"));
            System.out.println("Items aangemaakt\n...");
        } catch (FileNotFoundException fe) {
            System.out.println(fe);
        }

    }
}
