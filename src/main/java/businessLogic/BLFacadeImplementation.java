package businessLogic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.jws.WebMethod;
import javax.jws.WebService;

import configuration.ConfigXML;
import dataAccess.DataAccess;
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

/**
 * It implements the business logic as a web service.
 */
@WebService(endpointInterface = "businessLogic.BLFacade")
public class BLFacadeImplementation implements BLFacade {
	DataAccess dbManager;

	public BLFacadeImplementation() {
		System.out.println("Creating BLFacadeImplementation instance");
		ConfigXML c = ConfigXML.getInstance();

		if (c.getDataBaseOpenMode().equals("initialize")) {
			DataAccess dbManager = new DataAccess(c.getDataBaseOpenMode().equals("initialize"));
			dbManager.initializeDB();
			dbManager.close();
		}

	}
	public BLFacadeImplementation(DataAccess da) {
		System.out.println("Creating BLFacadeImplementationinstance with DataAccess parameter");
		ConfigXML c = ConfigXML.getInstance();
		if (c.getDataBaseOpenMode().equals("initialize")) {
			da.open(true);
			da.initializeDB();
			da.close();
		}
		dbManager = da;
	}

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
	public Question createQuestion(Event event, String question, float betMinimum)
			throws EventFinished, QuestionAlreadyExist {

		// The minimum bed must be greater than 0
		dbManager.open(false);
		Question qry = null;

		if (new Date().compareTo(event.getEventDate()) > 0)
			throw new EventFinished(ResourceBundle.getBundle("Etiquetas").getString("ErrorEventHasFinished"));

		qry = dbManager.createQuestion(event, question, betMinimum);

		dbManager.close();

		return qry;
	};

	/**
	 * This method invokes the data access to retrieve the events of a given date
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	@WebMethod
	public List<Event> getEvents(Date date) {
		dbManager.open(false);
		Vector<Event> events = (Vector<Event>) dbManager.getEvents(date);
		dbManager.close();
		return events;
	}

	/**
	 * This method invokes the data access to retrieve the dates a month for which
	 * there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved
	 * @return collection of dates
	 */
	@WebMethod
	public Vector<Date> getEventsMonth(Date date) {
		dbManager.open(false);
		Vector<Date> dates = dbManager.getEventsMonth(date);
		dbManager.close();
		return dates;
	}

	/**
	 * This method invokes the data access to initialize the database with some
	 * events and questions. It is invoked only when the option "initialize" is
	 * declared in the tag dataBaseOpenMode of resources/config.xml file
	 */
	@WebMethod
	public void initializeBD() {
		dbManager.open(false);
		dbManager.initializeDB();
		dbManager.close();
	}

	@WebMethod
	public boolean egiaztatuErab(String erab, String pass) {
		dbManager.open(false);
		boolean egiaztatu = dbManager.egiaztatuErabiltzailea(erab, pass);
		dbManager.close();
		return egiaztatu;
	}

	@WebMethod
	public boolean erabiltzaileaErreg(String erab, String pass, boolean aukeratua) {

		dbManager.open(false);
		boolean egiaztatu = dbManager.erabiltzaileaErregistratu(erab, pass, aukeratua);
		dbManager.close();
		return egiaztatu;
	}

	@WebMethod
	public user lortuErab(String us) {
		dbManager.open(false);
		user erab = dbManager.erabiltzaileaLortu(us);
		dbManager.close();
		return erab;
	}

	@WebMethod
	public List<Question> getQuest(int numb) {
		dbManager.open(false);
		List<Question> quest = dbManager.getEventQuestions(numb);
		dbManager.close();
		return quest;

	}

	@WebMethod
	public void gehiKuota(Kuota kuot, Question q) {
		dbManager.open(false);
		dbManager.setKuota(kuot, q);
		dbManager.close();
	}

	@WebMethod
	public void sortuGertaera(String desk, Date data) {
		dbManager.open(false);
		dbManager.gehituGertaera(desk, data);
		dbManager.close();
	}

	@WebMethod
	public Question lortuGaldera(int id) {
		dbManager.open(false);
		Question quest = dbManager.getQuestion(id);
		dbManager.close();
		QuestionContainer question = new QuestionContainer(quest);
		return question.getQuestion();
	}

	@WebMethod
	public List<NormalUser> lortuErabiltzaileNormalak() {
		dbManager.open(false);
		List<NormalUser> erab = dbManager.lortuErabiltzeileNormalak();
		dbManager.close();
		return erab;
	}

	@WebMethod
	public boolean diruaSartu(String erab, double dirua) {
		dbManager.open(false);
		boolean egin = dbManager.ErabiltzaileariDiruaSartu(erab, dirua);
		dbManager.close();
		return egin;
	}

	@WebMethod
	public boolean AdmindiruaSartu(String erab, double dirua) {
		dbManager.open(false);
		boolean egin = dbManager.AdminErabiltzaileariDiruaSartu(erab, dirua);
		dbManager.close();
		return egin;
	}

	@WebMethod
	public boolean erabApustuaEgin(String erab, Apustua apustu) {

		dbManager.open(false);
		boolean egin = dbManager.apustuaEgin(erab, apustu);
		dbManager.close();
		return egin;
	}

	@WebMethod
	public boolean apustuAnitzak(String erab, Double dir, List<Kuota> kuotak) {
		dbManager.open(false);
		boolean egin = dbManager.apustuAnitzaEgin(erab, dir, kuotak);
		dbManager.close();
		return egin;
	}

	@WebMethod
	public List<Event> lortuGertaeraGuztiak() {
		dbManager.open(false);
		List<Event> ev = dbManager.lortuEventuGuztiak();
		dbManager.close();
		return ev;
	}

	@WebMethod
	public void ipiniEmaitza(int id, String ema) {
		dbManager.open(false);
		dbManager.ipiniEmaitza(id, ema);
		dbManager.close();
	}

	@WebMethod
	public void ezabatuApustua(int apuId, String erabiltzailea) {
		dbManager.open(false);
		dbManager.apustuaEzabatu(apuId, erabiltzailea);
		dbManager.close();

	}

	@WebMethod
	public List<Apustua> lortuApustuakEmaitzarikGabe(String erabiltzailea) {
		dbManager.open(false);
		List<Apustua> apustuak = dbManager.lortuApustuak(erabiltzailea);
		List<Apustua> apustuakErantzunGabe = new ArrayList<Apustua>();
		dbManager.close();
		for (int i = 0; i < apustuak.size(); i++) {
			boolean ezabatu = false;
			for (int j = 0; j < apustuak.get(i).getApustuKuota().size(); j++) {
				KuotaContainer kCont = new KuotaContainer(apustuak.get(i).getApustuKuota().get(j));
				if (kCont.getQuestion().getResult() != "")
					ezabatu = true;
			}
			if (!ezabatu)
				apustuakErantzunGabe.add(apustuak.get(i));
		}
		return apustuakErantzunGabe;

	}

	@WebMethod
	public KuotaContainer LortuKuota(Kuota kuota) {
		dbManager.open(false);
		KuotaContainer kc = dbManager.LortuKuota(kuota);
		dbManager.close();
		return kc;
	}

	@WebMethod
	public QuestionContainer LortuQuestion(Question q) {
		dbManager.open(false);
		QuestionContainer qc = dbManager.LortuQuestion(q);
		dbManager.close();
		return qc;
	}
}
