package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.JLabel;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import businessLogic.*;
import domain.*;

public class ApustuakIkusi extends JFrame {

	private JPanel contentPane;
	private JTable table = new JTable();
	private String erabiltzailea;
	private BLFacade wsl = MainGUI.getBusinessLogic();
	private JLabel diruLabel;
	private String lengoaia;
	private DefaultTableModel model = (DefaultTableModel) table.getModel();
	private String[] columnNamesQueries = new String[] { "kuota kant", "gertaera", "galdera", "emaitza", "kuota",
			"dirua", "etekina"

	};

	/**
	 * Create the frame.
	 */
	public ApustuakIkusi(String erab,String l) {
		this.lengoaia=l;
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				model.setDataVector(null, columnNamesQueries);
				NormalUser er = (NormalUser) wsl.lortuErab(erabiltzailea);
				diruLabel.setText(er.getDirua().toString());
				for (double dr : er.lortuAdminSartutakoDirua()) {
					model.addRow(new Object[] { " ", "admin", " ", " ", " ", "", dr });
				}
				ArrayList<Apustua> apustuak = er.lortuApustuak();
				for (Apustua apu : apustuak) {
					model.addRow(
							new Object[] { apu.getApustuKuota().size(), "", " ", " ", " ", apu.getApustuDiru(), "" });
					int konta = 0;
					boolean emaitzakOngi = true;
					boolean emaitzarik = true;
					Double etekina = apu.getApustuDiru();
					for (Kuota k : apu.getApustuKuota()) {
						konta++;
						etekina = etekina * k.getPortzentaila();
						KuotaContainer kCont = wsl.LortuKuota(k);
						Question question = kCont.getQuestion();
						QuestionContainer qCont = wsl.LortuQuestion(question);
						Event event = qCont.getEvent();
						if (!question.getResult().equals(k.getPronostikoa()))
							emaitzakOngi = false;
						if (question.getResult().equals( ""))
							emaitzarik = false;
						if (konta < apu.getApustuKuota().size()) {
							model.addRow(new Object[] { "", event.getDescription() + event.getEventDate(), question.getQuestion(),
									question.getResult(), k.toString(), "", "" });
						}
						if (konta == apu.getApustuKuota().size() && emaitzakOngi && emaitzarik) {
							model.addRow(new Object[] { "", event.getDescription() + event.getEventDate(), question.getQuestion(),
									question.getResult(), k.toString(), "", etekina - apu.getApustuDiru() });
						}
						if (konta == apu.getApustuKuota().size() && !emaitzakOngi) {
							model.addRow(new Object[] { "", event.getDescription()+event.getEventDate(), question.getQuestion(),
									question.getResult(), k.toString(), "", -apu.getApustuDiru() });
						}
						if (konta == apu.getApustuKuota().size() && emaitzakOngi && !emaitzarik) {
							model.addRow(new Object[] { "", event.getDescription()+event.getEventDate(), question.getQuestion(),
									question.getResult(), k.toString(), "", "" });
						}

					}
				}

			}

		});
		this.erabiltzailea = erab;
		
		if(lengoaia.equals("eus"))
			columnNamesQueries = new String[] { "kuota kant", "gertaera", "galdera", "emaitza", "kuota",
					"dirua", "etekina"

			};
		else if(lengoaia.equals("cas"))
			columnNamesQueries = new String[] { "cant cuota", "evento", "pregunta", "resultado", "cuota",
					"dinero", "ganancia"

			};
		else
			columnNamesQueries = new String[] { "quote quality", "event", "question", "result", "quote",
					"money", "earnings"

			};
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1002, 506);
		contentPane = new JPanel();
		contentPane.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent arg0) {

			}

		});
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(44, 112, 862, 297);
		contentPane.add(scrollPane);

		table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "gertaera", "galdera", "emaitza", "kuota", "dirua", "etekina" }) {
			Class[] columnTypes = new Class[] { String.class, String.class, String.class, String.class, double.class,
					double.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});

		scrollPane.setViewportView(table);

		model = new DefaultTableModel(null, columnNamesQueries);
		table.setModel(model);
		table.getColumnModel().getColumn(0);
		table.getColumnModel().getColumn(1);
		table.getColumnModel().getColumn(2);
		table.getColumnModel().getColumn(3);
		table.getColumnModel().getColumn(4);
		table.getColumnModel().getColumn(5);

		JLabel lblNewLabel = new JLabel("Momentuan duzun dirua:");
		if (lengoaia.equals("eus"))
			lblNewLabel = new JLabel("Momentuan duzun dirua:");
		else if(lengoaia.equals("cas"))
			lblNewLabel = new JLabel("Dinero que tienes:");
		else
			lblNewLabel = new JLabel("Money you have:");
		lblNewLabel.setBounds(44, 13, 167, 16);
		contentPane.add(lblNewLabel);

		diruLabel = new JLabel("");
		diruLabel.setBounds(253, 13, 56, 16);
		contentPane.add(diruLabel);
	}
}
