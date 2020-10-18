package domain;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Admin extends user {
	public Admin(String us, String pass) {
		super(us, pass);
	}

	public Admin() {
		super();
	}

	public String toString() {
		return super.toString();
	}

}
