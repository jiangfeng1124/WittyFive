package ricm.mca2005.fivechess;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.WindowConstants;

public class ServerDialog extends JDialog {

	/**
	 * Launch the application
	 * 
	 * @param args
	 */
	Chess chess;
	private JTextField textport;

	/**
	 * Create the dialog
	 */
	public ServerDialog(Chess che) {
		super(che.frame, "Server Setting", true);
		setAlwaysOnTop(true);
		chess = che;
		setResizable(false);
		getContentPane().setLayout(null);
		setTitle("Server Setting");
		setBounds(100, 100, 335, 139);

		final JButton okButton = new JButton();
		okButton.setFont(new Font("", Font.BOLD, 12));
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chess.serverport = Integer.parseInt(textport.getText().trim());
				// System.out.println(Chess.serverport);
				dispose();
			}
		});
		okButton.setText("OK");
		okButton.setBounds(119, 69, 95, 23);
		getContentPane().add(okButton);

		final JLabel portNumberLabel = new JLabel();
		portNumberLabel.setFont(new Font("", Font.BOLD, 14));
		portNumberLabel.setText("Port number:");
		portNumberLabel.setBounds(24, 21, 105, 23);
		getContentPane().add(portNumberLabel);

		textport = new JTextField(4);
		textport.setText("5555");
		textport.setBounds(135, 23, 143, 20);
		getContentPane().add(textport);

		//
	}

}
