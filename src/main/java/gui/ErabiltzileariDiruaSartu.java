package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import domain.*;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class ErabiltzileariDiruaSartu extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JComboBox<String> comboBox;
	private JLabel lblNewLabel_2;
	private BLFacade wsl = MainGUI.getBusinessLogic();
	private String lengoaia;

	/**
	 * Launch the application.
	 */


	/**
	 * Create the frame.
	 */
	public ErabiltzileariDiruaSartu(String l) {
		this.lengoaia=l;
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent arg0) {
				List<NormalUser> zerrenda = wsl.lortuErabiltzaileNormalak();
				for (NormalUser n : zerrenda)
					comboBox.addItem(n.getErabiltzailea());
			}
		});
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 532, 356);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel ;
		if(lengoaia.equals("eus"))
			lblNewLabel = new JLabel("Erabiltzailea:");
			else if(lengoaia.equals("cas"))
				lblNewLabel = new JLabel("Usuario:");
			else
				lblNewLabel = new JLabel("User:");
		lblNewLabel.setBounds(68, 37, 94, 16);
		contentPane.add(lblNewLabel);

		comboBox = new JComboBox();
		comboBox.setBounds(312, 36, 145, 22);
		contentPane.add(comboBox);

		JLabel lblNewLabel_1 = new JLabel("Sartu nahi duzun diru kantitatea:");;
		if(lengoaia.equals("eus"))
			lblNewLabel_1 = new JLabel("Sartu nahi duzun diru kantitatea:");
			else if(lengoaia.equals("cas"))
				lblNewLabel_1 = new JLabel("mete la cantidad una cantidad de dinero:");
			else
				lblNewLabel_1 = new JLabel("put money quantity:");
		
		lblNewLabel_1.setBounds(12, 120, 218, 16);
		contentPane.add(lblNewLabel_1);

		textField = new JTextField();
		textField.setBounds(312, 117, 145, 22);
		contentPane.add(textField);
		textField.setColumns(10);

		JButton btnNewButton;
		if(lengoaia.equals("eus"))
			btnNewButton = new JButton("Sartu dirua");
		else if(lengoaia.equals("cas"))
			btnNewButton = new JButton("meter dinero");
		else
			btnNewButton = new JButton("put money");
			
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean egin = wsl.AdmindiruaSartu((String) comboBox.getSelectedItem(),
						Double.parseDouble(textField.getText()));
				if (egin)
					if(lengoaia.equals("eus"))
						lblNewLabel_2.setText("dirua ondo sartu da");
					else if(lengoaia.equals("cas"))
						lblNewLabel_2.setText("el dinero se ha metido bien");
					else
						lblNewLabel_2.setText("money is puting well");
					
				else
					if(lengoaia.equals("eus"))
						lblNewLabel_2.setText("sartu diru kantitate egoki bat");
					else if(lengoaia.equals("cas"))
						lblNewLabel_2.setText("mete una cantidad de dinero valida");
					else
						lblNewLabel_2.setText("put valid money quantity");
					
			}
		});
		btnNewButton.setBounds(169, 189, 97, 25);
		contentPane.add(btnNewButton);

		lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setForeground(Color.RED);
		lblNewLabel_2.setBounds(54, 227, 321, 16);
		contentPane.add(lblNewLabel_2);
	}
}
