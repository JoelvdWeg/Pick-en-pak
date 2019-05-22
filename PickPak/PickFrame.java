package PickPak;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import arduino.*;
import java.util.ArrayList;

public class PickFrame extends JFrame implements ActionListener {

    private int aantalBestellingen = 0;
    private boolean aanHetKalibreren = false;
    private boolean stop = false;
    private int picknr = 0;
    ArrayList<Item> bestelling;

    private PickPak pickpak;
    
    private static final class Lock{}
    private final Object lock = new Lock();

    private Thread t;

    public static boolean running = true;

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

        
        t = new Thread() {
            @Override
            public void run() {
                jbbevestig.setEnabled(false);

                    aantalBestellingen++;
                    if (aantalBestellingen > 1) { // aanpassen!

                        pickpak.resetRobots();

                        panel.paintImmediately(0, 0, 1920, 1080);
                    }

                    tekenRoute(jtfFile.getText());

                    pickBestelling();

                    return;
            }
        };
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jbbevestig) {
            if (checkRobotConnection()) {
                t.start();
            }

        } else if (e.getSource() == geavanceerd) {
            jdGeavanceerd = new GeavanceerdDialoog(this, pickpak);
            BPPalgoritme = jdGeavanceerd.getBPPalgoritme();
            arduinoKraan = jdGeavanceerd.getArduinoKraan();
            arduinoSchijf = jdGeavanceerd.getArduinoSchijf();
            jdGeavanceerd.dispose();

        } else if (e.getSource() == jbStop) {
            if (jbStop.getText().equals("Stop")) {
                jbStop.setText("Hervatten");

                synchronized (lock) {
                    try {
                        t.wait();
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                }

            } else if (jbStop.getText().equals("Hervatten")) {
                jbStop.setText("Stop");

                synchronized (lock) {
                    try {
                        t.notify();
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                }

            }
            arduinoKraan.serialWrite('f');
            running = !running;
        }

        repaint();
    }

    private void tekenRoute(String f) {
        try {
            bestelling = null;
            bestelling = pickpak.leesBestelling(f);

            if (bestelling == null) {
                System.out.println("Bestelling is null\n...");
            } else {
                ArrayList<Integer> route = pickpak.voerTSPuit(bestelling);

                pickpak.voerBPPuit(route, BPPalgoritme);
            }

        } catch (Exception ex) {
            System.out.println("Er ging iets mis\n...");
        }
    }

    private boolean checkRobotConnection() {
        try {
            arduinoKraan.getSerialPort();
            arduinoSchijf.getSerialPort();
            return true;
        }
        catch (NullPointerException npe) {
            JOptionPane.showMessageDialog(this, "Niet verbonden met de pick- of inpakrobot.");
            return false;
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
        if (pickpak.route == null) {
            return;
        } else {

            for (int it = 1; it < pickpak.route.size() - 1; it++) {
                if (running) {

                    pickpak.draaiSchijf(it, arduinoSchijf);

                    char s = '.';
                    do {
                        try {
                            s = arduinoSchijf.serialRead().charAt(0);
                        } catch (Exception ex) {

                        }
                    } while (s != 'd'); // schijf draaien

                    //System.out.println(s);
                    //while(){
                    // wacht op signaal
                    // }
                    panel.paintImmediately(0, 0, 1920, 1080);

                    pickpak.beweegKraan(it, arduinoKraan);

                    System.out.println("KRAAN BEWOGEN\n...");

                    panel.paintImmediately(0, 0, 1920, 1080);

                    arduinoKraan.serialWrite('p'); //push

                    System.out.println("GEPUSHT\n...");

                    pickpak.setPush(true);

                    panel.paintImmediately(0, 0, 1920, 1080);

                    char t = '.';
                    do {
                        try {
                            t = arduinoSchijf.serialRead().charAt(0);
                        } catch (Exception ex) {

                        }
                    } while (t != 'p'); //sensor

                    //try {
                    //    Thread.sleep(1000);
                    //} catch (Exception ex) {
                    //}
                    System.out.println(t);

                    pickpak.setPush(false);

                    panel.paintImmediately(0, 0, 1920, 1080);

                    pickpak.werkDoosInhoudBij(it);

                    panel.paintImmediately(0, 0, 1920, 1080);
                }
            }
            arduinoKraan.serialWrite("c00");
            arduinoSchijf.serialWrite("c1");

            jbbevestig.setEnabled(true);
        }
    }
}
