package com.gdnyt.ui.component;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import com.gdnyt.model.EventContent;

public class Subject {

	private List<Observer> list;

	private Subject() {
		list = new ArrayList<>();
	};

	public static Subject getInstance() {
		return Holder.instance;
	}

	private static class Holder {
		private static Subject instance = new Subject();
	}

	public void notifyObservers(EventContent eventContent) {
		// TODO Auto-generated method stub
		list.forEach(it -> {
			it.update(null, eventContent);
		});
	}

	public int countObservers() {
		return list.size();
	}

	public void addObserver(Observer observer) {
		list.add(observer);
	}

}
