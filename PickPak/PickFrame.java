package PickPak;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import arduino.*;
import java.util.ArrayList;

public class PickFrame extends JFrame implements ActionListener {

    private int aantalBestellingen = 0;
    private boolean aanHetKalibreren = false;
    private boolean stop = false;
    private int picknr = 0;
    ArrayList<Item> bestelling;

    private PickPak pickpak;
    
    private GeavanceerdDialoog jdGeavanceerd;

    private JTextField jtfFile;
    private JLabel jlFile, jlCOMschijf, jlCOMkraan;
    private JButton jbbevestig, jbRefresh, jbConnect, jbKalibreer, jbTekenRoute, jbReset, jbStop, geavanceerd;

    private PortDropdownMenu pdmCOMschijf, pdmCOMkraan;

    private PickPanel panel;

    private Arduino arduino, arduino2;

    public PickFrame(PickPak pickpak) {
        setTitle("GUI");
        setSize(1920, 1080);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.pickpak = pickpak;

        jlCOMkraan = new JLabel("Kraan:");
        add(jlCOMkraan);

        pdmCOMkraan = new PortDropdownMenu();
        add(pdmCOMkraan);
        pdmCOMkraan.refreshMenu();

        jlCOMschijf = new JLabel("Schijf:");
        add(jlCOMschijf);

        pdmCOMschijf = new PortDropdownMenu();
        add(pdmCOMschijf);
        pdmCOMschijf.refreshMenu();

        jbConnect = new JButton("Connect");
        jbConnect.addActionListener(this);
        add(jbConnect);

        jbRefresh = new JButton("Refresh");
        jbRefresh.addActionListener(this);
        add(jbRefresh);

        add(new JLabel("     "));

        jbKalibreer = new JButton("Kalibreer");
        jbKalibreer.setEnabled(false);
        jbKalibreer.addActionListener(this);
        add(jbKalibreer);

        add(new JLabel("     "));

        jlFile = new JLabel("Bestelling: ");
        add(jlFile);

        jtfFile = new JTextField(10);
        add(jtfFile);

        jbTekenRoute = new JButton("Teken route");
        jbTekenRoute.addActionListener(this);
        add(jbTekenRoute);

        jbbevestig = new JButton("Start picken");
        jbbevestig.addActionListener(this);
        jbbevestig.setEnabled(false);
        add(jbbevestig);

        add(new JLabel("     "));

        jbReset = new JButton("Reset");
        jbReset.addActionListener(this);
        jbReset.setEnabled(false);
        add(jbReset);

        jbStop = new JButton("Stop");

        jbStop.addActionListener(this);

//        jbStop.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                stop = true;
//            }
//        });
        add(jbStop);
        
        geavanceerd = new JButton("Geavanceerd");
        geavanceerd.addActionListener(this);
        add(geavanceerd);

        panel = new PickPanel(pickpak);
        add(panel);

        if (pdmCOMschijf.getItemCount() == 0 || pdmCOMschijf.getItemCount() == 0) {
            jbConnect.setEnabled(false);
        }
        
        
        
        
        
        setVisible(true);
        
        
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jbbevestig) {
            jbTekenRoute.setEnabled(false);
            jbbevestig.setEnabled(false);
            jbReset.setEnabled(false);
            jbKalibreer.setEnabled(false);

            jtfFile.setText("");

            aantalBestellingen++;
            if (aantalBestellingen > 1) {
                reconnect();
            }

            pickBestelling();

            jbReset.setEnabled(true);
            jbKalibreer.setEnabled(true);

        } else if(e.getSource() == geavanceerd){
            jdGeavanceerd = new GeavanceerdDialoog(this);
            
            jdGeavanceerd.dispose();
        }
        
        else if (e.getSource() == jbStop) {
            System.out.println("STOP");
        } else if (e.getSource() == jbRefresh) {

            pdmCOMkraan.refreshMenu();
            pdmCOMschijf.refreshMenu();

            if (pdmCOMkraan.getItemCount() == 0 || pdmCOMschijf.getItemCount() == 0) {
                jbConnect.setEnabled(false);
            } else {
                jbConnect.setEnabled(true);
            }

        } else if (e.getSource() == jbConnect) {

            if (jbConnect.getText().equals("Connect")) {
                String kraanPort = (String) pdmCOMkraan.getSelectedItem();
                String schijfPort = (String) pdmCOMschijf.getSelectedItem();

                if (!kraanPort.equals(schijfPort)) {

                    arduino = new Arduino(kraanPort, 9600);
                    arduino2 = new Arduino(schijfPort, 9600);

                    if (arduino.openConnection() && arduino2.openConnection()) {
                        jbConnect.setText("Disconnect");
                        pdmCOMschijf.setEnabled(false);
                        pdmCOMkraan.setEnabled(false);
                        jbRefresh.setEnabled(false);
                        jbKalibreer.setEnabled(true);
                        jbTekenRoute.setEnabled(true);
                    }
                }

            } else {
                arduino.closeConnection();
                arduino2.closeConnection();

                jbConnect.setText("Connect");;

                pdmCOMschijf.setEnabled(true);
                pdmCOMkraan.setEnabled(true);
                jbRefresh.setEnabled(true);
                jbbevestig.setEnabled(false);
                jbKalibreer.setEnabled(false);
            }

        } else if (e.getSource() == jbKalibreer) {

            if (aanHetKalibreren) {
                jbKalibreer.setText("Kalibreer");

                jbTekenRoute.setEnabled(true);
                jbConnect.setEnabled(true);
                jbReset.setEnabled(true);

            } else {
                jbKalibreer.setText("Stop");

                jbbevestig.setEnabled(false);
                jbTekenRoute.setEnabled(false);
                jbConnect.setEnabled(false);
                jbReset.setEnabled(false);
            }

            pickpak.kalibreerSchijf(arduino2, aanHetKalibreren);
            aanHetKalibreren = !aanHetKalibreren;

        } else if (e.getSource() == jbTekenRoute) {
            try {

                bestelling = null;
                bestelling = pickpak.leesBestelling(jtfFile.getText());

                ArrayList<Integer> route = pickpak.voerTSPuit(bestelling);

                pickpak.voerBPPuit(route);

                if (jbConnect.getText().equals("Disconnect")) {
                    jbbevestig.setEnabled(true);
                }

                jbTekenRoute.setEnabled(false);

            } catch (Exception ex) {
                System.out.println("Bestand niet gevonden\n...");
            }
        } else if (e.getSource() == jbReset) {

            pickpak.resetRobots(arduino, arduino2);
            
            reconnect();

            panel.paintImmediately(0, 0, 1920, 1080);

            jbTekenRoute.setEnabled(true);
        }
        repaint();
    }

    private void reconnect() {
        try {
            arduino.openConnection();
            arduino2.openConnection();
        } catch (Exception ex) {

        }

    }

    public void pickBestelling() {
        for (int it = 1; it < pickpak.route.size() - 1; it++) {

            if (stop) {
                stop = false;
                break;
            }

            pickpak.draaiSchijf(it, arduino2);

            char s = '.';
            do {
                try {
                    s = arduino2.serialRead().charAt(0);
                } catch (Exception ex) {

                }
            } while (s != 'd'); // schijf draaien

            System.out.println(s);
            //while(){
            // wacht op signaal
            // }
            panel.paintImmediately(0, 0, 1920, 1080);

            pickpak.beweegKraan(it, arduino);

            panel.paintImmediately(0, 0, 1920, 1080);

            arduino.serialWrite('p'); //push

            pickpak.setPush(true);

            panel.paintImmediately(0, 0, 1920, 1080);

            char t = '.';
            do {
                try {
                    t = arduino2.serialRead().charAt(0);
                } catch (Exception ex) {

                }
            } while (t != 'p'); //sensor

            try {
                Thread.sleep(1000);
            } catch (Exception ex) {
            }

            System.out.println(t);

            pickpak.setPush(false);

            panel.paintImmediately(0, 0, 1920, 1080);

            pickpak.werkDoosInhoudBij(it);

            panel.paintImmediately(0, 0, 1920, 1080);
        }
    }
}
