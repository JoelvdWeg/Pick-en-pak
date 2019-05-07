package libraryTest;


import arduino.*;
import java.awt.FlowLayout;
import javax.swing.JFrame;

public class BasicLEDExample extends JFrame{

    public BasicLEDExample() {
        setTitle("GUI");
        setSize(500, 200);
        setLayout(new FlowLayout());
        
        setVisible(true);
        
    }
    
	public static void main(String[] args) {
	
		Arduino arduino = new Arduino("cu.usbmodem1411", 9600); //enter the port name here, and ensure that Arduino is connected, otherwise exception will be thrown.
		arduino.openConnection();
		arduino.closeConnection();
		

	}

}
