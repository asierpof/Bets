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

public class ErregistratuNormala extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	private JButton jButtonQueryQueries = null;

	private static BLFacade appFacadeInterface;

	public static BLFacade getBusinessLogic() {
		return appFacadeInterface;
	}

	public static void setBussinessLogic(BLFacade afi) {
		appFacadeInterface = afi;
	}
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private String erabiltzailea;
	private JButton ezabatuApustuak;
	private String lengoaia;

	/**
	 * This is the default constructor
	 */
	public ErregistratuNormala(String erab,String l) {
		super();
		this.erabiltzailea = erab;
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
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
			jContentPane.add(getBoton3());

			JButton btnNewButton = new JButton("ikusi apustuak"); //$NON-NLS-1$ //$NON-NLS-2$
			if(lengoaia.equals("eus"))
				 btnNewButton = new JButton("ikusi apustuak");
			else  if(lengoaia.equals("cas"))
				btnNewButton = new JButton("ver apuestas");
			else
				btnNewButton = new JButton("view bets");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					new ApustuakIkusi(erabiltzailea,lengoaia).setVisible(true);
				}
			});
			btnNewButton.setBounds(0, 131, 477, 25);
			jContentPane.add(btnNewButton);
			jContentPane.add(getEzabatuApustuak());
		}
		return jContentPane;
	}

	/**
	 * This method initializes boton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBoton3() {
		if (jButtonQueryQueries == null) {
			if(lengoaia.equals("eus"))
			jButtonQueryQueries = new JButton("apustua egin");
			else if(lengoaia.equals("cas"))
				jButtonQueryQueries = new JButton("hacer apuesta");
			else
				jButtonQueryQueries = new JButton("bet");
			jButtonQueryQueries.setBounds(0, 96, 477, 33);
			jButtonQueryQueries.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame a = new ApustuaEgin(erabiltzailea,lengoaia);

					a.setVisible(true);
				}
			});
		}
		return jButtonQueryQueries;
	}

	private void redibujar() {
		
		jButtonQueryQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("QueryQueries"));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainTitle"));
	}

	private JButton getEzabatuApustuak() {
		if (ezabatuApustuak == null) {
			if(lengoaia.equals( "eus"))
				ezabatuApustuak = new JButton("ezabatu apustua");
			else if(lengoaia.equals("cas"))
				ezabatuApustuak = new JButton("eliminar apuesta");
			else
				ezabatuApustuak = new JButton("remove bet");
			ezabatuApustuak.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					new ApustuaEzabatu(erabiltzailea,lengoaia).setVisible(true);
				}
			});
			ezabatuApustuak.setBounds(0, 157, 477, 25);
		}
		return ezabatuApustuak;
	}
}