package PickPak;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import arduino.*;

import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class PickFrame extends JFrame implements ActionListener {

    private int aantalBestellingen = 0;
    private boolean aanHetKalibreren = false;
    private boolean stop = false;
    private int picknr = 0;
    private Bestelling bestelling;

    private PickPak pickpak;

    private JTable tabel;

    private static final class Lock {
    }
    private final Object lock = new Lock();

    private Thread t;

    public static boolean running = true;

    private GeavanceerdDialoog jdGeavanceerd;

    private int BPPalgoritme;

    private JTextField jtfFile;
    private JLabel jlFile;
    private JButton jbbevestig, jbStop, geavanceerd;

    private GridPanel gridPanel;
    private DozenPanel dozenPanel;

    private JPanel p;

    private Arduino arduinoKraan, arduinoSchijf;

    public PickFrame(PickPak pickpak) {
        setTitle("GUI");
        setSize(1920, 1080);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        p = new JPanel(new GridBagLayout());

        GridBagConstraints d = new GridBagConstraints();
        GridBagConstraints c = new GridBagConstraints();

        this.pickpak = pickpak;

        jlFile = new JLabel("Bestelling: ");
        add(jlFile);

        jtfFile = new JTextField(10);
        jtfFile.setText("bestelling.xml");
        add(jtfFile);

        jbbevestig = new JButton("Start");
        jbbevestig.addActionListener(this);
        add(jbbevestig);
        jbbevestig.setEnabled(true);

        jbStop = new JButton("Afbreken");
        jbStop.setEnabled(false);
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
        
        Object[][] array = new Object[10][7];
        
        String[] columnNames = {"Te picken","ID",
            "Product",
            "Grootte",
            "Coördinaten",
            "Doosnr.",
            "Voorraad"
        };

        TableModel tableModel = new DefaultTableModel(array, columnNames);
        tabel = new JTable(tableModel);

        add(tabel);
        add(new JScrollPane(tabel));
        tabel.setPreferredScrollableViewportSize(tabel.getPreferredSize());
        tabel.setFillsViewportHeight(true);

        //tabel.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
        tabel.getColumnModel().getColumn(0).setMinWidth(40);
        tabel.getColumnModel().getColumn(1).setMinWidth(40);
        tabel.getColumnModel().getColumn(2).setMinWidth(100);
        tabel.getColumnModel().getColumn(3).setMinWidth(100);
        tabel.getColumnModel().getColumn(4).setMinWidth(80);
        tabel.getColumnModel().getColumn(5).setMinWidth(50);
        tabel.getColumnModel().getColumn(6).setMinWidth(40);
        
        
        //tabel.setColumnModel(colModel);

        gridPanel = new GridPanel(pickpak);

        d.gridx = 0;
        d.gridy = 0;

        p.add(gridPanel, d);

        dozenPanel = new DozenPanel(pickpak);

        c.gridx = 300;
        c.gridy = 0;

        p.add(dozenPanel, c);

        add(p, BorderLayout.SOUTH);

        setVisible(true);

        t = new Thread() {

            @Override
            public void run() {

                jbbevestig.setEnabled(false);

                aantalBestellingen++;
                if (aantalBestellingen > 1) { // aanpassen!

                    pickpak.resetRobots();

                    p.paintImmediately(0, 0, 1920, 1080);

                }

                tekenRoute(jtfFile.getText());
                
                tabel.setModel(pickpak.maakTabelModel(0)); 

                pickBestelling();
                
                pickpak.maakPakbonnen(bestelling);

                return;
            }
        };

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jbbevestig) {
            if (checkRobotConnection()) {
                if (!jtfFile.getText().equals("")) {
                    if (!pickpak.checkBestrelling(jtfFile.getText())) {
                        JOptionPane.showMessageDialog(null, "Kan bestand niet lezen");
                    } else {
                        t.start();
                        jbStop.setEnabled(true);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Geef een bestelling op.");
                }

            } else {
                JOptionPane.showMessageDialog(this, "Niet verbonden met de pick- of inpakrobot.");
            }

        } else if (e.getSource() == geavanceerd) {
            if (jdGeavanceerd == null) {
                jdGeavanceerd = new GeavanceerdDialoog(this, pickpak);
            } else {
                jdGeavanceerd.setVisible(true);
            }
            jdGeavanceerd.setVisible(false);
            BPPalgoritme = jdGeavanceerd.getBPPalgoritme();
            arduinoKraan = jdGeavanceerd.getArduinoKraan();
            arduinoSchijf = jdGeavanceerd.getArduinoSchijf();

            if (jdGeavanceerd.connected()) {
                jbbevestig.setEnabled(true);
                jbStop.setEnabled(true);
            } else {
                jbbevestig.setEnabled(false);
                jbStop.setEnabled(false);
            }
            //jdGeavanceerd.dispose();

        } else if (e.getSource() == jbStop) {
            if (jbStop.getText().equals("Afbreken")) {
                jbStop.setText("Reset");

                arduinoKraan.serialWrite('f');

                t.stop();

            } else if (jbStop.getText().equals("Reset")) {
                jbStop.setText("Afbreken");
                jbStop.setEnabled(false);

                pickpak.resetRobots();

            }

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
                ArrayList<Integer> route = pickpak.voerTSPuit(bestelling.getBesteldeItems());

                pickpak.voerBPPuit(route, BPPalgoritme);
            }

        } catch (Exception ex) {
            System.out.println("Er ging iets mis\n...");
            System.out.println(ex);
        }
    }

    public boolean checkRobotConnection() {
        try {
            arduinoKraan.getSerialPort();
            arduinoSchijf.getSerialPort();
            return true;
        } catch (NullPointerException npe) {
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
                
                tabel.setModel(pickpak.maakTabelModel(it));
                
                gridPanel.paintImmediately(0, 0, 1920, 1080);

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
                dozenPanel.paintImmediately(0, 0, 1920, 1080);

                pickpak.beweegKraan(it, arduinoKraan);

                System.out.println("KRAAN BEWOGEN\n...");

                gridPanel.paintImmediately(0, 0, 1920, 1080);

                arduinoKraan.serialWrite('p'); //push

                System.out.println("GEPUSHT\n...");

                pickpak.setPush(true);

                gridPanel.paintImmediately(0, 0, 1920, 1080);

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

                gridPanel.paintImmediately(0, 0, 1920, 1080);

                pickpak.werkDoosInhoudBij(it);

                dozenPanel.paintImmediately(0, 0, 1920, 1080);

            }
            arduinoKraan.serialWrite("c00");
            arduinoSchijf.serialWrite("c1");

            gridPanel.paintImmediately(0, 0, 1920, 1080);
            dozenPanel.paintImmediately(0, 0, 1920, 1080);
            
            jbbevestig.setEnabled(true);
        }
    }
}
