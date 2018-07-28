package examples4;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LightWeightDialog extends JPanel {
	private static final long serialVersionUID = -4125889801879081463L;

	public final JTextField	firstName = new JTextField(20);		// <1>
	public final JTextField	secondName = new JTextField(20);
	public final JTextField	lastName = new JTextField(30);
	
	private LightWeightDialog(){			// <2>
		setLayout(new GridLayout(3,2));
		add(new JLabel("First name"));	add(firstName);
		add(new JLabel("Second name"));	add(secondName);
		add(new JLabel("Last name"));	add(lastName);
	}
	
	public static void main(String[] args) {
		final LightWeightDialog	lwd = new LightWeightDialog();	// <3>
		
		if (JOptionPane.showConfirmDialog(null,lwd,"Enter requested parameters", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION) {
			System.err.println("First name = "+lwd.firstName.getText());	// <4>
			System.err.println("Second name = "+lwd.secondName.getText());
			System.err.println("Last name = "+lwd.lastName.getText());
		}
	}

}
