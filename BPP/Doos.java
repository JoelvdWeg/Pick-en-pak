package BPP;

public class Doos {
    private final static int inhoud = 12;
    private int gevuld;

    public Doos(int vullen) {
        gevuld = vullen;
    }

    public Doos() {
        this(0);
    }

    public String toString() {
        return "Doos (" + inhoud + "): met " + gevuld + " gevuld.";
    }

    public int getInhoud() {
        return inhoud;
    }

    public int getGevuld() {
        return gevuld;
    }

    public int getOver() {
        return getInhoud() - getGevuld();
    }

    public void aanvullen(int gevuld) {
        this.gevuld += gevuld;
    }
}
