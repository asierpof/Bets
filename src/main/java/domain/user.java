package domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Entity
@XmlSeeAlso({ NormalUser.class, Admin.class })
public abstract class user implements Serializable {
	@XmlID
	@Id
	private String erabiltzailea;
	private String pasahitza;

	// private String izena;
	// private String abizena;
	// private int dirua;
	public user(String erab, String pas) {
		this.erabiltzailea = erab;
		this.pasahitza = pas;
	}

	public user() {
		super();
	}

	public String getErabiltzailea() {
		return this.erabiltzailea;
	}

	public void setPasahitza(String pas) {
		this.pasahitza = pas;
	}

	public String getPasahitza() {
		return this.pasahitza;
	}

	public String toString() {
		return "erabiltzailea: " + this.erabiltzailea + "pasahitza: " + this.pasahitza;
	}

}
