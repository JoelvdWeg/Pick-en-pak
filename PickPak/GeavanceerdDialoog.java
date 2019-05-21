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

public class GeavanceerdDialoog extends JDialog implements ActionListener{
    private JPanel jpArduino, jpSchijf, jpKraan, jpTSPBPP;
    private PortDropdownMenu pdmCOMkraan, pdmCOMschijf;
    private JComboBox jcbBPP;
    private JButton jbConnect, jbRefresh, jbCancel, jbTSP, jbUp, jbDown, jbLeft, jbRight, jbStop, jbReset, jbKalibreerSensor, jbKalibreerSchijf, jbPush, jbPull, jbCoordinaat, jbNoodstop;
    private JLabel jlpdmCOMkraan, jlpdmCOMschijf, jlBPP;
    private JTextField jtfCoordinaat;
    
    
    public GeavanceerdDialoog(JFrame frame){
        super(frame, true);
        
        setTitle("Geavanceerde instellingen");
        setSize(new Dimension(1000,800));
        setLayout(new FlowLayout());
        
        // arduino connectie
        
        TitledBorder tbArduino = new TitledBorder("Arduino");
        tbArduino.setTitleJustification(TitledBorder.CENTER);
        tbArduino.setTitlePosition(TitledBorder.TOP);
        
        jpArduino = new JPanel(new BorderLayout());
        jpArduino.setBackground(Color.RED);
        jpArduino.setBorder(tbArduino);
        
   
        jpArduino.add(new JLabel("Pickrobot: "));
        
        pdmCOMkraan = new PortDropdownMenu();
        pdmCOMkraan.refreshMenu();
        jpArduino.add(pdmCOMkraan);
        
        jpArduino.add(new JLabel("Inpakrobot: "));
        
        pdmCOMschijf= new PortDropdownMenu();
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
        
        jbKalibreerSchijf = new JButton("Draai schijf");
        jbKalibreerSchijf.addActionListener(this);
        add(jbKalibreerSchijf);
        
        jbKalibreerSensor = new JButton("Kalibreer sensor");
        jbKalibreerSensor.addActionListener(this);
        add(jbKalibreerSensor);
        
        
        // kraan bewegen
        //jbUp, jbDown, jbLeft, jbRight, jbStop, jbReset, jbPush, jbPull, jbCoordinaat;
        
        jbUp = new JButton("Omhoog");
        jbUp.addActionListener(this);
        add(jbUp);
        
        jbDown = new JButton("Omlaag");
        jbDown.addActionListener(this);
        add(jbDown);
        
        jbLeft = new JButton("Links");
        jbLeft.addActionListener(this);
        add(jbLeft);
        
        jbRight = new JButton("Rechts");
        jbRight.addActionListener(this);
        add(jbRight);
        
        jbStop = new JButton("Stop");
        jbStop.addActionListener(this);
        add(jbStop);
        
        jbPush = new JButton("Push");
        jbPush.addActionListener(this);
        add(jbPush);
        
        jbPull = new JButton("Pull");
        jbPull.addActionListener(this);
        add(jbPull);
        
        add(new JLabel("Coorindaat: "));
        
        jtfCoordinaat = new JTextField(4);
        add(jtfCoordinaat);
        
        jbCoordinaat = new JButton("Ga");
        jbCoordinaat.addActionListener(this);
        add(jbCoordinaat);
        
        jbReset = new JButton("Reset");
        jbReset.addActionListener(this);
        add(jbReset);
        
        jbNoodstop = new JButton("Noodstop");
        jbNoodstop.addActionListener(this);
        add(jbNoodstop);
        
        // tsp bpp
        
        jbTSP = new JButton("Teken TSP");
        jbTSP.addActionListener(this);
        add(jbTSP);
        
        add(new JLabel("BPP algoritme: "));
        
        String[] BPPalgoritmes = {"First fit","Best fit"};
        jcbBPP = new JComboBox(BPPalgoritmes);
        jcbBPP.addActionListener(this);
        add(jcbBPP);
        
        jbCancel = new JButton("Cancel");
        jbCancel.addActionListener(this);
        add(jbCancel);
        
        
        
        
       pack();
        
        
       setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e){
        
    }
}
