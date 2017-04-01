package com.gdnyt.ui;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.springframework.stereotype.Component;

import com.gdnyt.model.Column;
import com.gdnyt.model.Table;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * 数据表信息的面板
 * @author jinfang
 *
 */

public class PanelTableMsg extends JPanel{
	private Table table;
	
	public PanelTableMsg(Table table){
		this.table=table;
		initView();
	}
	
	/**
	 * 创建界面
	 */
	private void initView(){
			
			setLayout(new FormLayout(new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
					FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"), }, new RowSpec[] { FormFactory.RELATED_GAP_ROWSPEC,
					FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
					RowSpec.decode("default:grow"), }));

			JLabel lblNewLabel_9 = new JLabel("表名：");
			add(lblNewLabel_9, "2, 2");

			JLabel lblNewLabel_10 = new JLabel(table.getName());
			add(lblNewLabel_10, "4, 2");

			JLabel lblNewLabel_11 = new JLabel("备注：");
			add(lblNewLabel_11, "2, 4");

			JLabel lblNewLabel_12 = new JLabel(table.getComment());
			add(lblNewLabel_12, "4, 4");

			JScrollPane scrollPane = new JScrollPane();
			add(scrollPane, "4, 6, fill, fill");
			scrollPane.setViewportView(makeTable());			
		}
		
		/**
		 * 构建表格
		 * 
		 * @param tablet
		 * @return
		 */
		private JTable makeTable() {
			String[] columnname = new String[] { "序号", "列名", "备注", "数据类型", "长度", "是否为空", "主键", "其它" };
			String data[][] = new String[table.getColumnlist().size()][7];
			int i = 0;
			for (Column column : table.getColumnlist()) {
				String[] ss = new String[] { (i + 1) + "", column.getColumnname(), column.getComment(), column.getColumntype(),
						column.getLength() + "", column.getIsnull(), column.getColumnkey(), column.getExtra() };
				data[i++] = ss;
			}

			JTable table = new JTable(data, columnname);
			// 设置表格大小
			table.setPreferredScrollableViewportSize(new Dimension(800, 80));
			table.getTableHeader().setReorderingAllowed(true);
			table.setRowHeight(30);
			return table;
		}
	
}
