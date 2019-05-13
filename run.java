import arduino.Arduino;
import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;
import java.sql.*;

public class run {

    public static ArrayList<Item> items;
    private static ArrayList<Item> picks;
    private static ArrayList<Integer> volgorde;
    private static ArrayList<Integer> route;
    private static Connection connection;
    private static Pakbon pakbon;
    public static ArrayList<Lijn> lijnen = new ArrayList<>();

    public static void main(String[] args) {
       PickFrame pf = new PickFrame(1200, 800);
        
        if (maakDatabaseConnectie()) {
            haalItemsOp();

            maakBestellingAan("Lukas van Elten", "Molenmakerslaan 58", "3781DD Voorthuizen", "NEDERLAND", "newfile.txt");

            try {
                connection.close();
                System.out.println("Databaseconnectie succesvol gesloten\n...");
            } catch (Exception e) {
                System.out.println("Databaseconnectie kon niet worden gesloten\n...");
            }

            TSP tsp = new TSP(picks);
            route = tsp.getBestRoute();
            System.out.println("Route bepaald:");
            System.out.println(route + "\n...");
            
            maakRoute();

            BPP bpp = new BPP(picks);
            volgorde = bpp.getVolgorde();
            System.out.println("Doos volgorde bepaald:");
            System.out.println(volgorde + "\n...");

            System.out.println("\n\n" + pakbon + "\n\n");

            draaiSchijf(volgorde);
        }
    }
    
    public static void maakRoute(){
        for(int i = 0; i < route.size()-1; i++){
            //new Lijn(new Coordinate(Integer.parseInt(jtfX.getText()),Integer.parseInt(jtfY.getText())),new Coordinate(Integer.parseInt(jtfX2.getText()),Integer.parseInt(jtfY2.getText())))
            
            Coordinate c1,c2;
            c1 = items.get(route.get(i)).getLocatie().getCoord();
            c2 = items.get(route.get(i+1)).getLocatie().getCoord();
            
                    
            lijnen.add(new Lijn(c1,c2));
        }
    }
    
    public static void voegLijnToe(Lijn lijn){
        lijnen.add(lijn);
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

    private static void maakBestellingAan(String naam, String adres1, String adres2, String land, String f) {
        picks = new ArrayList<>();

        try {
            Statement pakbonIDstatement = connection.createStatement();
            ResultSet pakbonIDresult = pakbonIDstatement.executeQuery("SELECT MAX(id) FROM bestelling");
            pakbonIDresult.next();
            int newPakbonID = pakbonIDresult.getInt(1) + 1;
            pakbonIDresult.close();

            PreparedStatement newPakbon = connection.prepareStatement("INSERT INTO bestelling VALUES(?,?,?,?,?,?)");
            newPakbon.setInt(1, newPakbonID);
            newPakbon.setString(2, naam);
            newPakbon.setString(3, adres1);
            newPakbon.setString(4, adres2);
            newPakbon.setString(5, land);
            newPakbon.setInt(6, newPakbonID);
            newPakbon.executeUpdate();

            System.out.println("Bestelling toegevoegd aan database\n...");

            pakbon = new Pakbon(newPakbonID);
            pakbon.setNaam(naam);
            pakbon.setAdres1(adres1);
            pakbon.setAdres2(adres2);
            pakbon.setLand(land);
        } catch (Exception e) {
            System.out.println("Kon geen bestelling toevoegen aan de database\n...");
        }

        try {
            FileReader file = new FileReader(f);
            Scanner parser = new Scanner(file);
            int k = 1;
            while (parser.hasNextLine()) {
                String nextLine = parser.nextLine();
                voegToeAanPakbon(nextLine);
                System.out.println("Totaal: " + k + " items\n...");
                k++;
            }
        } catch (FileNotFoundException fe) {
            System.out.println("Kon geen pakbon printen\n...");
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

    private static void haalItemsOp() {
        items = new ArrayList<>();
        
        try{
            PreparedStatement itemsStatement = connection.prepareStatement("SELECT StockItemID, StockItemName, TypicalWeightPerUnit FROM stockitems WHERE StockItemID < 26");
            ResultSet itemsResultSet = itemsStatement.executeQuery();
            int i= 0 ,j = 0;
            while(itemsResultSet.next()){
                items.add(new Item(new Locatie(itemsResultSet.getInt(1)-1,new Coordinate(i,j)),itemsResultSet.getDouble(3),itemsResultSet.getInt(1)-1,itemsResultSet.getString(2)));
                if(i != 4){
                    i++;
                } else {
                    j++;
                    i = 0;
                }         
            }
            items.add(new Item(new Locatie(25, new Coordinate(4, 0)), 0, 0, "LOSPUNT"));
            System.out.println("Items succesvol opgehaald uit database\n..");
        }catch(Exception e){
            System.out.println("Kon items niet ophalen uit de database\n...");
        }
        
        
//        try {
//            FileReader file = new FileReader("fruit.txt");
//            Scanner parser = new Scanner(file);
//            int k = 0;
//            for (int i = 0; i < 5; i++) {
//                for (int j = 0; j < 5; j++) {
//                    String nextLine = parser.nextLine();
//                    items.add(new Item(new Locatie(k, new Coordinate(i, j)), Integer.parseInt(nextLine.split(" ")[2]), Integer.parseInt(nextLine.split(" ")[0]), nextLine.split(" ")[1]));
//                    k++;
//                }
//            }
//            items.add(new Item(new Locatie(k, new Coordinate(4, 0)), 0, 0, "LOSPUNT"));
//            System.out.println("Items opgehaald\n...");
//        } catch (FileNotFoundException fe) {
//            System.out.println("Kon items niet ophalen\n...");
//        }

    }
}
