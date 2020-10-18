package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import domain.Apustua;
import domain.Event;
import domain.Kuota;
import domain.KuotaContainer;
import domain.Question;
import domain.QuestionContainer;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ApustuaEzabatu extends JFrame {

	private JPanel contentPane;
	private String erabiltzailea;
	private List<Apustua> apustuZerrenda;
	private BLFacade wsl = MainGUI.getBusinessLogic();
	private JComboBox<Integer> apuBox;
	private JLabel Apudirua = new JLabel("");;
	private Apustua apu;
	private JTable table = new JTable();
	private DefaultTableModel model = (DefaultTableModel) table.getModel();
	private boolean dispose = false;
	private String lengoaia;
	private String[] columnNamesQueries = new String[] {

			"gertaera", "galdera", "kuota"

	};

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public void lehioaItxi() {
		this.dispose();
	}

	public ApustuaEzabatu(String erab,String l) {
		this.erabiltzailea = erab;
		this.lengoaia =l;
		if(lengoaia.equals("eus"))
		columnNamesQueries = new String[] {
					"gertaera", "galdera", "kuota"
			};
		else if(lengoaia.equals("cas"))
			columnNamesQueries = new String[] {
					"evento", "pregunta", "cuota"

			};
			
		else
	columnNamesQueries = new String[] {
					"event", "question", "quote"
			};
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1020, 631);
		contentPane = new JPanel();
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent arg0) {
				apustuZerrenda = wsl.lortuApustuakEmaitzarikGabe(erabiltzailea);
				System.out.println(apustuZerrenda.size());
				for (int i = 0; i < apustuZerrenda.size(); i++)
					apuBox.addItem(i);

			}
		});

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		apuBox = new JComboBox();
		apuBox.setBounds(356, 49, 208, 22);
		contentPane.add(apuBox);
		apuBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (apuBox.getItemCount() != 0) {
					int rowCount = model.getRowCount();
					// Remove rows one by one from the end of the table
					for (int i = rowCount - 1; i >= 0; i--) {
						model.removeRow(i);
					}
					if (apuBox.getItemCount() != 0) {
						int indizea = (Integer) apuBox.getSelectedItem();
						apu = apustuZerrenda.get(indizea);
						if(lengoaia.equals( "eus"))
						Apudirua.setText("apustuan gastatutako dirua : " + apu.getApustuDiru());
						else if(lengoaia.equals( "cas"))
							Apudirua.setText("dinero gastado : " + apu.getApustuDiru());
						else
							Apudirua.setText("money spend : " + apu.getApustuDiru());
						for (Kuota k : apu.getApustuKuota()) {
							KuotaContainer kCont = wsl.LortuKuota(k);
							Question question = kCont.getQuestion();
							QuestionContainer qCont = wsl.LortuQuestion(question);
							Event event = qCont.getEvent();
							model.addRow(new Object[] { event.getDescription(), question.getQuestion(), k.toString() });

						}
					}
				}
			}
		});
		JLabel lblNewLabel = new JLabel("Aukeratu apustua:");
		if(lengoaia.equals("eus"))
		lblNewLabel = new JLabel("Aukeratu apustua:");
		else if(lengoaia.equals( "cas"))
			lblNewLabel = new JLabel("elige apuesta:");
		else
			lblNewLabel = new JLabel("choose bet:");
		lblNewLabel.setBounds(118, 52, 162, 16);
		contentPane.add(lblNewLabel);
		JButton btnNewButton = new JButton("Ezabatu apustua");
		if(lengoaia.equals( "eus"))
		btnNewButton = new JButton("Ezabatu apustua");
		else if(lengoaia.equals("cas"))
			btnNewButton = new JButton("borrar apuesta");
		else
			btnNewButton = new JButton("remove bet");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!apuBox.getUIClassID().isEmpty())
					wsl.ezabatuApustua(apu.getId(), erabiltzailea);
				if(lengoaia .equals( "eus"))
				JOptionPane.showMessageDialog(new JFrame(), "apustua ondo ezabatu da.");
				else if(lengoaia.equals("cas"))
					JOptionPane.showMessageDialog(new JFrame(), "apuesta eliminada.");
				else
					JOptionPane.showMessageDialog(new JFrame(), "bet removed.");
				ApustuaEzabatu main = new ApustuaEzabatu(erabiltzailea,lengoaia);

				main.setVisible(true);
				lehioaItxi();

			}
		});
		btnNewButton.setBounds(748, 48, 162, 25);
		contentPane.add(btnNewButton);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(103, 173, 826, 228);
		contentPane.add(scrollPane);

		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "gertaera", "galdera", "kuota" }));
		scrollPane.setColumnHeaderView(table);
		Apudirua.setBounds(118, 130, 251, 16);
		contentPane.add(Apudirua);

		model = new DefaultTableModel(null, columnNamesQueries);
		table.setModel(model);
		table.getColumnModel().getColumn(0);
		table.getColumnModel().getColumn(1);
		table.getColumnModel().getColumn(2);
	}
}
