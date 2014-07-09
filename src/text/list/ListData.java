package text.list;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

import javax.swing.BoundedRangeModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import text.TextSource;

public abstract class ListData implements BoundedRangeModel {
	protected ChangeEvent event;
	protected LinkedList listeners;

	protected int currentValue = 0;

	protected boolean adjusting = false;

	protected int extent = 1;

	public ListData() {
		event = new ChangeEvent(this);
		listeners = new LinkedList();
	}

	public ListElement getElementAtLocation(int pixely) {
		int _height = (TextSource.fontMetrics.getHeight() + 2);

		int lineNo = ((pixely - (pixely % _height)) / _height) + currentValue;

		return getElementAt(lineNo);
	}

	public void dirty() {}

	public abstract int getSize();

	public abstract ListElement head();

	public abstract ListElement next();

	public abstract ListElement getElementAt(int number);

	public abstract Iterator dataIterator();

	public Object getSynchronizationKeyOuter() {
		return null;
	}

	public Object getSynchronizationKeyInner() {
		return null;
	}

	public void fireChangeEvent() {
		ListIterator i = listeners.listIterator();
		while (i.hasNext()) {
			ChangeListener temp = (ChangeListener) i.next();
			temp.stateChanged(event);
		}
	}

	@Override
	public void addChangeListener(ChangeListener x) {
		listeners.add(x);
	}

	@Override
	public void removeChangeListener(ChangeListener x) {
		listeners.remove(x);
	}

	@Override
	public int getExtent() {
		return extent;
	}

	@Override
	public int getMaximum() {
		return getSize();
	}

	@Override
	public int getMinimum() {
		return 0;
	}

	@Override
	public int getValue() {
		if ((currentValue + extent) > getSize()) {
			currentValue = getSize() - extent;
		}

		if (getSize() < extent) {
			currentValue = 0;
		}

		if (currentValue < getSize()) {
			return currentValue;
		}

		currentValue = getSize() - 1;
		return currentValue;
	}

	@Override
	public boolean getValueIsAdjusting() {
		return adjusting;
	}

	@Override
	public void setExtent(int x) {
		extent = x;
	}

	@Override
	public void setMaximum(int x) {}

	@Override
	public void setMinimum(int x) {}

	@Override
	public void setRangeProperties(int newValue, int extent, int min, int max, boolean adjusting) {}

	@Override
	public void setValue(int newValue) {
		if (newValue < 0) {
			currentValue = 0;
		} else if (newValue < getSize()) {
			currentValue = newValue;
		} else {
			currentValue = getSize() - 1;
		}

		fireChangeEvent();
	}

	@Override
	public void setValueIsAdjusting(boolean b) {
		adjusting = b;
	}
}
