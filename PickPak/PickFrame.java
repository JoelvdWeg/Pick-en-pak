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
import javax.swing.SwingUtilities;
import javax.swing.text.Document;
import javax.xml.parsers.*;
import org.w3c.dom.*;

public class PickFrame extends JFrame implements ActionListener {

    private int status = 0;
    private int aantalBestellingen = 0;
    ArrayList<Item> bestelling;
    private PickPak pickpak;
    private JTextField jtfx, jtfy, jtfFile;
    private JLabel jlx, jly, jlFile;
    private JButton jbbevestig;
    private JLabel jlStockItemID;
    private JLabel jlStockItemName;
    public Arduino arduino, arduino2;

    //ArrayList<Integer> route = null;
    private boolean aanHetKalibreren = false;

    private PortDropdownMenu pdmCOM;
    private JButton jbRefresh;
    private JButton jbConnect, jbKalibreer, jbTekenTSP;
    private PickPanel panel;

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
        panel = new PickPanel(pickpak);
        add(panel);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jbbevestig) {

            //if (status == 0) {
            //pickpak.maakRouteVoorGUI(bestelling);
            //repaint();
            jtfFile.setText("");
            voerTSPuit();
            
            arduino.closeConnection();
            arduino2.closeConnection();
            
            //pickpak.voerBPPuit(route, arduino);
            //status = 1;
            

            //} else if (status == 1) {
            //pickpak.voerBPPuit(bestelling, arduino);
            //
            //}
        } else if (e.getSource() == jbRefresh) {
            pdmCOM.refreshMenu();
        } else if (e.getSource() == jbConnect) {
            if (jbConnect.getText().equals("Connect")) {
                arduino = new Arduino("COM5", 9600);
                arduino2 = new Arduino("COM3", 9600);
                if (arduino.openConnection() && arduino2.openConnection()) {
                    jbConnect.setText("Disconnect");
                    pdmCOM.setEnabled(false);
                    jbRefresh.setEnabled(false);
                    jbKalibreer.setEnabled(true);
                    

                    //jlx.setEnabled(true);
                    //jtfx.setEnabled(true);
                    //jly.setEnabled(true);
                    //jtfy.setEnabled(true);
                    //jbbevestig.setEnabled(true);
                }
            } else {
                arduino.closeConnection();
                arduino2.closeConnection();
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
            pickpak.kalibreerSchijf(arduino2);
        } else if (e.getSource() == jbTekenTSP) {
            aantalBestellingen++;
            if(aantalBestellingen > 1){
                reconnect();
            }
            bestelling = null;
            bestelling = pickpak.leesBestelling(jtfFile.getText());
            pickpak.maakRouteVoorGUI(bestelling);
            pickpak.voerBPPuit(bestelling);
            
            jbbevestig.setEnabled(true);
            jbTekenTSP.setEnabled(false);
            //System.out.println(pickpak.maakRouteVoorGUI(bestelling));
        }
        repaint();
    }
    
    private void reconnect(){
        arduino.openConnection();
        arduino2.openConnection();
    }

    public void voerTSPuit() {
        System.out.println("route: " + pickpak.route);

        for (int it = 1; it < pickpak.route.size() - 1; it++) {

            pickpak.draaiSchijf(it, arduino2);
            
            panel.paintImmediately(0, 0, 1920, 1080);
            
            pickpak.beweegKraan(it, arduino);

            panel.paintImmediately(0, 0, 1920, 1080);
            
            arduino.serialWrite('p');
            
            while(arduino.serialRead().equals("") || arduino2.serialRead() == null){
                //wacht tot klaar met pushen
            }
            
            while (arduino2.serialRead().equals("") || arduino2.serialRead() == null) {
                //wacht op knop
            }
        }
        
        pickpak.resetRobots(arduino, arduino2);
        
        //arduino2.serialWrite((char) 49);
        //arduino.serialWrite('h');
        
        //arduino.closeConnection();
        //arduino2.closeConnection();
        panel.paintImmediately(0,0,1920, 1080);
    }

}
