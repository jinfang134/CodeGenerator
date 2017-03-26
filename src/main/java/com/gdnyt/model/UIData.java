package com.gdnyt.model;

import java.util.Observable;

public class UIData extends Observable{
	private String genStatus;
	private int progress;
	
	public UIData(){
		
	}
	
	public String getGenStatus() {
		return genStatus;
	}
	public void setGenStatus(String genStatus) {
		this.genStatus = genStatus;
		notifyObservers();
	}
	public int getProgress() {
		return progress;
		
	}
	public void setProgress(int progress) {
		notifyObservers();
		this.progress = progress;
	}
	
	
}
