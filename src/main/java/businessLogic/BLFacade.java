package businessLogic;

import java.util.Vector;
import java.util.Date;
import java.util.List;
import domain.Question;
import domain.QuestionContainer;
import domain.user;
import domain.Apustua;
import domain.Event;
import domain.Kuota;
import domain.KuotaContainer;
import domain.NormalUser;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * Interface that specifies the business logic.
 */
@WebService
public interface BLFacade {

	/**
	 * This method creates a question for an event, with a question text and the
	 * minimum bet
	 * 
	 * @param event      to which question is added
	 * @param question   text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws EventFinished        if current data is after data of the event
	 * @throws QuestionAlreadyExist if the same question already exists for the
	 *                              event
	 */
	@WebMethod
	Question createQuestion(Event event, String question, float betMinimum) throws EventFinished, QuestionAlreadyExist;

	/**
	 * This method retrieves the events of a given date
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	@WebMethod
	public List<Event> getEvents(Date date);

	/**
	 * This method retrieves from the database the dates a month for which there are
	 * events
	 * 
	 * @param date of the month for which days with events want to be retrieved
	 * @return collection of dates
	 */
	@WebMethod
	public Vector<Date> getEventsMonth(Date date);

	/**
	 * This method calls the data access to initialize the database with some events
	 * and questions. It is invoked only when the option "initialize" is declared in
	 * the tag dataBaseOpenMode of resources/config.xml file
	 */
	@WebMethod
	public void initializeBD();
	//metodo hau kuota bat eta question bat sartuta kuota datu-basera gehitzen du eta question-ari gehitzen dio.
	@WebMethod
	public void gehiKuota(Kuota kuot, Question q);
	//deskribapen bat eta data bat emanda gertaera bat sortzen du.
	@WebMethod
	public void sortuGertaera(String desk, Date data);
	//id bat sartuz galdera bat itzultzen du.
	@WebMethod
	public Question lortuGaldera(int id);
	// apustu id bat eta erabiltzaile izen bat sartuz erabiltzaile horren apustua ezabatzen du.
	@WebMethod
	public void ezabatuApustua(int apuId, String erabiltzailea);
	//erabiltzaile izen bat emanda erabiltzaile horrek dituen apustuak itzultzen ditu, 
	//baina itzultzen dituen apustuen kkuotak ezin dituen kuoten galderak ezin du emaitzik eduki.
	@WebMethod
	public List<Apustua> lortuApustuakEmaitzarikGabe(String erabiltzailea);
	//erabiltzaile izen bat eta pasahitz bat emanda konprobatzen du erabiltzaile eta pasahitza ondo dauden, ondo badaude true itzultzen du, bestela, false
	@WebMethod
	public boolean egiaztatuErab(String erab, String pass);
	//erabiltzaile bat eta pasahitz bat emanda, erabiltzailea erabiltzaile normal bat bezala erregistratzen du, aukeratua aldagaia beti false izango da,
	//baina, true jartzen bada administratzaile bezala erregistratuko du erabiltzailea.
	@WebMethod
	public boolean erabiltzaileaErreg(String erab, String pass, boolean aukeratua);
	//erabiltzaile izen bat sartuz, erabiltzaile hori itzultzen du.
	@WebMethod
	public user lortuErab(String us);
	//event baten id emanda, event horren question-ak itzultzen ditu.
	@WebMethod
	public List<Question> getQuest(int numb);
	//erabiltzaile normal guztiak itzultzen ditu.
	@WebMethod
	public List<NormalUser> lortuErabiltzaileNormalak();
	//erabiltzaile izen bat eta diru kantitate bat sartuta, erabiltzaileari diru kantitate hori sartzen dio.
	@WebMethod
	public boolean diruaSartu(String erab, double dirua);
	//erabiltzaile bat eta diru kantitate bat emanda erabiltzaileari jarritako diru kantitatea gehitzen dio, kasu honetan, 
	//adminDiruaSartu zerrendari gehitzen dio sartutako diru kantitatea.
	@WebMethod
	public boolean AdmindiruaSartu(String erab, double dirua);
	//erabiltzaile bat eta apustu bat emanda, datu-basean sortzen du apustua eta erabiltzaileari gehitzen dio. 
	@WebMethod
	public boolean erabApustuaEgin(String erab, Apustua apustu);
	//erabiltzaile izen bat , diru kantitate bat eta kuota zerrenda bat emanda, apustua sortzen du eta erabiltzaileari gehitzen dio.
	@WebMethod
	public boolean apustuAnitzak(String erab, Double dir, List<Kuota> kuotak);
	//event guztiak itzultzen ditu.
	@WebMethod
	public List<Event> lortuGertaeraGuztiak();
	//emaitza bat eta question id bat sartuta emaitza bat esleitzen dio.
	@WebMethod
	public void ipiniEmaitza(int id, String ema);
	// kuota bat emanda kuotacontainer objetu bat sortu eta itzultzen du.
	@WebMethod
	public KuotaContainer LortuKuota(Kuota kuota);
	// question bat emanda quotakontainer objetu bat sortu eta itzultzen du.
	@WebMethod
	public QuestionContainer LortuQuestion(Question q);

}
