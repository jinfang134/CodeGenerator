package com.gdnyt.ui.component;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.gdnyt.ui.event.TemplateSelectedEvent;

/**
 * 模板选择的面板
 * 
 * @author jinfang
 *
 */
public class TemplateListPanel extends JInternalFrame {

	/**
	 * 双击选中模板的事件
	 * 
	 * @author jinfang
	 *
	 */
	private class ListListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 2) {
				String tempName = (String) list.getSelectedValue();
				System.out.println(list.getSelectedValue());
				fireSelectedEvents(tempName, getContent(tempName));
			}
		}
	}

	private JList<String> list;

	private List<TemplateSelectedEvent> events;

	public TemplateListPanel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		events = new ArrayList<TemplateSelectedEvent>();
		this.setTitle("模板列表");
		this.setResizable(true);

		this.setClosable(true);
		this.setSize(200, 500);
		list = new JList<String>();
		refreshList();
		this.getContentPane().add(list, BorderLayout.NORTH);
		this.setVisible(true);
		list.addMouseListener(new ListListener());
	}

	public void AddTemplateSelectLister(TemplateSelectedEvent e) {
		if (e != null) {
			this.events.add(e);
		}
	}

	public void refreshList() {
		List<String> templates = getTemplates();
		setListData(templates);
	}

	private void fireSelectedEvents(String tempName, String content) {
		events.forEach(it -> {
			if (it != null) {
				it.select(tempName, content);
			}
		});
	}

	private String getContent(String tempName) {
		File file = new File("./template" + File.separator + tempName);
		if (file.exists()) {
			try {
				return jodd.io.FileUtil.readString(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "";
			}
		}
		return "";
	}

	private List<String> getTemplates() {
		File file = new File("./template");
		if (!file.exists()) {
			return new ArrayList<String>();
		} else {
			return Arrays.asList(file.list());
		}
	}

	private void setListData(List<String> templates) {
		list.setModel(new AbstractListModel<String>() {
			public String getElementAt(int index) {
				return templates.get(index);
			}

			public int getSize() {
				return templates.size();
			}
		});
	}

}
