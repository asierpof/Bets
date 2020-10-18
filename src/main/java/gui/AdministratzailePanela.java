package gui;

/**
 * @author Software Engineering teachers
 */

import javax.swing.*;

import domain.Event;
import businessLogic.BLFacade;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AdministratzailePanela extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	private JButton jButtonCreateQuery = null;

	private static BLFacade appFacadeInterface;

	public static BLFacade getBusinessLogic() {
		return appFacadeInterface;
	}

	public static void setBussinessLogic(BLFacade afi) {
		appFacadeInterface = afi;
	}

	protected JLabel jLabelSelectOption;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JButton btnNewButton;
	private JButton btnNewButton_3;
	private String lengoaia;

	/**
	 * This is the default constructor
	 */
	public AdministratzailePanela(String l ) {
		super();
		this.lengoaia=l;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					// if (ConfigXML.getInstance().isBusinessLogicLocal()) facade.close();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					System.out.println(
							"Error: " + e1.toString() + " , probably problems with Business Logic or Database");
				}
				System.exit(1);
			}
		});

		initialize();
		// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		// this.setSize(271, 295);
		this.setSize(495, 429);
		this.setContentPane(getJContentPane());
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainTitle"));
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getLblNewLabel());
			jContentPane.add(getBoton2());
			JButton btnNewButton_1 ; //$NON-NLS-1$ //$NON-NLS-2$
			if(lengoaia.equals("eus"))
				btnNewButton_1 = new JButton("kuotak sortu");
			else if(lengoaia.equals("cas"))
				btnNewButton_1 = new JButton("crear cuota");
			else 
				btnNewButton_1 = new JButton("create quota");
			
			btnNewButton_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFrame a = new KuotaSortu(new Vector<Event>(),lengoaia);
					a.setVisible(true);
				}
			});
			btnNewButton_1.setBounds(0, 187, 477, 25);
			jContentPane.add(btnNewButton_1);
			
			JButton btnNewButton_2 ; //$NON-NLS-1$ //$NON-NLS-2$
			if(lengoaia.equals("eus"))
				btnNewButton_2 = new JButton("gertaera sortu");
			else if(lengoaia.equals("cas"))
				btnNewButton_2 = new JButton("crear evento");
			else 
				btnNewButton_2 = new JButton("create event");
			btnNewButton_2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					JFrame a = new SortuEvent(new Vector<Event>(),lengoaia);
					a.setVisible(true);

				}
			});
			btnNewButton_2.setBounds(0, 214, 477, 25);
			jContentPane.add(btnNewButton_2);
			jContentPane.add(getBtnNewButton());
			jContentPane.add(getBtnNewButton_3());
		}
		return jContentPane;
	}

	/**
	 * This method initializes boton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBoton2() {
		if (jButtonCreateQuery == null) {
			jButtonCreateQuery = new JButton();
			if (lengoaia.equals("eus"))
			jButtonCreateQuery.setText("sortu galdera"); //$NON-NLS-1$ //$NON-NLS-2$
			else if(lengoaia.equals("cas"))
				jButtonCreateQuery.setText("crear pregunta");
			else
				jButtonCreateQuery.setText("create question");
			jButtonCreateQuery.setBounds(0, 152, 477, 33);
			jButtonCreateQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame a = new CreateQuestionGUI(new Vector<Event>());
					a.setVisible(true);
				}
			});
		}
		return jButtonCreateQuery;
	}

	private JLabel getLblNewLabel() {
		if (jLabelSelectOption == null) {
			jLabelSelectOption = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("SelectOption"));
			jLabelSelectOption.setBounds(0, 1, 477, 95);
			jLabelSelectOption.setFont(new Font("Tahoma", Font.BOLD, 13));
			jLabelSelectOption.setForeground(Color.BLACK);
			jLabelSelectOption.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return jLabelSelectOption;
	}

	private void redibujar() {
		jLabelSelectOption.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectOption"));
		jButtonCreateQuery.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateQuery"));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainTitle"));
	}

	private JButton getBtnNewButton() {
		if (btnNewButton == null) {
			if(lengoaia.equals("eus"))
				btnNewButton = new JButton("erabiltzaileari dirua sartu"); //$NON-NLS-1$ //$NON-NLS-2$
				else if(lengoaia.equals("cas"))
					btnNewButton = new JButton("meter dinero al usuario");
				else
					btnNewButton = new JButton("give money to the user");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new ErabiltzileariDiruaSartu(lengoaia).setVisible(true);
				}
			});
			btnNewButton.setBounds(0, 241, 477, 25);
		}
		return btnNewButton;
	}

	private JButton getBtnNewButton_3() {
		if (btnNewButton_3 == null) {
			if(lengoaia.equals( "eus"))
			btnNewButton_3 = new JButton("emaitza ipini"); //$NON-NLS-1$ //$NON-NLS-2$
			else if(lengoaia.equals("cas"))
				btnNewButton_3 = new JButton("meter solucion");
			else
				btnNewButton_3 = new JButton("put solution");
			btnNewButton_3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new EmaitzaIpini(lengoaia).setVisible(true);
				}
			});
			btnNewButton_3.setBounds(0, 267, 477, 25);
		}
		return btnNewButton_3;
	}
} // @jve:decl-index=0:visual-constraint="0,0"
