package gui;

import java.awt.Color;
import java.net.URL;
import java.util.Locale;

import javax.swing.UIManager;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import Factory.Data;
import Factory.DataLocal;
import Factory.DataRemote;
import configuration.ConfigXML;
import dataAccess.DataAccess;
import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;

public class ApplicationLauncher {

	public static void main(String[] args) {

		ConfigXML c = ConfigXML.getInstance();

		System.out.println(c.getLocale());

		Locale.setDefault(new Locale(c.getLocale()));

		System.out.println("Locale: " + Locale.getDefault());
		Data dataLocal ;
		MainGUI a = new MainGUI();
		a.setVisible(true);

		try {

			BLFacade appFacadeInterface;
//			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
//			UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");

			if (c.isBusinessLogicLocal()) {
				dataLocal = new DataLocal();
				appFacadeInterface=dataLocal.dataBase();
				} 

			else { // If remote
				dataLocal = new DataRemote();
				appFacadeInterface = dataLocal.dataBase();
			}
			/*
			 * if (c.getDataBaseOpenMode().equals("initialize"))
			 * appFacadeInterface.initializeBD();
			 */
			MainGUI.setBussinessLogic(appFacadeInterface);

		} catch (Exception e) {
			a.jLabelSelectOption.setText("Error: " + e.toString());
			a.jLabelSelectOption.setForeground(Color.RED);
			System.out.println("Error in ApplicationLauncher: " + e.toString());
		}
		// a.pack();

	}

}
