package com.gdnyt.dto;

import java.util.Observable;

public class ProgressDto extends Observable{
	private String genStatus;
	private int progress;
	
	public ProgressDto(){
		
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
