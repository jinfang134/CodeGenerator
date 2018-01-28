package com.gdnyt.ui.component;

import java.awt.FlowLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import com.gdnyt.model.EventContent;
import com.gdnyt.model.EventType;

public class StatusBar extends JPanel implements Observer {

	private JProgressBar progressBar;
	private JLabel status_text;

	public StatusBar() {
		FlowLayout flowLayout_1 = (FlowLayout) this.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		progressBar = new JProgressBar();
		this.add(progressBar);
		status_text = new JLabel("就绪！");
		this.add(status_text);
		Subject.getInstance().addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		EventContent eventContent = (EventContent) arg;
		if (eventContent.getEventType() == EventType.SHOW_STATUS) {
			String msg = (String) eventContent.getData();
			status_text.setText(msg);
		}
	}

}
