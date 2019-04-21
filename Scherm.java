import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Scherm extends JFrame implements ActionListener {
    private PickPak pickPak;

    public Scherm(PickPak pickPak) {
        this.pickPak = pickPak;


    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
