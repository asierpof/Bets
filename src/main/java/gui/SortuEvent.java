package gui;

import java.text.DateFormat;
import java.util.*;
import java.util.List;

import javax.swing.*;

import com.toedter.calendar.JCalendar;

import Iterator.ExtendedIterator;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.Event;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;

public class SortuEvent extends JFrame {
	private static final long serialVersionUID = 1L;
	DefaultComboBoxModel<Event> modelEvents = new DefaultComboBoxModel<Event>();
	private JLabel jLabelQuery ;
	private JLabel jLabelEventDate ;
	private JLabel jLabelListOfEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ListEvents"));
	private JTextField jTextFieldQuery = new JTextField();
	private JCalendar jCalendar = new JCalendar();
	private Calendar calendarMio = null;
	private Date data;
	private JScrollPane scrollPaneEvents = new JScrollPane();
	private BLFacade wsl = MainGUI.getBusinessLogic();
	private JButton jButtonCreate = new JButton("Sortu Gertaera");
	private JLabel jLabelMsg = new JLabel();
	private JLabel jLabelError = new JLabel();
	private String lengoaia;
	public SortuEvent(Vector<domain.Event> v, String l) {
		this.lengoaia =l;
		try {
			jbInit(v);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit(Vector<domain.Event> v) throws Exception {
		if(lengoaia.equals("eus"))
			jLabelEventDate =new JLabel("data");
			else if(lengoaia.equals("cas"))
			jLabelEventDate =new JLabel("fecha");	
			else
				jLabelEventDate =new JLabel("date");
		if(lengoaia.equals("eus"))
			jLabelQuery =new JLabel("gertaera");
			else if(lengoaia.equals("cas"))
				jLabelQuery =new JLabel("evento");
			else
				jLabelQuery =new JLabel("event");
		
		if(lengoaia.equals( "eus"))
			jButtonCreate = new JButton("sortu gertaera");
		else if(lengoaia.equals("cas"))
			jButtonCreate = new JButton("crear evento");
		else
				jButtonCreate = new JButton("create event");

		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(604, 370));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("CreateQuery"));
		jLabelQuery.setBounds(new Rectangle(25, 211, 75, 20));
		jTextFieldQuery.setBounds(new Rectangle(100, 211, 429, 20));

		jCalendar.setBounds(new Rectangle(40, 50, 225, 150));
		scrollPaneEvents.setBounds(new Rectangle(25, 44, 346, 116));

		jButtonCreate.setBounds(new Rectangle(202, 273, 130, 30));

		jButtonCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonCreate_actionPerformed(e);
			}
		});

		jLabelMsg.setBounds(new Rectangle(275, 182, 305, 20));
		jLabelMsg.setForeground(Color.red);
		// jLabelMsg.setSize(new Dimension(305, 20));

		jLabelError.setBounds(new Rectangle(175, 240, 305, 20));
		jLabelError.setForeground(Color.red);

		this.getContentPane().add(jLabelMsg, null);
		this.getContentPane().add(jLabelError, null);
		this.getContentPane().add(jButtonCreate, null);
		this.getContentPane().add(jTextFieldQuery, null);
		this.getContentPane().add(jLabelQuery, null);

		this.getContentPane().add(jCalendar, null);

		jLabelEventDate.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelEventDate.setBounds(40, 16, 140, 25);
		getContentPane().add(jLabelEventDate);

		// Code for JCalendar
		this.jCalendar.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent propertychangeevent) {
//				this.jCalendar.addPropertyChangeListener(new PropertyChangeListener() {
//					public void propertyChange(PropertyChangeEvent propertychangeevent) {
				if (propertychangeevent.getPropertyName().equals("locale")) {
					jCalendar.setLocale((Locale) propertychangeevent.getNewValue());
				} else if (propertychangeevent.getPropertyName().equals("calendar")) {
					calendarMio = (Calendar) propertychangeevent.getNewValue();
					DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar.getLocale());
					jCalendar.setCalendar(calendarMio);
					Date firstDay = UtilDate.trim(new Date(jCalendar.getCalendar().getTime().getTime()));

					try {

						ExtendedIterator<Event> event =  wsl.getEvents(firstDay);
						event.goFirst();
						if (!event.hasNext())
							jLabelListOfEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("NoEvents")
									+ ": " + dateformat1.format(calendarMio.getTime()));
						else
							jLabelListOfEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("Events") + ": "
									+ dateformat1.format(calendarMio.getTime()));
						data = calendarMio.getTime();
						System.out.println(calendarMio.getTime());
						System.out.println("Events " + event);
						Event ev;
						event.goFirst();
						 while (event.hasNext()){
						 ev=(Event) event.next();
							modelEvents.addElement(ev);
						 }
					} catch (Exception e1) {

						jLabelError.setText(e1.getMessage());
					}

				}
				paintDaysWithEvents(jCalendar);
			}
		});
	}

	public static void paintDaysWithEvents(JCalendar jCalendar) {
		// For each day in current month, it is checked if there are events, and in that
		// case, the background color for that day is changed.

		BLFacade facade = MainGUI.getBusinessLogic();

		List<Date> dates = facade.getEventsMonth(jCalendar.getDate());

		Calendar calendar = jCalendar.getCalendar();

		int month = calendar.get(Calendar.MONTH);
		// int today=calendar.get(Calendar.DAY_OF_MONTH);

		calendar.set(Calendar.DAY_OF_MONTH, 1);
		int offset = calendar.get(Calendar.DAY_OF_WEEK);

		if (Locale.getDefault().equals(new Locale("es")))
			offset += 4;
		else
			offset += 5;

		for (Date d : dates) {

			calendar.setTime(d);
			System.out.println(d);

			// Obtain the component of the day in the panel of the DayChooser of the
			// JCalendar.
			// The component is located after the decorator buttons of "Sun", "Mon",... or
			// "Lun", "Mar"...,
			// the empty days before day 1 of month, and all the days previous to each day.
			// That number of components is calculated with "offset" and is different in
			// English and Spanish
//			    		  Component o=(Component) jCalendar.getDayChooser().getDayPanel().getComponent(i+offset);; 
			Component o = (Component) jCalendar.getDayChooser().getDayPanel()
					.getComponent(calendar.get(Calendar.DAY_OF_MONTH) + offset);
			o.setBackground(Color.CYAN);
		}

		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.MONTH, month);

	}

	private void jButtonCreate_actionPerformed(ActionEvent e) {

		try {
			jLabelError.setText("");
			jLabelMsg.setText("");

			// Displays an exception if the query field is empty
			String inputQuery = jTextFieldQuery.getText();

			if (inputQuery.length() > 0) {

				// It could be to trigger an exception if the introduced string is not a number

				System.out.println(data);
				data.setHours(0);
				data.setMinutes(0);
				data.setSeconds(0);
				// Obtain the business logic from a StartWindow class (local or remote)
				wsl.sortuGertaera(inputQuery, data);
				jLabelMsg.setText("ondo sortu da");

			} else
				if(lengoaia.equals( "eus"))
				jLabelError.setText("ezin izan da sortu");
				else if(lengoaia.equals("cas"))
					jLabelError.setText("no se ha podido crear");
				else
					jLabelError.setText("cant be create");
					
//		} catch (EventFinished e1) {
//			jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("ErrorEventHasFinished") + ": "
//					+ event.getDescription());
//		} catch (QuestionAlreadyExist e1) {
//			jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("ErrorQueryAlreadyExist"));
		} catch (java.lang.NumberFormatException e1) {
			jLabelError.setText(ResourceBundle.getBundle("Etiquetas").getString("ErrorNumber"));
		} catch (Exception e1) {

			e1.printStackTrace();

		}
	}
}