package gui;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import configuration.UtilDate;

import com.toedter.calendar.JCalendar;

import Iterator.ExtendedIterator;
import domain.Apustua;
import domain.Event;
import domain.Kuota;
import domain.Question;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.text.DateFormat;
import java.util.*;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class ApustuaEgin extends JFrame {
	private static final long serialVersionUID = 1L;

	private JLabel jLabelEventDate ;
	private JLabel jLabelQueries ;
	private JLabel jLabelEvents ;

	// Code for JCalendar
	private JCalendar jCalendar1 = new JCalendar();
	private Calendar calendarMio = null;
	private JScrollPane scrollPaneEvents = new JScrollPane();
	private JScrollPane scrollPaneQueries = new JScrollPane();

	private JTable tableEvents = new JTable();
	private JTable tableQueries = new JTable();
	private Date data;
	private DefaultTableModel tableModelEvents;
	private DefaultTableModel tableModelQueries;
	private JComboBox<Kuota> comboBox;
	private String erabiltzailea;
	private JLabel gehiLabel;
	private Question quest;
	private String lengoaia;
	private java.util.List<Kuota> kuota = new java.util.ArrayList<Kuota>();
	private JTextField textField = new JTextField();
	private BLFacade wsl = MainGUI.getBusinessLogic();
	private String[] columnNamesEvents = new String[] { ResourceBundle.getBundle("Etiquetas").getString("EventN"),
			ResourceBundle.getBundle("Etiquetas").getString("Event"),

	};
	private String[] columnNamesQueries = new String[] { ResourceBundle.getBundle("Etiquetas").getString("QueryN"),
			ResourceBundle.getBundle("Etiquetas").getString("Query")

	};

	public ApustuaEgin(String erab,String l) {
		this.erabiltzailea = erab;
		this.lengoaia=l;

		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		
		if(lengoaia.equals( "eus"))
			jLabelEventDate =new JLabel("data");
			else if(lengoaia.equals("cas"))
			jLabelEventDate =new JLabel("fecha");	
			else
				jLabelEventDate =new JLabel("date");
			if(lengoaia.equals("eus"))
			jLabelQueries =new JLabel("galderak");
			else if(lengoaia.equals("cas"))
				jLabelQueries =new JLabel("preguntas");
			else
				jLabelQueries =new JLabel("questions");
			if(lengoaia.equals("eus"))
			jLabelEvents =new JLabel("gertaerak");
			else if(lengoaia.equals("cas"))
				jLabelEvents =new JLabel("eventos");
			else
				jLabelEvents =new JLabel("events");
		this.kuota = new ArrayList<Kuota>();
		this.kuota.clear();
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(700, 500));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("QueryQueries"));

		jLabelEventDate.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelQueries.setBounds(138, 248, 406, 14);
		jLabelEvents.setBounds(295, 19, 259, 16);

		this.getContentPane().add(jLabelEventDate, null);
		this.getContentPane().add(jLabelQueries);
		this.getContentPane().add(jLabelEvents);

		comboBox = new JComboBox<Kuota>();
		comboBox.setBounds(451, 282, 187, 22);
		getContentPane().add(comboBox);

		jCalendar1.setBounds(new Rectangle(40, 50, 225, 150));

		// Code for JCalendar
		this.jCalendar1.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent propertychangeevent) {

				if (propertychangeevent.getPropertyName().equals("locale")) {
					jCalendar1.setLocale((Locale) propertychangeevent.getNewValue());
				} else if (propertychangeevent.getPropertyName().equals("calendar")) {
					calendarMio = (Calendar) propertychangeevent.getNewValue();
					DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar1.getLocale());
					jCalendar1.setCalendar(calendarMio);
					Date firstDay = UtilDate.trim(new Date(jCalendar1.getCalendar().getTime().getTime()));

					try {
						tableModelEvents.setDataVector(null, columnNamesEvents);
						tableModelEvents.setColumnCount(3); // another column added to allocate ev objects

						ExtendedIterator<Event> events =  wsl.getEvents(firstDay);
						events.goFirst();

						if (!events.hasNext())
							jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("NoEvents") + ": "
									+ dateformat1.format(calendarMio.getTime()));
						else
							jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("Events") + ": "
									+ dateformat1.format(calendarMio.getTime()));
						events.goFirst();
						while (events.hasNext()) {
							Event ev = (Event) events.next();
							Vector<Object> row = new Vector<Object>();
							row.add(ev.getEventNumber());
							row.add(ev.getDescription());
							row.add(ev); // ev object added in order to obtain it with tableModelEvents.getValueAt(i,2)
							tableModelEvents.addRow(row);
							data = calendarMio.getTime();
						}
						tableEvents.getColumnModel().getColumn(0).setPreferredWidth(25);
						tableEvents.getColumnModel().getColumn(1).setPreferredWidth(268);
						tableEvents.getColumnModel().removeColumn(tableEvents.getColumnModel().getColumn(2)); // not
																												// shown
																												// in
																												// JTable
					} catch (Exception e1) {

						jLabelQueries.setText(e1.getMessage());
					}

				}
				CreateQuestionGUI.paintDaysWithEvents(jCalendar1);
			}
		});

		this.getContentPane().add(jCalendar1, null);

		scrollPaneEvents.setBounds(new Rectangle(292, 50, 346, 150));
		tableQueries.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				comboBox.removeAllItems();
				BLFacade facade = MainGUI.getBusinessLogic();
				int i = tableQueries.getSelectedRow();
				int id = (Integer) tableModelQueries.getValueAt(i, 0);
				quest = facade.lortuGaldera(id);
				if (quest.getResult().equals("")) {
					ArrayList<Kuota> kuotak = quest.lortuKuotak();
					for (Kuota k : kuotak)
						comboBox.addItem(k);
				}
			}
		});
		scrollPaneQueries.setBounds(new Rectangle(12, 277, 406, 116));

		tableEvents.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				comboBox.removeAllItems();
				int i = tableEvents.getSelectedRow();
				domain.Event ev = (domain.Event) tableModelEvents.getValueAt(i, 2); // obtain ev object
				Vector<Question> queries = ev.getQuestions();

				tableModelQueries.setDataVector(null, columnNamesQueries);

				if (queries.isEmpty())
					jLabelQueries.setText(
							ResourceBundle.getBundle("Etiquetas").getString("NoQueries") + ": " + ev.getDescription());
				else
					jLabelQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectedEvent") + " "
							+ ev.getDescription());

				for (domain.Question q : queries) {
					Vector<Object> row = new Vector<Object>();

					row.add(q.getQuestionNumber());
					row.add(q.getQuestion());
					tableModelQueries.addRow(row);
				}
				tableQueries.getColumnModel().getColumn(0).setPreferredWidth(25);
				tableQueries.getColumnModel().getColumn(1).setPreferredWidth(268);

			}
		});

		scrollPaneEvents.setViewportView(tableEvents);
		tableModelEvents = new DefaultTableModel(null, columnNamesEvents);

		tableEvents.setModel(tableModelEvents);
		tableEvents.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableEvents.getColumnModel().getColumn(1).setPreferredWidth(268);

		scrollPaneQueries.setViewportView(tableQueries);
		tableModelQueries = new DefaultTableModel(null, columnNamesQueries);

		tableQueries.setModel(tableModelQueries);
		tableQueries.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableQueries.getColumnModel().getColumn(1).setPreferredWidth(268);

		this.getContentPane().add(scrollPaneEvents, null);
		this.getContentPane().add(scrollPaneQueries, null);

		textField.setBounds(451, 371, 187, 22);
		getContentPane().add(textField);
		textField.setColumns(10);

		JLabel lblApostatuNahiDuzun ; //$NON-NLS-1$ //$NON-NLS-2$
		if(lengoaia.equals("eus"))
			lblApostatuNahiDuzun = new JLabel("apostatu nahi duzun dirua");
		else if(lengoaia.equals("cas"))
			lblApostatuNahiDuzun = new JLabel("dinero que quieras apostar");
		else
			lblApostatuNahiDuzun = new JLabel("money to bet");
			
		lblApostatuNahiDuzun.setBounds(456, 327, 182, 25);
		getContentPane().add(lblApostatuNahiDuzun);
		
		JButton apustuAnitzaegin ; //$NON-NLS-1$ //$NON-NLS-2$
		if(lengoaia.equals("eus"))
			apustuAnitzaegin = new JButton("apustua egin");
		else if(lengoaia.equals("cas"))
			apustuAnitzaegin = new JButton("hacer apuesta");
		else
			apustuAnitzaegin = new JButton("bet");
		
		apustuAnitzaegin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (kuota.isEmpty())
					if(lengoaia.equals("eus"))
					JOptionPane.showMessageDialog(new JFrame(),
							"apustu anitza egiteko gutxienez apustu bat sartu behar duzu.");
					else if(lengoaia.equals("cas"))
						JOptionPane.showMessageDialog(new JFrame(),
								"para hacer una apuesta, añade una cuota.");
					else
						JOptionPane.showMessageDialog(new JFrame(),
								"plis, add quote.");
				else if (textField.getText().equals(""))
					if(lengoaia.equals("eus"))
						JOptionPane.showMessageDialog(new JFrame(),
								"mesedez sartu diru kantitate bat");
						else if(lengoaia.equals("cas"))
							JOptionPane.showMessageDialog(new JFrame(),
									"mete dinero.");
						else
							JOptionPane.showMessageDialog(new JFrame(),
									"plis, put money.");
					
				else {
					double apustuDirua = Double.parseDouble(textField.getText());
					for (int i = 0; i < kuota.size(); i++)
						if (apustuDirua < kuota.get(i).getQuestion().getBetMinimum())
							if(lengoaia.equals("eus"))
								JOptionPane.showMessageDialog(new JFrame(),
										"apostatutako dirua ezin du minimoa baino txikiagoa izan.");
								else if(lengoaia.equals("cas"))
									JOptionPane.showMessageDialog(new JFrame(),
											"no puedes poner mas dinero del que tienes.");
								else
									JOptionPane.showMessageDialog(new JFrame(),
											"cant put more money than you have.");
					Date gaur = Calendar.getInstance().getTime();
					if (data.compareTo(gaur) > 0) {
						Apustua apustu = new Apustua(apustuDirua, kuota);
						boolean ema = wsl.erabApustuaEgin(erabiltzailea, apustu);
						kuota.clear();
						if (ema) {
							if(lengoaia.equals("eus"))
								JOptionPane.showMessageDialog(new JFrame(),
										"apustua ondo egin da.");
								else if(lengoaia.equals("cas"))
									JOptionPane.showMessageDialog(new JFrame(),
											"la apuesta se ha echo bien.");
								else
									JOptionPane.showMessageDialog(new JFrame(),
											"the bet is doing well.");
						}
						

					} 

				}
			}

		});
		apustuAnitzaegin.setBounds(505, 422, 154, 25);
		getContentPane().add(apustuAnitzaegin);

		JButton apustuanitz = new JButton("gehitu kuota"); //$NON-NLS-1$ //$NON-NLS-2$
		if(lengoaia.equals("eus"))
			apustuanitz = new JButton("gehitu kuota");
			else if(lengoaia.equals("cas"))
				apustuanitz = new JButton("añadir cuota");
			else
				apustuanitz = new JButton("add quote");
		apustuanitz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(lengoaia.equals("eus"))
					gehiLabel.setText("kuota kopurua:" + kuota.size());
					else if(lengoaia.equals("cas"))
						gehiLabel.setText("cantidad de cuotas:" + kuota.size());
					else
						gehiLabel.setText("bets amount:" + kuota.size());
				Kuota kuota1 = (Kuota) comboBox.getSelectedItem();
				boolean egia = true;
				for (int j = 0; j < quest.lortuKuotak().size(); j++)
					for (int i = 0; i < kuota.size(); i++) {
						if (kuota.get(i).equals(quest.lortuKuotak().get(j))) {
							if(lengoaia.equals("eus"))
								JOptionPane.showMessageDialog(new JFrame(),
										"Ezin duzu bi kuota galdera berdinarekin aukeratu.");
								else if(lengoaia.equals("cas"))
									JOptionPane.showMessageDialog(new JFrame(),
											"no puedes seleccionar 2 veces una cuota de la misma pregunta");
								else
									JOptionPane.showMessageDialog(new JFrame(),
											"you cant select 2 quote from the same question.");
							egia = false;
						}
					}
				if (egia) {
					kuota.add(kuota1);
					if(lengoaia.equals("eus"))
						gehiLabel.setText("kuota kopurua:" + kuota.size());
						else if(lengoaia.equals("cas"))
							gehiLabel.setText("cantidad de cuotas:" + kuota.size());
						else
							gehiLabel.setText("bets amount:" + kuota.size());

				}

			}

		});

		apustuanitz.setBounds(288, 422, 130, 25);
		getContentPane().add(apustuanitz);

		gehiLabel = new JLabel(); // $NON-NLS-1$ //$NON-NLS-2$
		gehiLabel.setForeground(Color.RED);
		gehiLabel.setBounds(456, 213, 182, 16);
		getContentPane().add(gehiLabel);

	}
}
