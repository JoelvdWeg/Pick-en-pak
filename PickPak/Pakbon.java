package PickPak;

import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

public class Pakbon {

    private int id;
    private String naam;
    private String adres1;
    private String adres2;
    private String land;
    public ArrayList<Item> items = new ArrayList<>();
    public int[] aantallen = new int[25];
    public static String newline = System.getProperty("line.separator");

    
    public Pakbon(int id, String naam, String adres1, String adres2, String land) {
        this.id = id;
        this.naam = naam;
        this.adres1 = adres1;
        this.adres2 = adres2;
        this.land = land;
        
        for(int i = 0; i < 25; i++){
            aantallen[i] = 0;
        }
    }

    public void maakPakbonBestand() {
        String fileName = "pakbonnen/" + naam + "_" + id + ".txt";

        try {

            FileWriter fw = new FileWriter(fileName);
            fw.write(toString());
            fw.close();
            
            Thread.sleep(2000);
            
            System.out.println(toString());
            Desktop.getDesktop().open(new File(fileName));
        } catch (Exception ex) {
            System.err.println("Kon het bestand niet openen\n...");
            //System.out.println(ex);
        }
    }

    public String toString() {

        String s = "";
        s += "PAKBON" + newline;
        s += "----------------------------" + newline;
        s += naam + newline;
        s += adres1 + newline;
        s += adres2 + newline;
        s += land + newline;
        s += "----------------------------" + newline + newline;
        s += "Producten:" + newline;

        for (Item item : items) {
            
            
            s += item + "     aantal: " + aantallen[item.getID()] +  "    Totaalprijs:    €" + item.getPrijs()*aantallen[item.getID()] +"\n";
        }

        s += "----------------------------" + newline + newline;

        return s;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getAdres1() {
        return adres1;
    }

    public void setAdres1(String adres1) {
        this.adres1 = adres1;
    }

    public String getAdres2() {
        return adres2;
    }

    public void setAdres2(String adres2) {
        this.adres2 = adres2;
    }

    public String getLand() {
        return land;
    }

    public void setLand(String land) {
        this.land = land;
    }

    public ArrayList<Item> getItems() {
        return items;
    }
    
    public int getSize(){
        return items.size();
    }

    public void voegItemToe(Item item) {
        aantallen[item.getID()]++;
        items.add(item);
        
       
        
    }
    
    public int[] getAantallen(){
        return aantallen;
    }

//    public String toString() {
//        String s = "";
//        s += "PAKBON\n\n";
//        s += "----------------------------\n";
//        s += naam + "\n";
//        s += adres1 + "\n";
//        s += adres2 + "\n";
//        s += land + "\n";
//        s += "----------------------------\n\n";
//        s += "Producten:\n";
//
//        for (Item item : items) {
//            s += item + "\n";
//        }
//
//        s += "----------------------------\n\n";
//
//        return s;
//    }
}
