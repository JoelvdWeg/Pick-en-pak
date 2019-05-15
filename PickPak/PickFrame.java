package PickPak;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import arduino.*;
import java.io.File;
import java.util.ArrayList;
import javax.swing.text.Document;
import javax.xml.parsers.*;
import org.w3c.dom.*;

public class PickFrame extends JFrame implements ActionListener {

    private int status = 0;
    ArrayList<Item> bestelling;
    private PickPak pickpak;
    private JTextField jtfx, jtfy, jtfFile;
    private JLabel jlx, jly, jlFile;
    private JButton jbbevestig;
    private JLabel jlStockItemID;
    private JLabel jlStockItemName;
    public Arduino arduino;
    
    ArrayList<Integer> route = null;

    private boolean aanHetKalibreren = false;

    private PortDropdownMenu pdmCOM;
    private JButton jbRefresh;
    private JButton jbConnect, jbKalibreer, jbTekenTSP;

    public PickFrame(PickPak pickpak) {
        setTitle("GUI");
        setSize(1920, 1080);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.pickpak = pickpak;

        pdmCOM = new PortDropdownMenu();
        add(pdmCOM);
        pdmCOM.refreshMenu();

        jbRefresh = new JButton("Refresh");
        jbRefresh.addActionListener(this);
        add(jbRefresh);

        jbConnect = new JButton("Connect");
        jbConnect.addActionListener(this);
        add(jbConnect);

        jbKalibreer = new JButton("Kalibreer");
        jbKalibreer.setEnabled(false);
        jbKalibreer.addActionListener(this);
        add(jbKalibreer);

        jlx = new JLabel("x-as");
        jlx.setEnabled(false);
        //add(jlx);

        jtfx = new JTextField(4);
        jtfx.setEnabled(false);
        //add(jtfx);

        jly = new JLabel("y-as");
        jly.setEnabled(false);
        //add(jly);

        jtfy = new JTextField(4);
        jtfy.setEnabled(false);
        //add(jtfy);

        jlFile = new JLabel("Bestelling: ");
        add(jlFile);

        jtfFile = new JTextField(10);
        add(jtfFile);

        jbbevestig = new JButton("Bevestig");
        jbbevestig.addActionListener(this);
        jbbevestig.setEnabled(false);
        add(jbbevestig);

        jbTekenTSP = new JButton("Teken TSP");
        jbTekenTSP.addActionListener(this);
        add(jbTekenTSP);

        setVisible(true);
        PickPanel panel = new PickPanel(pickpak);
        add(panel);

    }

    @Override
    public void actionPerformed(ActionEvent e) {    
        if (e.getSource() == jbbevestig) {
            
            //if (status == 0) {

                //pickpak.maakRouteVoorGUI(bestelling);
                //repaint();
                pickpak.voerTSPuit(arduino);
                //pickpak.voerBPPuit(route, arduino);
                //status = 1;
                jtfFile.setText("");

            //} else if (status == 1) {

                //pickpak.voerBPPuit(bestelling, arduino);
                //
            //}
        } else if (e.getSource() == jbRefresh) {
            pdmCOM.refreshMenu();
        } else if (e.getSource() == jbConnect) {
            if (jbConnect.getText().equals("Connect")) {
                arduino = new Arduino("COM5", 9600);
                if (arduino.openConnection()) {
                    jbConnect.setText("Disconnect");
                    pdmCOM.setEnabled(false);
                    jbRefresh.setEnabled(false);
                    jbKalibreer.setEnabled(true);
                    jbbevestig.setEnabled(true);

                    //jlx.setEnabled(true);
                    //jtfx.setEnabled(true);
                    //jly.setEnabled(true);
                    //jtfy.setEnabled(true);
                    //jbbevestig.setEnabled(true);
                }
            } else {
                arduino.closeConnection();
                jbConnect.setText("Connect");;
                pdmCOM.setEnabled(true);
                jbRefresh.setEnabled(true);

                jlx.setEnabled(false);
                jtfx.setEnabled(false);
                jly.setEnabled(false);
                jtfy.setEnabled(false);
                //jbbevestig.setEnabled(false);
                jbKalibreer.setEnabled(false);
            }
        } else if (e.getSource() == jbKalibreer) {
            pickpak.kalibreerSchijf();
        } else if (e.getSource() == jbTekenTSP) {
            bestelling = pickpak.leesBestelling(jtfFile.getText());
            pickpak.maakRouteVoorGUI(bestelling);
            pickpak.voerBPPuit(bestelling);
            //System.out.println(pickpak.maakRouteVoorGUI(bestelling));
        }
        repaint();
    }

    
}
