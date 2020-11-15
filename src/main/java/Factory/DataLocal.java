package Factory;

import java.net.MalformedURLException;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import configuration.ConfigXML;
import dataAccess.DataAccess;

public class DataLocal implements Data{

	public BLFacade dataBase() throws MalformedURLException {
		ConfigXML c = ConfigXML.getInstance();
		DataAccess da= new DataAccess(c.getDataBaseOpenMode().equals("initialize"));
		return new BLFacadeImplementation(da);
	}

}
