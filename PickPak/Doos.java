package PickPak;

import java.util.ArrayList;

public class Doos {
    private static int aantalDozen = 0;
    private int doosID;
    //private static final int CAPACITEIT = 12;
    private double capaciteit;
    private ArrayList<Item> inhoud;

    public Doos() {
        inhoud = new ArrayList<>();

        aantalDozen++;
        doosID = aantalDozen;
        
    }
    
    public Doos(double capaciteit){
        this();
        
        //doosID = aantalDozen;
        //aantalDozen++;
        
        this.capaciteit = capaciteit;
        
    }
    
    public Doos(Item item, double capaciteit) {
        this();

        this.capaciteit = capaciteit;
        
        inhoud.add(item);
    }

    public static int getAantalDozen() {
        return aantalDozen;
    }
    
    
    
    public static void resetDozen(){
        
        aantalDozen = 0;
    }
    
    public double getRuimte() {
        return capaciteit - getInhoud();
    }
    
    public void resetInhoud(){
        inhoud = new ArrayList<Item>();
        //aantalDozen = 0;
    }

    public double getInhoud() {
        double inhoudSum = 0;

        for (Item item : inhoud) {
            inhoudSum += item.getGrootte();
        }

        return inhoudSum;
    }

    public int getDoosID() {
        return doosID;
    }

    public void aanvullen(Item item) {
        inhoud.add(item);
    }

    @Override
    public String toString() {
        return "Doos " + doosID + ": " + getInhoud() + "/" + capaciteit;
    }
}
