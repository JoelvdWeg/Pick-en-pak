package PickPak;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import arduino.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Scrollbar;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.text.Document;
import javax.xml.parsers.*;
import org.w3c.dom.*;

public class PickFrame extends JFrame implements ActionListener {
    private PickPak pickpak;
    private JTextField jtfx, jtfy, jtfFile;
    private JLabel jlx, jly, jlFile;
    private JButton jbbevestig;
    private JLabel jlStockItemID;
    private JLabel jlStockItemName;
    private Arduino arduino;
    private JTabbedPane pane;
    private JPanel main, advanced;


    private int aantalBestellingen = 0;
    private boolean aanHetKalibreren = false;
    private boolean stop = false;
    private int picknr = 0;
    ArrayList<Item> bestelling;




    private GeavanceerdDialoog jdGeavanceerd;

    private int BPPalgoritme;


    private PickPanel panel;

    private Arduino arduinoKraan, arduinoSchijf;


    public PickFrame(PickPak pickpak) {
        setTitle("GUI");
        setSize(1920, 1080);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        main = new JPanel();
        advanced = new JPanel();

        this.pickpak = pickpak;

        

        jlx = new JLabel("x-as");
        jlx.setEnabled(false);
//        add(jlx);

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
        main.add(jlFile, BorderLayout.NORTH);

        jtfFile = new JTextField(10);

        main.add(jtfFile, BorderLayout.NORTH);

        jtfFile.setText("bestelling.xml");
        add(jtfFile);


        jbbevestig = new JButton("Start");

        jbbevestig.setEnabled(true);
        main.add(jbbevestig, BorderLayout.NORTH);

        setVisible(true);
        PickPanel panel = new PickPanel(pickpak);
        main.add(panel);
        


        pane = new JTabbedPane();
        add(pane);
        pane.add(main, "main");
        pane.add(advanced, "advanced");
        
        main.setPreferredSize(new Dimension(1920, 1080));
        JScrollPane scrollFrame = new JScrollPane(main);
        main.setAutoscrolls(true);
        scrollFrame.setPreferredSize(new Dimension(1800, 1100));
        this.add(scrollFrame);

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
            jbbevestig.setEnabled(false);


            try {
                String naam = "...";
                String adres1 = "...";
                String adres2 = "...";
                String land = "...";
                ArrayList<Integer> items = new ArrayList<>();

                //File file = new File("userdata.xml");
                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                org.w3c.dom.Document document = documentBuilder.parse(new File("bestelling.xml"));

                Element rootElement = (Element) document.getFirstChild();

                NodeList nlist = rootElement.getChildNodes();
                System.out.println(nlist);

                for (int i = 0; i < nlist.getLength(); i++) {
                    //System.out.println("1:     "+nlist.item(i).getNodeType()+"\n...");
                    //System.out.println("2:     "+nlist.item(i).getTextContent()+"\n...");
                    //System.out.println("3:     "+nlist.item(i).getNodeName()+"\n...");

                    Node child = nlist.item(i);
                    //System.out.println(child);

                    String nodeName = (String) child.getNodeName();
                    //System.out.println("NODENAME" + child.getNodeType());
                    if (nodeName.equals("naam")) {
                        naam = child.getTextContent();
                    } else if (nodeName.equals("adres1")) {
                        adres1 = child.getTextContent();
                    } else if (nodeName.equals("adres2")) {
                        adres2 = child.getTextContent();
                    } else if (nodeName.equals("land")) {
                        land = child.getTextContent();
                    } else if (nodeName.equals("item")) {
                        
                        //items.add(Integer.parseInt(child.getTextContent()));
                        

//                        NodeList itemList = child.getChildNodes();
//                        //System.out.println(itemList);
//                        for (int j = 0; j < itemList.getLength(); j++) {
//
//                            if (itemList.item(j).getNodeType() == Node.ELEMENT_NODE) {
//                                
//                                System.out.println(itemList.item(j).getAttributes());
//
//                                Node itemChild = itemList.item(j);
//                                String itemNodeName = (String) itemChild.getNodeName();
//
//                                int itemID = -1;
//                                int aantal = -1;
//                                //System.out.println(itemNodeName);
//
//                                if (itemNodeName.equals("id")) {
//                                    itemID = Integer.parseInt(itemChild.getTextContent());
//                                } else if (itemNodeName.equals("aantal")) {
//                                    aantal = Integer.parseInt(itemChild.getTextContent());
//                                }
//                                for (int k = 0; k < aantal; k++) {
//                                    items.add(itemID);
//                                }
//                            }
//                        }
                    }
                }

            aantalBestellingen++;
            if (aantalBestellingen > 1) { // aanpassen!
                
                pickpak.resetRobots(arduinoKraan, arduinoSchijf);
                
                //pickpak.resetRobots(arduinoKraan, arduinoSchijf);

                //reconnect();

                panel.paintImmediately(0, 0, 1920, 1080);
            }

            tekenRoute(jtfFile.getText());

            pickBestelling();

        } else if(e.getSource() == geavanceerd){
            jdGeavanceerd = new GeavanceerdDialoog(this, pickpak);
            BPPalgoritme = jdGeavanceerd.getBPPalgoritme();
            arduinoKraan = jdGeavanceerd.getArduinoKraan();
            arduinoSchijf = jdGeavanceerd.getArduinoSchijf();
            jdGeavanceerd.dispose();
        }

        else if (e.getSource() == jbStop) { // aanpassen!
            System.out.println("STOP");

        }

        repaint();
    }
    }

    private void tekenRoute(String f) {
        try {
            bestelling = null;
            bestelling = pickpak.leesBestelling("bestelling.xml");

            if (bestelling == null) {
                System.out.println("hoi");
            }
            else {
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
