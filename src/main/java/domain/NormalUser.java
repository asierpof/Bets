package domain;

import java.util.ArrayList;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class NormalUser extends user {
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	private ArrayList<Apustua> apustuak = new ArrayList<Apustua>();
	private double dirua;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	private ArrayList<Double> AdminSartutakoDirua;

	public NormalUser(String us, String pass) {
		super(us, pass);
		this.dirua = 0.0;
		AdminSartutakoDirua = new ArrayList<Double>();
	}

	public NormalUser() {
		super();
	}

	public Double getDirua() {
		return this.dirua;
	}

	public void gehituApustua(Apustua apustu) {
		this.apustuak.add(apustu);
	}

	public ArrayList<Apustua> lortuApustuak() {
		return this.apustuak;
	}

	public boolean apustuaDiruaKendu(double dir) {
		if (this.dirua < dir)
			return false;
		else
			this.dirua -= dir;
		return true;
	}

	public boolean gehituDirua(double dir) {
		if (dir <= 0.0)
			return false;
		else {
			this.dirua = this.dirua + dir;
		}
		return true;
	}

	public void gehituAdmindirua(double dir) {
		this.AdminSartutakoDirua.add(dir);
	}

	public ArrayList<Double> lortuAdminSartutakoDirua() {
		return this.AdminSartutakoDirua;
	}

	public void ezabatuApustua(int id) {
		for (int i = 0; i < apustuak.size(); i++) {
			if (apustuak.get(i).getId() == id) {
				this.gehituDirua(apustuak.get(i).getApustuDiru());
				apustuak.remove(i);
				break;
			}
		}
	}

	public String toString() {
		return super.toString();
	}

}
