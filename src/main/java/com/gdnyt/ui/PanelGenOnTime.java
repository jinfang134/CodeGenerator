package com.gdnyt.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.annotation.Resource;
import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

import org.apache.log4j.Logger;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.Gutter;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.springframework.stereotype.Component;

import com.gdnyt.model.Setting;
import com.gdnyt.service.CodeGenService;
import com.gdnyt.utils.MessageBox;

import freemarker.template.TemplateException;

/**
 * 代码实时生成，
 * 
 * @author jinfang
 *
 */

public class PanelGenOnTime extends JPanel implements SyntaxConstants {
	Logger log = Logger.getLogger(PanelGenOnTime.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Font font = new Font("微软雅黑", Font.PLAIN, 14);
	private Setting setting;
	private JButton  btn_gen;
	private RSyntaxTextArea textArea_temp;
	private RSyntaxTextArea textArea_code;
	
	private CodeGenService genService;
	
	private JTextField text_mode_prefix;
	private JLabel label;

	public PanelGenOnTime(CodeGenService genService) {
		setLayout(new BorderLayout(0, 0));
		setting = Setting.getInstance();
		this.genService=genService;
		initView();
	}

	private void initView() {
		JPanel panel = new JPanel();
		add(panel, BorderLayout.NORTH);
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		btn_gen = new JButton("执行");
		btn_gen.setBounds(new Rectangle(2, 2, 2, 2));
		btn_gen.addActionListener(new GenSQLFromExcelAction());

		label = new JLabel("表前缀：");
		panel.add(label);

		text_mode_prefix = new JTextField();
		panel.add(text_mode_prefix);
		text_mode_prefix.setColumns(10);
		panel.add(btn_gen);

		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.5);
		add(splitPane, BorderLayout.CENTER);

		textArea_temp = new RSyntaxTextArea();
		textArea_temp.setUseSelectedTextColor(true);
		textArea_temp.setSyntaxEditingStyle(SYNTAX_STYLE_JAVA);
		textArea_temp.setFont(font);
		textArea_temp.setTabSize(4);
		textArea_temp.setCaretPosition(0);
		textArea_temp.requestFocusInWindow();
		textArea_temp.setMarkOccurrences(true);
		textArea_temp.setCodeFoldingEnabled(true);
		textArea_temp.setClearWhitespaceLinesEnabled(false);

		RTextScrollPane scrollPane = new RTextScrollPane(textArea_temp, true);
		Gutter gutter = scrollPane.getGutter();
		splitPane.setLeftComponent(scrollPane);

		textArea_code = new RSyntaxTextArea();
		textArea_code.setSyntaxEditingStyle(SYNTAX_STYLE_JAVA);
		textArea_code.setFont(font);
		textArea_code.setTabSize(4);
		textArea_code.setCaretPosition(0);
		textArea_code.requestFocusInWindow();
		textArea_code.setMarkOccurrences(true);
		textArea_code.setCodeFoldingEnabled(true);
		textArea_code.setClearWhitespaceLinesEnabled(false);
		RTextScrollPane scrollPane2 = new RTextScrollPane(textArea_code, true);
		Gutter gutter2 = scrollPane2.getGutter();
		splitPane.setRightComponent(scrollPane2);

	}

	/**
	 * 根据excel中的元数据，来生成sql语句
	 * 
	 * @author jinfang
	 *
	 */
	private class GenSQLFromExcelAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			int[] rows = FrameMain.tree.getSelectionRows();
			if (rows.length <= 0) {
				MessageBox.showErrorMessage("请选择要生成的表");
				return;
			}
			StringBuilder sb = new StringBuilder();
			for (int i : rows) {
				sb.append(FrameMain.tablenames[i - 1] + ",");
			}
			String selectedTableNames = sb.toString();
			if (textArea_temp.getText().length() == 0) {
				MessageBox.showErrorMessage("请先载入或者编辑模板");
				return;
			}

			setting.setPrefix(text_mode_prefix.getText());					
			String dbName = setting.getDbname();
			try {
				String code = genService.genCode(textArea_temp.getText(), dbName, selectedTableNames);
				textArea_code.setText(code);
			} catch (TemplateException e2) {
				e2.printStackTrace();
				MessageBox.showErrorMessage(e2.getMessage());
				log.error(e2);
				return;
			}
			log.info("模板字符：" + textArea_temp.getText());
			MessageBox.showInfoMessage("生成完成！");

		}
	}

	
}
