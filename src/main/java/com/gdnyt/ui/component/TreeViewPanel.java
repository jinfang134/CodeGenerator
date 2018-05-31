package com.gdnyt.ui.component;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.stream.Collectors;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import com.gdnyt.dto.EventContent;
import com.gdnyt.dto.EventType;
import com.gdnyt.dto.SchemaInfo;
import com.gdnyt.ui.event.TableSelectEvent;
import com.gdnyt.utils.MessageBox;
import com.gdnyt.view.MyDefaultTreeCellRenderer;

public class TreeViewPanel extends JScrollPane implements Observer {

	private static Font font = new Font("微软雅黑", Font.PLAIN, 14);
	private List<TableSelectEvent> events;
	JTree tree;
	private List<String> tableNames;

	public TreeViewPanel() {
		Subject.getInstance().addObserver(this);
		events = new ArrayList<>();
		tree = new JTree();
		tree.setBackground(Color.WHITE);
		tree.setForeground(Color.LIGHT_GRAY);
		tree.setFont(font);
		tree.setScrollsOnExpand(true);
		tree.addMouseListener(new TreeListener());
		this.setViewportView(tree);
		this.setBounds(this.getX(), this.getY(), 200, this.getHeight());
	}

	private class TreeListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			if (e.getClickCount() == 2) {
				// 处理鼠标双击
				JTree tree = (JTree) e.getSource();
				int index = tree.getSelectionRows()[0] - 1;
				if (index < 0) {
					MessageBox.showErrorMessage("请选择正确的数据表");
					return;
				}
				String tablename = tableNames.get(index);
				fireDbClick(tablename);
			}
			if (e.getClickCount() == 1) {
				events.forEach(event -> {
					event.select(getSelectedTables());
				});
				Subject.getInstance().notifyObservers(
						new EventContent(EventType.TABLE_SELECTED,
								getSelectedTables()));
			}
		}

		private void fireDbClick(String tablename) {
			events.forEach(event -> {
				if (event != null) {
					event.dbClick(tablename);
				}
			});
		}

	}

	public void addTableSelected(TableSelectEvent event) {
		this.events.add(event);
	}

	public void setTableNames(String dbName, List<String> tableNames) {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(dbName);
		for (String s : tableNames) {
			DefaultMutableTreeNode tableNode = new DefaultMutableTreeNode(s);
			node.add(tableNode);
		}
		tree.setModel(new DefaultTreeModel(node));
		// 设置自定义描述类
		tree.setCellRenderer(new MyDefaultTreeCellRenderer());
		this.setSize(200, this.getHeight());
		updateUI();
	}

	public List<String> getSelectedTables() {
		return Arrays.stream(tree.getSelectionRows())
				.mapToObj(it -> tableNames.get(it - 1))
				.collect(Collectors.toList());
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		EventContent eventContent = (EventContent) arg;
		if (eventContent.getEventType() == EventType.LOAD_TABLES) {
			SchemaInfo schemaInfo = (SchemaInfo) eventContent.getData();
			this.tableNames = schemaInfo.getTableNames();
			setTableNames(schemaInfo.getDbName(), schemaInfo.getTableNames());
		}
	}

}
