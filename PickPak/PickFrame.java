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

    private int BPPalgoritme;

    private JTextField jtfFile;
    private JLabel jlFile;
    private JButton jbbevestig, jbStop, geavanceerd;

    private PickPanel panel;

    private Arduino arduinoKraan, arduinoSchijf;

    public PickFrame(PickPak pickpak) {
        setTitle("GUI");
        setSize(1920, 1080);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.pickpak = pickpak;

        jlFile = new JLabel("Bestelling: ");
        add(jlFile);

        jtfFile = new JTextField(10);
        jtfFile.setText("bestelling.xml");
        add(jtfFile);

        jbbevestig = new JButton("Start");
        jbbevestig.addActionListener(this);
        add(jbbevestig);

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

        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jbbevestig) {
            System.out.println("Boven thread\n..");
            new Thread() {
                public void run() {
                    System.out.println("Binnen thread\n..");
                    jbbevestig.setEnabled(false);

                    aantalBestellingen++;
                    if (aantalBestellingen > 1) { // aanpassen!

                        pickpak.resetRobots(arduinoKraan, arduinoSchijf);

                        //pickpak.resetRobots(arduinoKraan, arduinoSchijf);
                        //reconnect();
                        panel.paintImmediately(0, 0, 1920, 1080);
                    }

                    tekenRoute(jtfFile.getText());

                    pickBestelling();
                }
            }.start();

        } else if (e.getSource() == geavanceerd) {
            jdGeavanceerd = new GeavanceerdDialoog(this, pickpak);
            BPPalgoritme = jdGeavanceerd.getBPPalgoritme();
            arduinoKraan = jdGeavanceerd.getArduinoKraan();
            arduinoSchijf = jdGeavanceerd.getArduinoSchijf();
            jdGeavanceerd.dispose();
        } else if (e.getSource() == jbStop) { // aanpassen!
            System.out.println("STOP");

        }

        repaint();
    }

    private void tekenRoute(String f) {
        try {
            bestelling = null;
            bestelling = pickpak.leesBestelling("bestelling.xml");

            if (bestelling == null) {
                System.out.println("hoi");
            } else {
                ArrayList<Integer> route = pickpak.voerTSPuit(bestelling);

                pickpak.voerBPPuit(route, BPPalgoritme);
            }

        } catch (Exception ex) {
            System.out.println("Bestand niet gevonden\n...");
        }
    }

    private void reconnect() {
        try {
            arduinoKraan.openConnection();
            arduinoSchijf.openConnection();
        } catch (Exception ex) {

        }

    }

    public void pickBestelling() {
        for (int it = 1; it < pickpak.route.size() - 1; it++) {

            if (stop) {
                stop = false;
                break;
            }

            pickpak.draaiSchijf(it, arduinoSchijf);

            char s = '.';
            do {
                try {
                    s = arduinoSchijf.serialRead().charAt(0);
                } catch (Exception ex) {

                }
            } while (s != 'd'); // schijf draaien

            System.out.println(s);
            //while(){
            // wacht op signaal
            // }
            panel.paintImmediately(0, 0, 1920, 1080);

            pickpak.beweegKraan(it, arduinoKraan);

            panel.paintImmediately(0, 0, 1920, 1080);

            arduinoKraan.serialWrite('p'); //push

            pickpak.setPush(true);

            panel.paintImmediately(0, 0, 1920, 1080);

            char t = '.';
            do {
                try {
                    t = arduinoSchijf.serialRead().charAt(0);
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
        arduinoKraan.serialWrite("c00");
        arduinoSchijf.serialWrite("c1");

        jbbevestig.setEnabled(true); //hey x

    }
}
