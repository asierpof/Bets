package gui;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import configuration.UtilDate;

import com.toedter.calendar.JCalendar;

import domain.Apustua;
import domain.Kuota;
import domain.KuotaContainer;
import domain.NormalUser;
import domain.Question;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.text.DateFormat;
import java.util.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class EmaitzaIpini extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private  JLabel jLabelEventDate ;
	private  JLabel jLabelQueries ;
	private  JLabel jLabelEvents ;

	// Code for JCalendar
	private JCalendar jCalendar1 = new JCalendar();
	private Calendar calendarMio = null;
	private JScrollPane scrollPaneEvents = new JScrollPane();
	private JScrollPane scrollPaneQueries = new JScrollPane();

	private JTable tableEvents = new JTable();
	private JTable tableQueries = new JTable();

	private DefaultTableModel tableModelEvents;
	private DefaultTableModel tableModelQueries;
	private JComboBox<Kuota> comboBox;
	private Question quest;
	private String lengoaia;
	private BLFacade wsl = MainGUI.getBusinessLogic();
	private String[] columnNamesEvents = new String[] { ResourceBundle.getBundle("Etiquetas").getString("EventN"),
			ResourceBundle.getBundle("Etiquetas").getString("Event"),

	};
	private String[] columnNamesQueries = new String[] { ResourceBundle.getBundle("Etiquetas").getString("QueryN"),
			ResourceBundle.getBundle("Etiquetas").getString("Query")

	};

	public EmaitzaIpini(String l) {
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
		else if(lengoaia.equals( "cas"))
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
		

		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(700, 500));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("QueryQueries"));

		jLabelEventDate.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelQueries.setBounds(138, 248, 110, 14);
		jLabelEvents.setBounds(295, 19, 259, 16);

		this.getContentPane().add(jLabelEventDate, null);
		this.getContentPane().add(jLabelQueries);
		this.getContentPane().add(jLabelEvents);

		comboBox = new JComboBox();
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

						BLFacade facade = MainGUI.getBusinessLogic();

						List<domain.Event> events = wsl.getEvents(calendarMio.getTime());

						if (events.isEmpty())
							jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("NoEvents") + ": "
									+ dateformat1.format(calendarMio.getTime()));
						else
							jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("Events") + ": "
									+ dateformat1.format(calendarMio.getTime()));
						for (domain.Event ev : events) {
							Vector<Object> row = new Vector<Object>();

							System.out.println("Events " + ev);

							row.add(ev.getEventNumber());
							row.add(ev.getDescription());
							row.add(ev); // ev object added in order to obtain it with tableModelEvents.getValueAt(i,2)
							tableModelEvents.addRow(row);
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
				ArrayList<Kuota> kuotak = quest.lortuKuotak();
				for (Kuota k : kuotak)
					comboBox.addItem(k);
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
					if (q.getResult().equals("")) {
						Vector<Object> row = new Vector<Object>();

						row.add(q.getQuestionNumber());
						row.add(q.getQuestion());
						tableModelQueries.addRow(row);
					}
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
		
		JButton emaitzaBotoia = new JButton("Emaitza ipini"); //$NON-NLS-1$ //$NON-NLS-2$
		if(lengoaia.equals("eus"))
			emaitzaBotoia = new JButton("Emaitza ipini");
		else if(lengoaia.equals("cas"))
			emaitzaBotoia = new JButton("poner resultado");
		else
			emaitzaBotoia = new JButton("put result");
		
		emaitzaBotoia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int id = quest.getQuestionNumber();
				String[] ema = comboBox.getSelectedItem().toString().split(" ");
				wsl.ipiniEmaitza(id, ema[0]);
				List<NormalUser> erabiltzaileak = wsl.lortuErabiltzaileNormalak();

				for (NormalUser er : erabiltzaileak) {
					for (Apustua apu : er.lortuApustuak()) {
						boolean apustuaExist = false;
						boolean apustuakOndo = true;
						Double multiplikadorea = 1.0;
						for (Kuota k : apu.getApustuKuota()) {
							multiplikadorea = multiplikadorea * k.getPortzentaila();
							KuotaContainer kCon = wsl.LortuKuota(k);
							if (!(kCon.getQuestion().getResult().equals(k.getPronostikoa()))) {
								apustuakOndo = false;
								break;
							}
							if (kCon.getQuestion().getQuestionNumber() == quest.getQuestionNumber()
									&& k.getPronostikoa().equals(ema[0]))
								apustuaExist = true;

						}
						wsl.diruaSartu(er.getErabiltzailea(), apu.getApustuDiru() * multiplikadorea);
					}
				}
				if(lengoaia.equals("eus"))
				JOptionPane.showMessageDialog(new JFrame(), "emaitza ongi ipini da.");
				else if(lengoaia.equals("cas"))
					JOptionPane.showMessageDialog(new JFrame(), "el resultado se ha puesto bien.");
				else
					JOptionPane.showMessageDialog(new JFrame(), "the result has been put well.");
			}

		});
		emaitzaBotoia.setBounds(494, 419, 130, 30);
		getContentPane().add(emaitzaBotoia);

		JLabel lblNewLabel ; //$NON-NLS-1$ //$NON-NLS-2$
		if(lengoaia.equals("eus"))
			lblNewLabel = new JLabel("emaitza");
		else if(lengoaia.equals("cas"))
			lblNewLabel = new JLabel("resultado");
		else
		lblNewLabel = new JLabel("result");
		lblNewLabel.setBounds(451, 247, 56, 16);
		getContentPane().add(lblNewLabel);

	}
}
