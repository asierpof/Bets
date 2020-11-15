package Iterator;

import java.util.Vector;

import domain.Event;

public class IteratorDate implements ExtendedIterator<Event>{
	private Vector<Event> ev;
	int position=0;
	public IteratorDate (Vector<Event> evector) {
		ev = evector;
	}
	public boolean hasNext() {
		return position< ev.size();
	}

	public Object next() {
		Event e=ev.get(position);
		position++;
		return e;
	}

	public Object previous() {
		position--;
		Event e=ev.get(position);
		return e;
	}

	public boolean hasPrevious() {
		return position>0;
	}

	public void goFirst() {
		position =0;
		
	}

	public void goLast() {
		position = ev.size();
	}

}
