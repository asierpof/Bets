package domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class QuestionContainer {
	private Question question;
	private Event event;

	public QuestionContainer(Question q) {
		this.question = q;
		this.event = q.getEvent();
	}

	public QuestionContainer() {
		this.question = null;
		this.event = null;
	}

	public Question getQuestion() {
		return this.question;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

}
