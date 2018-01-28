package com.gdnyt.model;

/**
 * 事件
 * 
 * @author jinfang
 *
 * @param <T>
 */
public class EventContent {
	private EventType eventType;
	private Object data;

	public EventContent(EventType eventType, Object data) {
		super();
		this.eventType = eventType;
		this.data = data;
	}

	public EventType getEventType() {
		return eventType;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
