package Factory;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import businessLogic.BLFacade;

public interface Data {
	public BLFacade dataBase() throws MalformedURLException ;

}
