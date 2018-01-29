package com.gdnyt.ui.component;

import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.AbstractAction;
import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import net.miginfocom.swing.MigLayout;

import org.apache.commons.lang3.StringUtils;

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
	private JTextField keyword;
	private JScrollPane scrollPane;

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
		refreshList("");
		getContentPane().setLayout(
				new MigLayout("", "[114px,grow][]", "[19px][1px][grow]"));

		JButton btnDel = new JButton("删除");
		btnDel.addActionListener(new DelListener());
		getContentPane().add(btnDel, "cell 1 0,aligny center");
		// this.getContentPane().add(list,
		// "cell 0 1,alignx center,aligny bottom");

		keyword = new JTextField();
		keyword.setToolTipText("过滤");
		keyword.setColumns(10);
		keyword.addKeyListener(new KeywordLister());
		getContentPane().add(keyword, "cell 0 0,alignx left,aligny center");

		scrollPane = new JScrollPane();
		scrollPane.setViewportView(list);
		getContentPane().add(scrollPane, "cell 0 2 2 1,grow");

		this.setVisible(true);
		list.addMouseListener(new ListListener());
	}

	/**
	 * 删除模板的事件
	 * 
	 * @author jinfang
	 *
	 */
	private class DelListener extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String tempName = list.getSelectedValue();
			int response = JOptionPane.showConfirmDialog(null, "确认删除模板["
					+ tempName + "]", "确认", JOptionPane.YES_NO_OPTION);
			if (response == 0 && StringUtils.isNotBlank(tempName)) {
				File file = new File("./template" + File.separator + tempName);
				file.delete();
				Subject.getInstance().showStatus("模板删除成功！");
				refreshList(keyword.getText());
			}
		}

	}

	private class KeywordLister extends KeyAdapter {
		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			String key = keyword.getText();
			refreshList(key);
		}
	}

	public void AddTemplateSelectLister(TemplateSelectedEvent e) {
		if (e != null) {
			this.events.add(e);
		}
	}

	public void refreshList(String key) {
		List<String> templates = getTemplates().stream()
				.filter(item -> item.indexOf(key.toLowerCase()) > -1)
				.collect(Collectors.toList());
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
