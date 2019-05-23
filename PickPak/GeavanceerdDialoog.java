package PickPak;

import arduino.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class GeavanceerdDialoog extends JDialog implements ActionListener {

    private JFrame frame;
    private PickPak pickpak;
    private JPanel jpArduino, jpSchijf, jpKraan, jpTSPBPP;
    private PortDropdownMenu pdmCOMkraan, pdmCOMschijf;
    private JComboBox jcbBPP;
    private JButton jbConnect, jbRefresh, jbApply, jbUp, jbDown, jbLeft, jbRight, jbStop, jbReset, jbKalibreerSensor, jbKalibreerSchijf, jbPush, jbCoordinaat, jbNoodstop;
    private JTextField jtfCoordinaat;
    private Arduino arduinoKraan, arduinoSchijf;
    private boolean aanHetKalibreren;
    private int BPPalgoritme;

    public GeavanceerdDialoog(JFrame frame, PickPak pickpak) {
        super(frame, true);

        this.frame = frame;
        this.pickpak = pickpak;
        aanHetKalibreren = false;

        setTitle("Geavanceerde instellingen");
        setSize(new Dimension(1200, 240));
        setLayout(new FlowLayout());

        // arduino connectie
        TitledBorder tbArduino = new TitledBorder("Arduino");

        jpArduino = new JPanel(new FlowLayout());
        jpArduino.setBorder(tbArduino);

        jpArduino.add(new JLabel("Pickrobot: "));

        pdmCOMkraan = new PortDropdownMenu();
        pdmCOMkraan.refreshMenu();
        jpArduino.add(pdmCOMkraan);

        jpArduino.add(new JLabel("Inpakrobot: "));

        pdmCOMschijf = new PortDropdownMenu();
        pdmCOMschijf.refreshMenu();
        jpArduino.add(pdmCOMschijf);

        jbRefresh = new JButton("Refresh");
        jbRefresh.addActionListener(this);
        jpArduino.add(jbRefresh);

        jbConnect = new JButton("Connect");
        jbConnect.addActionListener(this);
        jpArduino.add(jbConnect);

        add(jpArduino);

        // schijf sensor en schijf
        TitledBorder tbSchijf = new TitledBorder("Schijf");

        jpSchijf = new JPanel(new FlowLayout());
        jpSchijf.setBorder(tbSchijf);

        jbKalibreerSchijf = new JButton("Draai schijf");
        jbKalibreerSchijf.addActionListener(this);
        jpSchijf.add(jbKalibreerSchijf);
        jbKalibreerSchijf.setEnabled(false);

        jbKalibreerSensor = new JButton("Kalibreer sensor");
        jbKalibreerSensor.addActionListener(this);
        jpSchijf.add(jbKalibreerSensor);
        jbKalibreerSensor.setEnabled(false);

        add(jpSchijf);

        // kraan bewegen
        //jbUp, jbDown, jbLeft, jbRight, jbStop, jbReset, jbPush, jbPull, jbCoordinaat;
        TitledBorder tbKraan = new TitledBorder("Pick robot");

        jpKraan = new JPanel(new FlowLayout());
        jpKraan.setBorder(tbKraan);

        jbUp = new JButton("Omhoog");
        jbUp.addActionListener(this);
        jpKraan.add(jbUp);
        jbUp.setEnabled(false);

        jbDown = new JButton("Omlaag");
        jbDown.addActionListener(this);
        jpKraan.add(jbDown);
        jbDown.setEnabled(false);

        jbLeft = new JButton("Links");
        jbLeft.addActionListener(this);
        jpKraan.add(jbLeft);
        jbLeft.setEnabled(false);

        jbRight = new JButton("Rechts");
        jbRight.addActionListener(this);
        jpKraan.add(jbRight);
        jbRight.setEnabled(false);

        jbStop = new JButton("Stop");
        jbStop.addActionListener(this);
        jpKraan.add(jbStop);
        jbStop.setEnabled(false);

        jbPush = new JButton("Push");
        jbPush.addActionListener(this);
        jpKraan.add(jbPush);
        jbPush.setEnabled(false);

        jpKraan.add(new JLabel("Coorindaat: "));

        jtfCoordinaat = new JTextField(4);
        jpKraan.add(jtfCoordinaat);

        jbCoordinaat = new JButton("Ga");
        jbCoordinaat.addActionListener(this);
        jpKraan.add(jbCoordinaat);
        jbCoordinaat.setEnabled(false);

        jbReset = new JButton("Reset");
        jbReset.addActionListener(this);
        jpKraan.add(jbReset);
        jbReset.setEnabled(false);

        jbNoodstop = new JButton("Noodstop");
        jbNoodstop.addActionListener(this);
        jpKraan.add(jbNoodstop);
        jbNoodstop.setEnabled(false);

        add(jpKraan);

        // tsp bpp
        TitledBorder tbTSPBPP = new TitledBorder("TSP en BPP");

        jpTSPBPP = new JPanel(new FlowLayout());
        jpTSPBPP.setBorder(tbTSPBPP);

        jpTSPBPP.add(new JLabel("BPP algoritme: "));

        String[] BPPalgoritmes = {"Best fit", "First fit"};
        jcbBPP = new JComboBox(BPPalgoritmes);
        jcbBPP.addActionListener(this);
        jpTSPBPP.add(jcbBPP);

        add(jpTSPBPP);

        jbApply = new JButton("Apply");
        jbApply.addActionListener(this);
        add(jbApply);

        //pack();
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jbRefresh) {
            pdmCOMkraan.refreshMenu();
            pdmCOMschijf.refreshMenu();
        } else if (e.getSource() == jbConnect) {
            if (jbConnect.getText().equals("Connect")) {
                if (jbConnect.getText().equals("Disconnect")) {
                    String kraanPort = (String) pdmCOMkraan.getSelectedItem();
                    String schijfPort = (String) pdmCOMschijf.getSelectedItem();

                    if (!kraanPort.equals(schijfPort)) {

                        arduinoKraan = new Arduino(kraanPort, 9600);
                        arduinoSchijf = new Arduino(schijfPort, 9600);

                        if (arduinoKraan.openConnection() && arduinoSchijf.openConnection()) {
                            jbConnect.setText("Disconnect");
                            pdmCOMschijf.setEnabled(false);
                            pdmCOMkraan.setEnabled(false);
                            jbRefresh.setEnabled(false);
                            
                            jbKalibreerSchijf.setEnabled(true);
                            jbKalibreerSensor.setEnabled(true);
                            jbUp.setEnabled(true);
                            jbDown.setEnabled(true);
                            jbLeft.setEnabled(true);
                            jbRight.setEnabled(true);
                            jbStop.setEnabled(true);
                            jbPush.setEnabled(true);
                            jbCoordinaat.setEnabled(true);
                            jbReset.setEnabled(true);
                            jbNoodstop.setEnabled(true);
                                    
                        }
                    }
                } else {
                    arduinoKraan.closeConnection();
                    arduinoSchijf.closeConnection();
                    
                    jbConnect.setText("Cconnect");
                            pdmCOMschijf.setEnabled(true);
                            pdmCOMkraan.setEnabled(true);
                            jbRefresh.setEnabled(true);
                            
                            jbKalibreerSchijf.setEnabled(false);
                            jbKalibreerSensor.setEnabled(false);
                            jbUp.setEnabled(false);
                            jbDown.setEnabled(false);
                            jbLeft.setEnabled(false);
                            jbRight.setEnabled(false);
                            jbStop.setEnabled(false);
                            jbPush.setEnabled(false);
                            jbCoordinaat.setEnabled(false);
                            jbReset.setEnabled(false);
                            jbNoodstop.setEnabled(false);
                }
            } else {
                arduinoKraan.closeConnection();
                arduinoSchijf.closeConnection();
                jbConnect.setText("Connect");
                pdmCOMschijf.setEnabled(true);
                pdmCOMkraan.setEnabled(true);
                jbRefresh.setEnabled(true);
            }
        } else if (e.getSource() == jbKalibreerSchijf) {
            if (aanHetKalibreren) {
                jbKalibreerSchijf.setText("Kalibreer");
                jbConnect.setEnabled(true);
                jbReset.setEnabled(true);

            } else {
                jbKalibreerSchijf.setText("Stop");

                jbConnect.setEnabled(false);
                jbReset.setEnabled(false);
            }

            pickpak.kalibreerSchijf(arduinoSchijf, aanHetKalibreren);
            aanHetKalibreren = !aanHetKalibreren;
        } else if (e.getSource() == jbKalibreerSensor) {
            arduinoSchijf.serialWrite('t');
        } else if (e.getSource() == jbUp) {
            arduinoKraan.serialWrite('u');
        } else if (e.getSource() == jbDown) {
            arduinoKraan.serialWrite('d');
        } else if (e.getSource() == jbLeft) {
            arduinoKraan.serialWrite('l');
        } else if (e.getSource() == jbRight) {
            arduinoKraan.serialWrite('r');
        } else if (e.getSource() == jbStop) {
            arduinoKraan.serialWrite('s');
        } else if (e.getSource() == jbPush) {
            arduinoKraan.serialWrite('p');
        } else if (e.getSource() == jbCoordinaat) {
            arduinoKraan.serialWrite("c" + jtfCoordinaat.getText());
        } else if (e.getSource() == jbReset) {
            arduinoKraan.serialWrite('z');
        } else if (e.getSource() == jbNoodstop) {
            arduinoKraan.serialWrite('f');
        } else if (e.getSource() == jbApply) {
            BPPalgoritme = jcbBPP.getSelectedIndex();
        }
    }

    public int getBPPalgoritme() {
        return BPPalgoritme;
    }

    public Arduino getArduinoKraan() {
        return arduinoKraan;
    }

    public Arduino getArduinoSchijf() {
        return arduinoSchijf;
    }
}
