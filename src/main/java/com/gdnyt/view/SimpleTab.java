package com.gdnyt.view;
import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;

public class SimpleTab extends JFrame {
	JTabbedPane tabPane;
	JPopupMenu popMenu;

	public SimpleTab() {
		super("JTabbedPane Demo");
		tabPane = new JTabbedPane();
		tabPane.addTab("Tab1", null, new JLabel("This is tab one."), "Tab #1");
		tabPane.addTab("Tab2", null, new JLabel("This is tab two."), "Tab #2");
		tabPane.addTab("Tab3", null, new JLabel("This is tab three."), "Tab #3");
		
		popMenu = new JPopupMenu();
		popMenu.add(new JMenuItem("Close"));
		popMenu.add(new JMenuItem("Close other"));
		popMenu.add(new JMenuItem("Close all"));

		tabPane.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseReleased(MouseEvent e) {
				if(e.isPopupTrigger()){
					if (tabPane.getTabCount() <= 0) {
						popMenu.show(e.getComponent(), e.getX(), e.getY());
					       return;
					}
					for (int i = 0; i < tabPane.getTabCount(); i ++) {
					       Rectangle rect = tabPane.getBoundsAt(i);
					       if (rect.contains(e.getX(), e.getY())) {
					    	   popMenu.show(e.getComponent(), e.getX(), e.getY());
					        return;
					       }
					}
				}
			}
			
		});
		this.getContentPane().add(tabPane, BorderLayout.CENTER);
		this.setSize(400, 200);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		new SimpleTab().setVisible(true);
	}

}