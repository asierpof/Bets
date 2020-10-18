package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import dataAccess.DataAccess;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;

public class Erregistratu extends JFrame {
	private BLFacade wsl = MainGUI.getBusinessLogic();
	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	private String lengoaia;

	/**
	 * Launch the application.
	 */


	/**
	 * Create the frame.
	 */
	public Erregistratu(String l) {
		this.lengoaia=l;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textField = new JTextField();
		textField.setBounds(203, 30, 189, 22);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblErabiltzailea ;
		if(lengoaia.equals("eus"))
			lblErabiltzailea = new JLabel("Erabiltzailea:");
		else if(lengoaia.equals("cas"))
			lblErabiltzailea = new JLabel("Usuario:");
		else
			lblErabiltzailea = new JLabel("User:");
		lblErabiltzailea.setBounds(69, 33, 101, 16);
		contentPane.add(lblErabiltzailea);

		JLabel lblPasahitza ;
		if(lengoaia.equals("eus"))
			lblPasahitza = new JLabel("Pasahitza:");
		else if(lengoaia.equals("cas"))
			lblPasahitza = new JLabel("Contraseña:");
		else
			lblPasahitza = new JLabel("Password:");
		
		lblPasahitza.setBounds(69, 91, 85, 16);
		contentPane.add(lblPasahitza);

		JLabel lblErrepikatuPasahitza = new JLabel("Errepikatu pasahitza:");
		if(lengoaia.equals("eus"))
			lblErrepikatuPasahitza = new JLabel("Errepikatu Pasahitza:");
		else if(lengoaia.equals("cas"))
			lblErrepikatuPasahitza = new JLabel("Repite Contraseña:");
		else
			lblErrepikatuPasahitza = new JLabel("Repeat Password:");
		
		lblErrepikatuPasahitza.setBounds(26, 135, 141, 16);
		contentPane.add(lblErrepikatuPasahitza);

		JButton btnErregistratu ;
		if(lengoaia.equals("eus"))
			btnErregistratu = new JButton("Erregistratu");
			else if(lengoaia.equals("cas"))
				btnErregistratu = new JButton("Registrarse");
			else
				btnErregistratu = new JButton("Register");
		btnErregistratu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String pasahitza1 = new String(passwordField.getPassword());
				String pasahitza2 = new String(passwordField_1.getPassword());
				if (!pasahitza1.equals(pasahitza2) || textField.getText().equals( "") || pasahitza1.equals( ""))
					if(lengoaia.equals("eus"))
					JOptionPane.showMessageDialog(new JFrame(),
							"pasahitzak gaizki sartu dituzu, erabiltzailea ez duzu sartu edo pasahitzaren bat ez duzu sartu.");
					else if(lengoaia.equals("cas"))
						JOptionPane.showMessageDialog(new JFrame(),
								"has metido mal la contraseña o el usuario.");
					else
						JOptionPane.showMessageDialog(new JFrame(),
								"wrong user or password.");
						
				else {
					if (wsl.erabiltzaileaErreg(textField.getText(), pasahitza1, false))
						JOptionPane.showMessageDialog(new JFrame(), "ondo erregistratu zara");
					else
						JOptionPane.showMessageDialog(new JFrame(), "erabiltzaile hori existitzen da");
				}
			}
		});
		btnErregistratu.setBounds(167, 215, 107, 25);
		contentPane.add(btnErregistratu);

		passwordField = new JPasswordField();
		passwordField.setBounds(203, 88, 189, 22);
		contentPane.add(passwordField);

		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(203, 132, 189, 22);
		contentPane.add(passwordField_1);

	}
}
