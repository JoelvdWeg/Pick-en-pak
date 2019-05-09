import java.util.ArrayList;

public class Doos {
    private static int aantalDozen = 1;
    private int doosID;
    //private static final int CAPACITEIT = 12;
    private int capaciteit;
    private ArrayList<Item> inhoud;

    public Doos() {
        inhoud = new ArrayList<>();

        doosID = aantalDozen;
        aantalDozen++;
    }
    
    public Doos(int capaciteit){
        this();
        
        //doosID = aantalDozen;
        //aantalDozen++;
        
        this.capaciteit = capaciteit;
        
    }
    
    public Doos(Item item, int capaciteit) {
        this();

        this.capaciteit = capaciteit;
        
        inhoud.add(item);
    }
    
    public int getRuimte() {
        return capaciteit - getInhoud();
    }

    public int getInhoud() {
        int inhoudSum = 0;

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
