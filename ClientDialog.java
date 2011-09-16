package ricm.mca2005.fivechess;

import java.awt.ComponentOrientation;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ClientDialog extends JDialog {

	private JTextField porttext;
	/**
	 * Launch the application
	 * 
	 * @param args
	 */

	String ip;
	int port;
	Chess chess;

	private JTextField ip4;
	private JTextField ip3;
	private JTextField ip2;
	private JTextField ip1;

	/**
	 * Create the dialog
	 */
	public ClientDialog(Chess che) {
		super(che.frame, "Connection Setting", true);
		setAlwaysOnTop(true);
		chess = che;
		getContentPane().setLayout(null);
		setTitle("Client Setting");
		setBounds(100, 100, 320, 147);

		ip1 = new JTextField();
		ip1.setText("127");
		ip1.setBounds(84, 10, 47, 20);
		getContentPane().add(ip1);

		ip2 = new JTextField();
		ip2.setText("0");
		ip2.setBounds(137, 10, 47, 20);
		getContentPane().add(ip2);

		ip3 = new JTextField();
		ip3.setText("0");
		ip3.setBounds(190, 10, 47, 20);
		getContentPane().add(ip3);

		ip4 = new JTextField();
		ip4.setText("1");
		ip4.setBounds(241, 10, 47, 20);
		getContentPane().add(ip4);

		final JLabel serverIpLabel = new JLabel();
		serverIpLabel.setFont(new Font("", Font.BOLD, 12));
		serverIpLabel.setText("Server IP");
		serverIpLabel.setBounds(24, 13, 54, 14);
		getContentPane().add(serverIpLabel);

		porttext = new JTextField();
		porttext.setText("5555");
		porttext.setBounds(84, 36, 100, 20);
		getContentPane().add(porttext);

		final JLabel portLabel = new JLabel();
		portLabel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		portLabel.setFont(new Font("", Font.BOLD, 12));
		portLabel.setText("ServerPort");
		portLabel.setBounds(10, 39, 68, 14);
		getContentPane().add(portLabel);

		final JButton okButton = new JButton();
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ip = ip1.getText().trim() + "." + ip2.getText().trim() + "."
						+ ip3.getText().trim() + "." + ip4.getText().trim();
				port = Integer.parseInt(porttext.getText().trim());
				chess.serverip = ip;
				chess.serverport = port;
				dispose();
			}
		});
		okButton.setFont(new Font("", Font.BOLD, 12));
		okButton.setText("OK");
		okButton.setBounds(113, 81, 95, 23);
		getContentPane().add(okButton);
		//
	}

}
