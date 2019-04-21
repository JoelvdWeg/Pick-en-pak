import java.util.ArrayList;

public class Doos {
    private static int aantal = 1;
    private int doosID;
    private static final int CAPACITEIT = 12;
    private ArrayList<Item> inhoud;

    public Doos() {
        inhoud = new ArrayList<>();

        doosID = aantal;
        aantal++;
    }

    public Doos(Item item) {
        this();

        inhoud.add(item);
    }

    public int getRuimte() {
        return CAPACITEIT - getInhoud();
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
        return "Doos " + doosID + ": " + getInhoud() + "/" + CAPACITEIT;
    }
}
