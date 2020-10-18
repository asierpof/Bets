package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Apustua implements Serializable {
	@XmlID
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@Id
	@GeneratedValue
	private Integer id;
	private double apustuDiru;

	@OneToMany(fetch = FetchType.EAGER)
	private List<Kuota> apustuKuota;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Apustua(double dirua, List<Kuota> kuot) {
		this.apustuDiru = dirua;
		this.apustuKuota = kuot;

	}

	public Apustua() {
		this.apustuDiru = 0;
		this.apustuKuota = new ArrayList<Kuota>();

	}

	public double getApustuDiru() {
		return apustuDiru;
	}

	public void setApustuDiru(double apustuDiru) {
		this.apustuDiru = apustuDiru;
	}

	public void gehituKuota(Kuota k) {
		this.apustuKuota.add(k);
	}

	public List<Kuota> getApustuKuota() {
		return apustuKuota;
	}

	public void setApustuKuota(ArrayList<Kuota> apustuKuota) {
		this.apustuKuota = apustuKuota;
	}

	public String toString() {
		return this.apustuDiru + this.apustuKuota.toString();
	}

}
