package domain;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;

@XmlAccessorType(XmlAccessType.FIELD)
public class KuotaContainer {
	private Question question;
	private Kuota kuota;

	public KuotaContainer(Kuota k) {
		this.question = k.getQuestion();
		this.kuota = k;
	}

	public KuotaContainer() {
		this.kuota = null;
		this.question = null;
	}

	public Question getQuestion() {
		return question;
	}

	public Kuota getKuota() {
		return kuota;
	}

	public void setKuota(Kuota kuota) {
		this.kuota = kuota;
	}

}
