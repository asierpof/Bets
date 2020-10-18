package domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Kuota implements Serializable {
	@XmlID
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@Id
	@GeneratedValue
	private Integer id;
	private String pronostikoa;
	private Double portzentaila;
	@XmlIDREF
	private Question question;

//	@XmlIDREF
//	@ManyToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
//	private List<Apustua> apustua;
	public Kuota(String pro, Double por, Question q) {
		this.pronostikoa = pro;
		this.portzentaila = por;
		this.question = q;
	}

	public Kuota(int i, String pro, Double por, Question q) {
		this.id = i;
		this.pronostikoa = pro;
		this.portzentaila = por;
		this.question = q;
	}

	public Kuota() {
		super();
	}

	public String getPronostikoa() {
		return pronostikoa;
	}

	public void setPronostikoa(String pronostikoa) {
		this.pronostikoa = pronostikoa;
	}

	public Double getPortzentaila() {
		return portzentaila;
	}

	public void setPortzentaila(Double portzentaila) {
		this.portzentaila = portzentaila;
	}

	public int getId() {
		return this.id;
	}

	public Question getQuestion() {
		return question;
	}

	public void addQuestion(Question quest) {
		this.question = quest;
	}

	public String toString() {
		return this.pronostikoa + " " + this.portzentaila;
	}

}
