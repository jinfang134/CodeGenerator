package com.gdnyt.ui.component;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import com.gdnyt.utils.MessageBox;
import com.gdnyt.view.MyDefaultTreeCellRenderer;
import com.openhtmltopdf.swing.SelectionHighlighter;

public class TreeViewPanel extends JScrollPane {
	private static Font font = new Font("微软雅黑", Font.PLAIN, 14);
	private List<TableSelectEvent> events;
	JTree tree;
	private List<String> tableNames;

	public TreeViewPanel() {
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

	private class TreeListener extends SelectionHighlighter {
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
				events.forEach(event -> {
					event.dbClick(tablename);
				});

			}
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
		return Arrays.stream(tree.getSelectionRows()).mapToObj(it -> tableNames.get(it)).collect(Collectors.toList());
	}

}
