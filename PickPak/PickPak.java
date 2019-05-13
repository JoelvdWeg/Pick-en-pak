package PickPak;

import java.sql.Connection;
import arduino.Arduino;
import java.awt.Color;
import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;
import java.sql.*;
import java.awt.Graphics;

public class PickPak {

    public static ArrayList<Item> items;
    //public static ArrayList<Item> picks;
    //private static ArrayList<Integer> volgorde;
    private static ArrayList<Integer> route;
    private static Connection connection;
    public static TSP tsp;
    //private static Pakbon pakbon;
    //public static ArrayList<Lijn> lijnen = new ArrayList<>();

    public PickPak() {
        if (maakDatabaseConnectie()) {
            haalItemsOp();
            sluitDatabaseConnectie();
        }
    }

    public void pickBestelling(ArrayList<Item> picks) {
        TSP tsp = new TSP(picks);
        route = tsp.getBestRoute();
        System.out.println("Route bepaald:");
        System.out.println(route + "\n...");

        //tekenTSP(route);
        BPP bpp = new BPP(picks);
        ArrayList<Integer> volgorde = bpp.getVolgorde();
        System.out.println("Doos volgorde bepaald:");
        System.out.println(volgorde + "\n...");

        draaiSchijf(volgorde);
    }

    public void maakLijnen(ArrayList<Integer> route) {
        ArrayList<Lijn> lijnen = new ArrayList<>();
        for (int i = 0; i < route.size() - 1; i++) {
            lijnen.add(new Lijn(items.get(route.get(i)).getLocatie().getCoord(), items.get(route.get(i + 1)).getLocatie().getCoord()));
        }
    }

    public void tekenTSP(Graphics g) {
        g.setColor(Color.BLUE);

        if (route != null) {
            
            int k = 0;
            for (int r = 0; r < route.size() - 1; r++) {
                int startx = items.get(route.get(r)).getLocatie().getCoord().getX();
                int starty = items.get(route.get(r)).getLocatie().getCoord().getY();
                int eindx = items.get(route.get(r + 1)).getLocatie().getCoord().getX();
                int eindy = items.get(route.get(r + 1)).getLocatie().getCoord().getY();
                
                System.out.println(startx+"..."+starty+"..."+eindx+"..."+eindy);

                g.drawLine(250 + startx * 100, 900 - 100 * starty, 250 + eindx * 100, 900 - eindy * 100);

                if (k == 0) {
                    g.setColor(Color.GREEN);
                } else if (k == route.size() - 1) {
                    g.setColor(Color.RED);
                }
                g.fillOval(startx * 100 + 245, 895 - starty * 100, 10, 10);
                g.setColor(Color.BLUE);
                k++;
            }
        }
    }

    private static boolean maakDatabaseConnectie() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost/wideworldimporters";
            connection = DriverManager.getConnection(url, "root", "");
            System.out.println("Databaseconnectie succesvol\n...");
            return true;
        } catch (Exception e) {
            System.out.println("Databaseconnectie niet succesvol, bestelling afgebroken\n...");
            return false;
        }
    }

    private static void sluitDatabaseConnectie() {
        try {
            connection.close();
            System.out.println("Databaseconnectie succesvol gesloten\n...");
        } catch (Exception e) {
            System.out.println("Databaseconnectie kon niet worden gesloten\n...");
        }
    }

    private static void draaiSchijf(ArrayList<Integer> volgorde) {
        try {
            Arduino arduino = new Arduino("COM3", 9600); //enter the port name here, and ensure that Arduino is connected, otherwise exception will be thrown.
            arduino.openConnection();

            arduino.serialRead();

            System.out.println("Druk op de knop om de schijf te kalibreren\n...");

            while (arduino.serialRead().equals("")) {
                //wait for incoming message  
            }

            System.out.println("Schijf gekalibreerd\n...");

            char c;

            volgorde.add(1);
            for (int i : volgorde) {
                if (i < 7) {
                    System.out.println("Draai naar doos " + i + "\n...");
                    c = (char) (i + 48);
                    arduino.serialWrite(c);
                    while (arduino.serialRead().equals("")) {
                        //wait for incoming message
                    }
                    System.out.println("Schijf is gedraaid\n...");
                    Thread.sleep(1000);
                } else {
                    System.out.println("Kan niet draaien naar doos " + i + "\n...");
                }
            }

            arduino.closeConnection();
            System.out.println("Done");
        } catch (Exception e) {
            System.out.println("Connectie met de motor kon niet worden opgezet\n...");
        }
    }

    public static ArrayList<Item> maakBestellingAan(String naam, String adres1, String adres2, String land, String f) {
        if (maakDatabaseConnectie()) {

            ArrayList<Item> picks = new ArrayList<>();

            try {
                Statement bestellingIDstatement = connection.createStatement();
                ResultSet bestellingIDresult = bestellingIDstatement.executeQuery("SELECT MAX(id) FROM bestelling");
                bestellingIDresult.next();
                int newPakbonID = bestellingIDresult.getInt(1) + 1;
                bestellingIDresult.close();

                PreparedStatement newBestelling = connection.prepareStatement("INSERT INTO bestelling VALUES(?,?,?,?,?,?)");
                newBestelling.setInt(1, newPakbonID);
                newBestelling.setString(2, naam);
                newBestelling.setString(3, adres1);
                newBestelling.setString(4, adres2);
                newBestelling.setString(5, land);
                newBestelling.setInt(6, newPakbonID);
                newBestelling.executeUpdate();

                System.out.println("Bestelling toegevoegd aan database\n...");
            } catch (Exception e) {
                System.out.println("Kon geen bestelling toevoegen aan de database\n...");
            }

            try {
                FileReader file = new FileReader(f);
                Scanner parser = new Scanner(file);
                int k = 1;
                while (parser.hasNextLine()) {
                    String nextLine = parser.nextLine();
                    picks = voegToeAanPicks(nextLine, picks);
                    System.out.println("Totaal: " + k + " items\n...");
                    k++;
                }
            } catch (FileNotFoundException fe) {
                System.out.println("Kon geen bestelling vinden\n...");
            }

            sluitDatabaseConnectie();

            return picks;
        }
        return null;
    }

    private static ArrayList<Item> voegToeAanPicks(String line, ArrayList<Item> picks) {
        int nextItemID = Integer.parseInt(line);

        for (Item item : items) {
            if (item.getLocatie().getID() == nextItemID) {
                picks.add(item);
                System.out.println("Item met locatie-id " + item.getLocatie().getID() + " toegevoegd aan picklijst");
                break;
            }
        }
        return picks;
    }

    private static void haalItemsOp() {
        items = new ArrayList<>();

        try {
            PreparedStatement itemsStatement = connection.prepareStatement("SELECT StockItemID, StockItemName, TypicalWeightPerUnit FROM stockitems WHERE StockItemID < 26");
            ResultSet itemsResultSet = itemsStatement.executeQuery();
            int i = 0, j = 0;
            while (itemsResultSet.next()) {
                items.add(new Item(new Locatie(itemsResultSet.getInt(1) - 1, new Coordinate(j, i)), itemsResultSet.getDouble(3), itemsResultSet.getInt(1) - 1, itemsResultSet.getString(2)));
                if (j != 4) {
                    j++;
                } else {
                    i++;
                    j = 0;
                }
            }
            items.add(new Item(new Locatie(25, new Coordinate(0, 0)), 0, 25, "LOSPUNT"));
            System.out.println("Items succesvol opgehaald uit database\n..");
        } catch (Exception e) {
            System.out.println("Kon items niet ophalen uit de database\n...");
        }
    }
}
