package GUI;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hylke
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.*;  

class DatabaseConn{ 
    public static ResultSet rs;
    
public static void main(String args[]){  
try{  
Class.forName("com.mysql.jdbc.Driver");  
Connection con=DriverManager.getConnection(  
"jdbc:mysql://localhost:3306/wideworldimporters","root","");  
//here wideworldimporters is database name, root is username and password  
Statement stmt=con.createStatement();  
rs=stmt.executeQuery("select * from stockitems where StockItemID <= 11");

/*
while(rs.next()){  
System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getInt(3)); 
}*/

PickFrame pickframe = new PickFrame();
System.out.println("gelukt!");
con.close();  
}catch(Exception e){ System.out.println(e); System.out.println("mislukt!");}  
} 


}  


/*

package libraryTest;
import java.sql.*;  
class DatabaseConn{  
public static void main(String args[]){  
try{  
Class.forName("com.mysql.jdbc.Driver");  
Connection con=DriverManager.getConnection(  
"jdbc:mysql://localhost:3306/sonoo","root","");  
//here sonoo is database name, root is username and password  
Statement stmt=con.createStatement();  
ResultSet rs=stmt.executeQuery("select * from emp");  
while(rs.next())  
System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));  

System.out.println("gelukt!");
con.close();  
}catch(Exception e){ System.out.println(e); System.out.println("mislukt!");}  
}  
}  


*/
