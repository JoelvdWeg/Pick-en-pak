package PickPak;


import GUI.PickPanel;
import static PickPak.PickPak.items;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import arduino.*;
import java.awt.Dimension;
import java.awt.PopupMenu;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

public class PickFrame extends JFrame implements ActionListener {

    private PickPak pickpak;
    private JTextField jtfx, jtfy, jtfFile;
    private JLabel jlx, jly, jlFile;
    private JButton jbbevestig;
    private JLabel jlStockItemID;
    private JLabel jlStockItemName;
    private Arduino arduino;
    private static Connection connection;
    JTable table;
    private PortDropdownMenu pdmCOM;
    private JButton jbRefresh;
    private JButton jbConnect;
    private PopupMenu PickPanel;
    public PickFrame(PickPak pickpak) {
        
        setTitle("GUI");
        setSize(1920, 1080);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setTitle("Tabbed Pane");
        JTabbedPane jtp = new JTabbedPane();
        getContentPane().add(jtp);
        JPanel jp1 = new JPanel();
        JPanel jp2 = new JPanel();
        JLabel label1 = new JLabel();
        label1.add(PickPanel);
        JLabel label2 = new JLabel();
        label2.setText("You are in area of Tab2");
        jp1.add(label1);
        jp2.add(label2);
        jtp.addTab("Tab1", jp1);
        jtp.addTab("Tab2", jp2);
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
    
        String[] columnNames = {"ID", "alskfj", "sadfkjh"};
    
        Object[][] data = {
        {"lol" , "", ""},
        
        };
    table = new JTable(data, columnNames);
    table.setPreferredScrollableViewportSize(new Dimension(500, 50));
    }
          
    @Override       
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jbbevestig) {
            
            try {
                ArrayList<Item> bestelling = pickpak.maakBestellingAan("Lukas van Elten", "Molenmakerslaan 58", "3781DD Voorthuizen", "NEDERLAND", jtfFile.getText());
                pickpak.pickBestelling(bestelling);
            } catch (Exception ex) {
                System.out.println("Error!");
                System.out.println(ex);
            }

            //arduino.serialWrite(jtfx.getText() + jtfy.getText());

            jtfx.setText("");
            jtfy.setText("");
            repaint();
        } else if (e.getSource() == jbRefresh) {
            pdmCOM.refreshMenu();
        } else if (e.getSource() == jbConnect) {
            if (jbConnect.getText().equals("Connect")) {
                arduino = new Arduino(pdmCOM.getSelectedItem().toString(), 9600);
                if (arduino.openConnection()) {
                    jbConnect.setText("Disconnect");
                    pdmCOM.setEnabled(false);
                    jbRefresh.setEnabled(false);

                    jlx.setEnabled(true);
                    jtfx.setEnabled(true);
                    jly.setEnabled(true);
                    jtfy.setEnabled(true);
                    jbbevestig.setEnabled(true);
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
                jbbevestig.setEnabled(false);
            }
        }
    }
}
