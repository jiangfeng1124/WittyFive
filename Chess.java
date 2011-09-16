package ricm.mca2005.fivechess;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.JMenuItem;
import java.net.*;

public class Chess {

	JFrame frame;

	boolean PcPlay = false;

	boolean NetPlay = false;

	boolean NetOK = false;

	boolean player1 = false;

	boolean player2 = false;

	boolean wait = false;

	Chessman chessman;

	Chessline judge;

	Computer computer;

	Chessboard board;

	String serverip;

	int serverport;

	String clientip;

	int clientport;

	String remoteip;

	ServerSocket serversocket;

	Socket conn;

	ServerThread serverthread;

	Message message;

	int mode = 0; // 0=normal, 1=client, 2=server;

	int disconnecting = 0;

	SocketThread socketthread;

	InputStream in;

	OutputStream out;

	About aboutwin;

	ServerDialog serverdialog;

	ClientDialog clientdialog;

	JMenuItem PlayToPcMenuItem;

	JMenuItem disconnectMenuItem;

	JMenuItem resetMenuItem;

	JMenuItem iAmServerMenuItem;

	JMenuItem iAmClientMenuItem;

	/**
	 * Launch the application
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Chess window = new Chess();
			window.frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the application
	 */
	public Chess() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame
	 */
	private void initialize() {
		message = new Message(this);
		chessman = new Chessman();
		judge = new Chessline(chessman);
		computer = new Computer(judge);
		serverdialog = new ServerDialog(this);
		clientdialog = new ClientDialog(this);
		frame = new JFrame();
		frame.setResizable(false);
		frame.getContentPane().setLayout(new BorderLayout());
		frame.setTitle("Five Chess V1.0 [RICM MCA2005]");
		frame.setBounds(100, 100, 453, 498); // no effect?
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		final JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		final JMenu systemMenu = new JMenu();
		systemMenu.setText("System");
		menuBar.add(systemMenu);

		final JMenu PlayToNetworkMenu = new JMenu();
		PlayToNetworkMenu.setText("Play on network");
		systemMenu.add(PlayToNetworkMenu);

		iAmServerMenuItem = new JMenuItem();
		iAmServerMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				serverdialog.setLocation(150, 150);
				serverdialog.setVisible(true);
				// serverdialog=null;
				PcPlay = false;
				NetPlay = true;
				player1 = true;
				player2 = false;
				reset();
				startserver();
				mode = 2;
			}
		});
		iAmServerMenuItem.setText("I am Server");
		PlayToNetworkMenu.add(iAmServerMenuItem);

		iAmClientMenuItem = new JMenuItem();
		iAmClientMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				clientdialog.setLocation(150, 150);
				clientdialog.setVisible(true);
				// clientdialog=null;
				PcPlay = false;
				NetPlay = true;
				player1 = false;
				player2 = true;
				reset();
				try {
					disconnect();
					conn = new Socket(serverip, serverport);
					in = conn.getInputStream();
					out = conn.getOutputStream();
					wait = true;
					NetPlay = true;
					player1 = false;
					player2 = true;
					setThread();
					mode = 1;
				} catch (IOException ex) {
					reset();
					NetOK = false;
					JOptionPane.showMessageDialog(null, "Connect error",
							"Error", JOptionPane.ERROR_MESSAGE);

				}

			}
		});
		iAmClientMenuItem.setText("I am Client");
		PlayToNetworkMenu.add(iAmClientMenuItem);

		disconnectMenuItem = new JMenuItem();
		disconnectMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (out != null) {
					message.command = 10;
					message.send();
				}
				disconnect();
			}
		});
		disconnectMenuItem.setText("Disconnect");
		PlayToNetworkMenu.add(disconnectMenuItem);
		disconnectMenuItem.setEnabled(false);

		PlayToPcMenuItem = new JMenuItem();
		systemMenu.add(PlayToPcMenuItem);
		PlayToPcMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reset();
				conn = null;
				NetOK = false;
				serversocket = null;
				in = null;
				out = null;
				frame.setTitle("Human fight PC!!!");
				PcPlay = true;
				NetPlay = false;
				player1 = true;
				player2 = false;
				mode = 0;
			}
		});
		PlayToPcMenuItem.setText("Play with computer");

		resetMenuItem = new JMenuItem();
		resetMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reset();
				if (NetPlay && NetOK) {
					message.command = 1;
					message.send();
				}
			}
		});
		resetMenuItem.setText("Reset");
		systemMenu.add(resetMenuItem);

		systemMenu.addSeparator(); 

		final JMenuItem exitMenuItem = new JMenuItem();
		exitMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		exitMenuItem.setText("Exit");
		systemMenu.add(exitMenuItem);

		final JMenu helpMenu = new JMenu();
		helpMenu.setText("Help");
		menuBar.add(helpMenu);

		final JMenuItem aboutMenuItem = new JMenuItem();
		aboutMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aboutwin = new About(null);
				aboutwin.setLocation(150, 150);
				aboutwin.setVisible(true);
				aboutwin = null;
			}
		});
		aboutMenuItem.setText("About");
		helpMenu.add(aboutMenuItem);

		board = new Chessboard(this);
		board.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int x, y;
				int i, j;
				x = e.getX();
				y = e.getY();
				j = (x - 5) / 23;
				i = (y - 5) / 23;
				if (j < 19 && i < 19 && !full()) {
					if (chessman != null) {
						if (chessman.point[i][j] == 2) {
							chesslistener(i, j);
						} else
							JOptionPane.showMessageDialog(null,
									"There already has a piece.", "Error",
									JOptionPane.ERROR_MESSAGE);
					}
					frame.repaint();
				}
			}
		});
		frame.getContentPane().add(board);
	}

	public void reset() {
		if (chessman != null)
			chessman.init();
		board.draw();

	}

	boolean full() {
		boolean full = false;
		int count = 0;
		if (chessman != null) {
			out: for (int i = 0; i < 19; i++)
				for (int j = 0; j < 19; j++) {
					if (chessman.point[i][j] == 2) {
						full = false;
						break out;
					}
					count++;
				}
		}
		if (count == 361) {
			full = true;
			JOptionPane.showMessageDialog(null, "Draw", "Message",
					JOptionPane.INFORMATION_MESSAGE);
			reset();
		}
		return full;
	}

	void chesslistener(int i, int j) {
		if (PcPlay) {
			if (player1) {
				chessman.modify(i, j, 0);
				player1 = false;
				player2 = true;
				board.draw();
				whowin();
			}
			if (player2 && chessman != null) {
				computer.location();
				i = computer.row;
				j = computer.line;
				chessman.modify(i, j, 1);
				player2 = false;
				player1 = true;
				board.draw();
				whowin();
			}

		}
		if (NetPlay) {
			if (wait == false) {
				wait = true;
				message.row = i;
				message.col = j;
				message.command = 2;
				message.send();
				if (player1)
					chessman.modify(i, j, 0);
				if (player2)
					chessman.modify(i, j, 1);
				board.draw();
				frame.setTitle("Waiting for competitor");
				whowin();
			}

		}
	}

	public void whowin() {
		judge.count();
		if (judge.judge() == 0) {
			JOptionPane.showMessageDialog(null, "Haha! Black won!!!", "Haha",
					JOptionPane.INFORMATION_MESSAGE);
			board.draw();
			reset();
			if (NetPlay) {
				if (player1) {
					player1 = false;
					player2 = true;
				} else {
					player2 = false;
					player1 = true;
				}
			} else {
				if (player1) {
					player1 = false;
					player2 = true;
				} else {
					player2 = false;
					player1 = true;
				}
			}
		} else if (judge.judge() == 1) {
			JOptionPane.showMessageDialog(null, "Haha! White won!!!", "Haha",
					JOptionPane.INFORMATION_MESSAGE);
			board.draw();
			reset();
		}

	}

	public void startserver() {
		if (serverthread != null)
			disconnect();
		serverthread = new ServerThread(this);
		serverthread.start();
	}

	public void setThread() {
		NetOK = true;
		disconnectMenuItem.setEnabled(true);
		PlayToPcMenuItem.setEnabled(false);
		iAmServerMenuItem.setEnabled(false);
		iAmClientMenuItem.setEnabled(false);
		socketthread = new SocketThread(this);
		socketthread.start();
		remoteip = conn.getRemoteSocketAddress().toString();
		JOptionPane.showMessageDialog(null, "Connect to " + remoteip
				+ " successfully", "Information",
				JOptionPane.INFORMATION_MESSAGE);
		if (player1)
			this.frame.setTitle("Your turn...");
		if (player2)
			this.frame.setTitle("Waiting for competitor...");
	}

	void disconnect() {
		if (conn == null)
			return;
		disconnecting = 1;
		socketthread.StopRun();
		socketthread = null;
		serverthread = null;
		if (mode == 1) {
			try {
				in.close();
				out.close();
				conn.close();
			} catch (IOException e) {
			}
		}
		if (mode == 2) {
			try {
				in.close();
				out.close();
				conn.close();
				serversocket.close();
			} catch (IOException e) {
			}
		}
		in = null;
		out = null;
		conn = null;
		serversocket = null;
		NetOK = false;
		NetPlay = false;
		PcPlay = false;
		frame.setTitle("FiveChess RICM-MCA2005");
		disconnectMenuItem.setEnabled(false);
		PlayToPcMenuItem.setEnabled(true);
		iAmServerMenuItem.setEnabled(true);
		iAmClientMenuItem.setEnabled(true);
		reset();
		mode = 0;
		disconnecting = 0;
	}

}
