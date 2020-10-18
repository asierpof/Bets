package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import dataAccess.DataAccess;
import domain.Admin;
import domain.user;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	private  BLFacade wsl = MainGUI.getBusinessLogic();
	private String lengoaia;
	/**
	 * Launch the application.
	 */


	/**
	 * Create the frame.
	 */
	public Login(String l) {
		this.lengoaia=l;
		addWindowListener(new WindowAdapter() {
			@Override
		            public void windowClosing(java.awt.event.WindowEvent e) {
		                
			}
		});
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(188, 24, 200, 22);
		contentPane.add(textField);
		textField.setColumns(10);
		JLabel lblErabiltzailea;
		if(lengoaia.equals("eus"))
		lblErabiltzailea = new JLabel("Erabiltzailea:");
		else if(lengoaia.equals("cas"))
			 lblErabiltzailea = new JLabel("Usuario:");
		else
			 lblErabiltzailea = new JLabel("User:");
		lblErabiltzailea.setBounds(91, 27, 85, 16);
		contentPane.add(lblErabiltzailea);
		
		JLabel lblPasahitza ;
		if(lengoaia.equals("eus"))
			lblPasahitza = new JLabel("Pasahitza:");
			else if(lengoaia.equals("cas"))
				lblPasahitza = new JLabel("Contraseña:");
			else
				lblPasahitza = new JLabel("Password:");
		lblPasahitza.setBounds(91, 83, 85, 16);
		contentPane.add(lblPasahitza);
		
		JButton btnErregistratu;
		if(lengoaia.equals("eus"))
			btnErregistratu = new JButton("Erregistratu");
			else if(lengoaia.equals("cas"))
				btnErregistratu = new JButton("Registrarse");
			else
				btnErregistratu = new JButton("Register");
		
		btnErregistratu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Erregistratu(lengoaia).setVisible(true);
			}
		});
		btnErregistratu.setBounds(41, 191, 115, 25);
		contentPane.add(btnErregistratu);
		passwordField = new JPasswordField();
		passwordField.setBounds(188, 80, 200, 22);
		contentPane.add(passwordField);
		
		JButton btnSartu = new JButton("Sartu");
		if(lengoaia.equals("eus"))
			btnSartu = new JButton("Sartu");
			else if(lengoaia.equals("cas"))
				btnSartu = new JButton("Entrar");
			else
				btnSartu = new JButton("Enter");
		btnSartu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String pasahitza=new String(passwordField.getPassword());
				if(!wsl.egiaztatuErab(textField.getText(),pasahitza)) {
					if(lengoaia.equals("eus"))
					JOptionPane.showMessageDialog(new JFrame(), "erabiltzaile edo pasahitza okerra.");
					else if(lengoaia.equals("cas"))
						JOptionPane.showMessageDialog(new JFrame(), "usuario o contraseña erronea.");
					else
						JOptionPane.showMessageDialog(new JFrame(), "wrong usero or password.");
				}
				else {
					user erab= wsl.lortuErab(textField.getText());
					if(lengoaia.equals("eus"))
					JOptionPane.showMessageDialog(new JFrame(), "login egin duzu.");
					else if(lengoaia.equals("cas"))
						JOptionPane.showMessageDialog(new JFrame(), "has logueado.");
					else
						JOptionPane.showMessageDialog(new JFrame(), "good login.");
					if (erab instanceof Admin)
						new AdministratzailePanela(lengoaia).setVisible(true);
					else
						new ErregistratuNormala(textField.getText(),lengoaia).setVisible(true);
				}
			}
		});
		btnSartu.setBounds(271, 191, 97, 25);
		contentPane.add(btnSartu);
		
		
	}
}
