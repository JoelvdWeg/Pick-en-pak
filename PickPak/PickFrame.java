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
    ArrayList<Item> bestelling;

    private PickPak pickpak;

    private JTextField jtfFile;
    private JLabel jlFile;
    private JButton jbbevestig, jbRefresh, jbConnect, jbKalibreer, jbTekenRoute, jbReset;

    private PortDropdownMenu pdmCOM;

    private PickPanel panel;

    private Arduino arduino, arduino2;

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

        jlFile = new JLabel("Bestelling: ");
        add(jlFile);

        jtfFile = new JTextField(10);
        add(jtfFile);

        jbbevestig = new JButton("Start picken");
        jbbevestig.addActionListener(this);
        jbbevestig.setEnabled(false);
        add(jbbevestig);

        jbTekenRoute = new JButton("Teken route");
        jbTekenRoute.addActionListener(this);
        jbTekenRoute.setEnabled(false);
        add(jbTekenRoute);

        jbReset = new JButton("Reset");
        jbReset.addActionListener(this);
        jbReset.setEnabled(false);
        add(jbReset);

        panel = new PickPanel(pickpak);
        add(panel);

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

            pickBestelling();

            jbReset.setEnabled(true);
            jbKalibreer.setEnabled(true);

        } else if (e.getSource() == jbRefresh) {

            pdmCOM.refreshMenu();

        } else if (e.getSource() == jbConnect) {

            if (jbConnect.getText().equals("Connect")) {
                arduino = new Arduino("COM6", 9600);
                arduino2 = new Arduino("COM3", 9600);

                if (arduino.openConnection() && arduino2.openConnection()) {
                    jbConnect.setText("Disconnect");
                    pdmCOM.setEnabled(false);
                    jbRefresh.setEnabled(false);
                    jbKalibreer.setEnabled(true);
                    jbTekenRoute.setEnabled(true);
                }

            } else {
                arduino.closeConnection();
                arduino2.closeConnection();

                jbConnect.setText("Connect");;

                pdmCOM.setEnabled(true);
                jbRefresh.setEnabled(true);
                jbTekenRoute.setEnabled(false);
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
                aantalBestellingen++;
                if (aantalBestellingen > 1) {
                    reconnect();
                }

                bestelling = null;
                bestelling = pickpak.leesBestelling(jtfFile.getText());

                ArrayList<Integer> route = pickpak.voerTSPuit(bestelling);

                pickpak.voerBPPuit(route);

                jbbevestig.setEnabled(true);
                jbTekenRoute.setEnabled(false);

            } catch (Exception ex) {
                System.out.println("Bestand niet gevonden\n...");
            }
        } else if (e.getSource() == jbReset) {

            pickpak.resetRobots(arduino, arduino2);

            jbTekenRoute.setEnabled(true);

            panel.paintImmediately(0, 0, 1920, 1080);
        }
        repaint();
    }

    private void reconnect() {
        arduino.openConnection();
        arduino2.openConnection();
    }

    public void pickBestelling() {
        for (int it = 1; it < pickpak.route.size() - 1; it++) {

            pickpak.draaiSchijf(it, arduino2);

            panel.paintImmediately(0, 0, 1920, 1080);

            pickpak.beweegKraan(it, arduino);

            panel.paintImmediately(0, 0, 1920, 1080);

            pickpak.setPush(true);

            panel.paintImmediately(0, 0, 1920, 1080);

            arduino.serialWrite('p');

            pickpak.setPush(false);

            panel.paintImmediately(0, 0, 1920, 1080);

            while (arduino.serialRead().equals("") || arduino.serialRead() == null) {
                //wacht tot klaar met pushen
            }

            while (arduino2.serialRead().equals("") || arduino2.serialRead() == null) {
                //wacht op knop
            }

            pickpak.werkDoosInhoudBij(it);

            panel.paintImmediately(0, 0, 1920, 1080);
        }
    }
}
