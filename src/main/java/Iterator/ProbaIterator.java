package Iterator;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import Factory.Data;
import Factory.DataLocal;
import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import domain.Event;

public class ProbaIterator {
	public static void main (String[] args) throws ParseException, MalformedURLException {
	 //Facade objektua lortu lehendabiziko ariketa erabiliz
		Data d = new DataLocal();
	 BLFacade facadeInterface=d.dataBase();
	String eguna = "16/11/2020";
   String pattern = "dd/MM/yyyy";
   SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
   Date data = simpleDateFormat.parse(eguna);
   ExtendedIterator<Event> i;

   
	 i = facadeInterface.getEvents(data);
	 Event ev;
	i.goLast();
	 while (i.hasPrevious()){
	 ev=(Event) i.previous();
	 System.out.println(ev.toString());
	 }
	 //Nahiz eta suposatu hasierara ailegatu garela, eragiketa egiten dugu.
	 System.out.println();
	 i.goFirst();
	 while (i.hasNext()){
	 ev=(Event) i.next();
	 System.out.println(ev.toString());
	 	}
	 }

}
