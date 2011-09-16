package ricm.mca2005.fivechess;

import java.awt.*;
import java.awt.Font;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import com.swtdesigner.SwingResourceManager;

//jiangfeng

public class About extends JDialog {

	private JTextArea ruleTextArea;
	/**
	 * Create the dialog
	 */
	Image aboutpic;
	String ruletxt;

	public About(JFrame parent) {
		super(parent, true);
		setAlwaysOnTop(true);
		getContentPane().setLayout(null);
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("About");
		setBounds(100, 100, 338, 381);

		final JButton okButton = new JButton();
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		okButton.setText("OK");
		okButton.setBounds(115, 319, 95, 23);
		getContentPane().add(okButton);

		final JLabel fiveChessV10Label = new JLabel();
		fiveChessV10Label.setIcon(SwingResourceManager.getIcon(About.class,
				"/images/about.jpg"));
		fiveChessV10Label.setFont(new Font("Times New Roman", Font.BOLD, 16));
		fiveChessV10Label.setText("Five Chess V1.0");
		fiveChessV10Label.setBounds(10, 0, 317, 242);
		getContentPane().add(fiveChessV10Label);

		ruleTextArea = new JTextArea();
		ruleTextArea.setEditable(false);
		ruleTextArea.setBorder(new TitledBorder(null, "",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		ruleTextArea
				.setText("Rule:\nTwo players put the chessman alternately.\nIf anyone forms a line of five consecutive chessmans, \nno matter horizontal, vertical or diagonal, then he wins.");
		ruleTextArea.setRows(4);
		ruleTextArea.setLineWrap(true);
		ruleTextArea.setFont(new Font("Arial", Font.BOLD, 11));
		ruleTextArea.setBounds(10, 245, 312, 68);
		getContentPane().add(ruleTextArea);
		ruletxt = "Rule:\n" + "Player put the chessman alternately.\n"
				+ "If anyone forms a line of five consecutive chessmans, \n"
				+ "no matter horizontal, vertical or diagonal, then he wins.";
		aboutpic = Toolkit.getDefaultToolkit().getImage(
				ClassLoader.getSystemResource("images/about.jpg"));
		//
	}

}
