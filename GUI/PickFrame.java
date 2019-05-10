/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

/**
 *
 * @author hylke
 */
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import arduino.*;

public class PickFrame extends JFrame implements ActionListener {

	private Pick pick;
	private JTextField jtfx, jtfy;
	private JLabel jlx, jly, jlcontrolpanel, jlbestelling, jlstelling, jldozen;
	private JButton jbbevestig;

	private Arduino arduino;

	private PortDropdownMenu pdmCOM;
	private JButton jbRefresh;
	private JButton jbConnect;

	public PickFrame() {
		setTitle("GUI");
		setSize(1920, 1080);
		setLayout(new FlowLayout());

		this.pick = pick;

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
		add(jlx);

		jtfx = new JTextField(4);
		jtfx.setEnabled(false);
		add(jtfx);

		jly = new JLabel("y-as");
		jly.setEnabled(false);
		add(jly);

		jtfy = new JTextField(4);
		jtfy.setEnabled(false);
		add(jtfy);

		jbbevestig = new JButton("Bevestig");
		jbbevestig.addActionListener(this);
		jbbevestig.setEnabled(false);
		add(jbbevestig);

		setVisible(true);
		PickPanel panel = new PickPanel();
		add(panel);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jbbevestig) {
			try {
				Pick c = new Pick(Integer.parseInt(jtfx.getText()), Integer.parseInt(jtfy.getText()));
//                Pick.naarVakje(c); 
			} catch (NumberFormatException nfe) {
				System.out.println("onjuiste invoer!");
			}
			repaint();
			
			arduino.serialWrite(jtfx.getText() + jtfy.getText());
			
			jtfx.setText("");
			jtfy.setText("");
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
