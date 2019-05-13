package PickPak;


public class Lijn {
    public Coordinate start;
    public Coordinate eind;

    public Lijn(Coordinate start, Coordinate eind) {
        this.start = start;
        this.eind = eind;
    }

    public Coordinate getStart() {
        return start;
    }

    public void setStart(Coordinate start) {
        this.start = start;
    }

    public Coordinate getEind() {
        return eind;
    }

    public void setEind(Coordinate eind) {
        this.eind = eind;
    }
    
    
}
