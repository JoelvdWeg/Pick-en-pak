package PickPak;

import java.sql.Connection;
import arduino.Arduino;
import java.awt.BasicStroke;
import java.awt.Color;
import java.io.*;
import java.util.ArrayList;
import java.sql.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class PickPak {
    
    private double[] doosInhoud = {0.0,0.0,0.0,0.0,0.0,0.0};

    private int kraanPositie, doosPositie;

    public static ArrayList<Item> items;

    public static ArrayList<Integer> route, volgorde;

    private static Connection connection;

    private boolean aanHetKalibreren;

    public PickPak() {
        kraanPositie = 0;
        doosPositie = 1;
        aanHetKalibreren = false;

        if (maakDatabaseConnectie()) {
            haalItemsOp();
            sluitDatabaseConnectie();
        }
    }

    public ArrayList<Item> leesBestelling(String f) {
        try {
            String naam = "";
            String adres1 = "";
            String adres2 = "";
            String land = "";
            ArrayList<Integer> besteldeItems = new ArrayList<>();

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            org.w3c.dom.Document document = documentBuilder.parse(new File(f));

            Element rootElement = (Element) document.getFirstChild();

            NodeList nlist = rootElement.getChildNodes();

            for (int i = 0; i < nlist.getLength(); i++) {

                Node child = nlist.item(i);

                String nodeName = (String) child.getNodeName();

                if (nodeName.equals("naam")) {
                    naam = child.getTextContent();
                } else if (nodeName.equals("adres1")) {
                    adres1 = child.getTextContent();
                } else if (nodeName.equals("adres2")) {
                    adres2 = child.getTextContent();
                } else if (nodeName.equals("land")) {
                    land = child.getTextContent();
                } else if (nodeName.equals("item")) {

                    NodeList itemList = child.getChildNodes();

                    Node idNode = itemList.item(0);
                    Node aantalNode = itemList.item(1);

                    int itemID = Integer.parseInt(idNode.getTextContent());
                    int aantal = Integer.parseInt(aantalNode.getTextContent());

                    for (int k = 0; k < aantal; k++) {
                        besteldeItems.add(itemID);
                    }
                }
            }

            ArrayList<Item> bestelling = maakBestellingAan(naam, adres1, adres2, land, besteldeItems);
            return bestelling;
        } catch (Exception ex) {
            System.out.println("Error!");
            System.out.println(ex);
        }
        return null;
    }

    public void maakRouteVoorGUI(ArrayList<Item> picks) {
        route = null;
        TSP tsp = null;
        tsp = new TSP(picks);
        route = tsp.getBestRoute();
        System.out.println("Route bepaald:");
        System.out.println(route + "\n...");
    }

    public void kalibreerSchijf(Arduino arduino) {
        //arduino = new Arduino("COM3", 9600);
        arduino.openConnection();
        if (aanHetKalibreren) {
            try {

                aanHetKalibreren = false;
                arduino.serialWrite('d');

                System.out.println("Schijf gekalibreerd\n...");

                Thread.sleep(1000);

            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            System.out.println("Schijf kalibreren\n...");

            aanHetKalibreren = true;
            arduino.serialWrite('k');
        }
        arduino.closeConnection();

    }

    public void voerBPPuit(ArrayList<Item> picks) {
        volgorde = null;
        BPP bpp = null;
        bpp = new BPP(picks);
        volgorde = bpp.getVolgorde();
        System.out.println("Doos volgorde bepaald:");
        System.out.println(volgorde + "\n...");
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

                if (r == route.size() - 2) {
                    Graphics2D g2d = (Graphics2D) g;
                    Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
                    g2d.setStroke(dashed);
                    g2d.drawLine(250 + startx * 100, 900 - 100 * starty, 250 + eindx * 100, 900 - eindy * 100);
                    g2d.setStroke(new BasicStroke(4));
                } else {
                    g.drawLine(250 + startx * 100, 900 - 100 * starty, 250 + eindx * 100, 900 - eindy * 100);
                }
                

                if (k == 0) {
                    g.setColor(Color.GREEN);
                } else if (k == route.size() - 2) {
                    g.setColor(Color.RED);
                }
                g.fillOval(startx * 100 + 243, 893 - starty * 100, 14, 14);
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

    public void resetRobots(Arduino arduino, Arduino arduino2) {
        arduino.serialWrite('h');

        try {
            Thread.sleep(5000);
        } catch (Exception e) {

        }
        arduino.serialWrite('z');

        arduino2.serialWrite((char) 49);

        kraanPositie = 0;
        doosPositie = 1;
    }

    public void beweegKraan(int next, Arduino arduino) {
        kraanPositie = route.get(next);

        String message = "";
        message += "c";

        message += items.get(route.get(next)).getLocatie().getCoord().getX();
        message += items.get(route.get(next)).getLocatie().getCoord().getY();
        System.out.println(message);

        arduino.serialWrite(message);

        String s;
        do {
            s = arduino.serialRead();
        } while (s.equals("") || s == null);

        System.out.print("inkomend bericht:   " + s);
//        DEZE CODE WACHT OP DE KNOP, IS VOOR TESTEN EVEN VERVANGEN DOOR EEN SLEEP

        //      try{
        //          Thread.sleep(2000);
        //      } catch(Exception e){
        //      }
    }

    public void tekenDoosPositie(Graphics g) {
        g.setColor(Color.GREEN);
        //g.fillRect(1000 + 100 * doosPositie, 920, 50, 50);
        
        g.drawRect(1000 + 100 * doosPositie, 500, 50, 400);
    }

    public void tekenKraanPositie(Graphics g) {
        g.setColor(Color.GREEN);
        g.drawRect(203 + 100 * items.get(kraanPositie).getLocatie().getCoord().getX(), 853 - 100 * items.get(kraanPositie).getLocatie().getCoord().getY(), 95, 95);
    }
    
    public void werkDoosInhoudBij(int next){
        doosInhoud[volgorde.get(next)] += items.get(route.get(next)).getGrootte();
        
        for(double d: doosInhoud){
            System.out.println("INHOUD: "+ d);
        }
        
    }

    public void tekenDoosInhoud(Graphics g) {
        g.setColor(Color.YELLOW);
        
        for(int i = 0; i < 6; i++){
        
        g.fillRect(1000 + 100 * i, (int) (800.0*(900.0 -  doosInhoud[i])),   50, (int) (800.0*doosInhoud[i]));

        }
    }

    public void draaiSchijf(int next, Arduino arduino) {
        char c = (char) (volgorde.get(next - 1) + 48);
        System.out.println(volgorde.get(next - 1));

        doosPositie = volgorde.get(next - 1);

        //arduino.openConnection();
        arduino.serialWrite(c);

        while (arduino.serialRead().equals("") || arduino.serialRead() == null) {
            //wait for incoming message
        }

        //arduino.closeConnection();
    }

    public static ArrayList<Item> maakBestellingAan(String naam, String adres1, String adres2, String land, ArrayList<Integer> besteldeItems) {
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

            int k = 1;
            for (int i : besteldeItems) {

                picks = voegToeAanPicks(i, picks);
                k++;
            }

            System.out.println("Totaal: " + k + " items\n...");

            sluitDatabaseConnectie();
            return picks;
        }
        return null;
    }

    private static ArrayList<Item> voegToeAanPicks(int besteldItem, ArrayList<Item> picks) {
        //int nextItemID = Integer.parseInt(line);

        for (Item item : items) {
            if (item.getLocatie().getID() == besteldItem) {
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
