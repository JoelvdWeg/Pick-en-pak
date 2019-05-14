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

    private PickPak pickpak;
    private JTextField jtfx, jtfy, jtfFile;
    private JLabel jlx, jly, jlFile;
    private JButton jbbevestig;
    private JLabel jlStockItemID;
    private JLabel jlStockItemName;
    private Arduino arduino;

    private boolean aanHetKalibreren = false;

    private PortDropdownMenu pdmCOM;
    private JButton jbRefresh;
    private JButton jbConnect, jbKalibreer;

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
        jbbevestig.setEnabled(true);
        add(jbbevestig);

        setVisible(true);
        PickPanel panel = new PickPanel(pickpak);
        add(panel);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jbbevestig) {

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
                        
                        items.add(Integer.parseInt(child.getTextContent()));
                        

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

                System.out.println(naam + " " + adres1 + " " + adres2 + " " + land);
                for (int i : items) {
                    System.out.println(i);
                }
//                String usr = document.g("user").item(0).getTextContent();
//                String pwd = document.getElementsByTagName("password").item(0).getTextContent();
//                //ArrayList<Item> bestelling = pickpak.maakBestellingAan("Lukas van Elten", "Molenmakerslaan 58", "3781DD Voorthuizen", "NEDERLAND", jtfFile.getText());

                //pickpak.pickBestelling(bestelling);
                jtfFile.setText("");
                repaint();
            } catch (Exception ex) {
                System.out.println("Error!");
                System.out.println(ex);
            }

            //arduino.serialWrite(jtfx.getText() + jtfy.getText());
            //jtfx.setText("");
            //jtfy.setText("");
        } else if (e.getSource() == jbRefresh) {
            pdmCOM.refreshMenu();
        } else if (e.getSource() == jbConnect) {
            if (jbConnect.getText().equals("Connect")) {
                arduino = new Arduino(pdmCOM.getSelectedItem().toString(), 9600);
                if (arduino.openConnection()) {
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
            kalibreerSchijf();
        }
    }

    private void kalibreerSchijf() {
        if (aanHetKalibreren) {
            System.out.println("Schijf gekalibreerd\n...");
            aanHetKalibreren = false;
            arduino.serialWrite('d');
        } else {
            aanHetKalibreren = true;
            arduino.serialWrite('k');

            System.out.println("Schijf kalibreren\n...");
        }

    }
}
