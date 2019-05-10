/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libraryTest;

import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import static libraryTest.DatabaseConn.rs;

/**
 *
 * @author Jens
 */
public class Scherm extends JFrame {
    
    private JLabel jlStockItemID;
    private JLabel jlStockItemName;
    
    public Scherm(){
        
        setTitle("Database");
        setSize(300, 200);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        try{
            while(DatabaseConn.rs.next()){
                System.out.println();
                jlStockItemID = new JLabel(Integer.toString(DatabaseConn.rs.getInt(1)));
                add(jlStockItemID);
                jlStockItemName = new JLabel(DatabaseConn.rs.getString(2));
                add(jlStockItemName);
                }
        }catch(Exception e){
            
        }
            
        
        setVisible(true);
        
    }
    
    
}
