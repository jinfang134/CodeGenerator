package com.gdnyt.ui;

import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.log4j.Logger;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.Gutter;
import org.fife.ui.rtextarea.RTextScrollPane;

import com.gdnyt.service.CodeGenService;
import com.gdnyt.utils.FileUtil;
import com.gdnyt.utils.MessageBox;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import freemarker.template.TemplateException;



/**
 * 根据excel生成sql代码及其它代码
 * 使用poi进行excel的相关操作。
 * @author jinfang
 *
 */
public class PanelExcel extends JPanel implements SyntaxConstants{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Font font = new Font("微软雅黑", Font.PLAIN, 14);
	Logger log = Logger.getLogger(PanelExcel.class);
	
	private JButton excelselect,btn_import,btn_savetem,btn_gensql,exportpathselect;
	private JTextField excelpath_text,exportpath_text;
	private RSyntaxTextArea temp_text;
	private CodeGenService genService;
	
	
	public PanelExcel() {		
		initView();
		genService = new CodeGenService();
	}
	
	
	private void initView(){
		setLayout(new FormLayout(new ColumnSpec[] { ColumnSpec.decode("left:4dlu"), FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("left:default:grow"), FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("left:default"), FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, },
				new RowSpec[] { FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
						RowSpec.decode("default:grow"), FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, }));

		JLabel lblexcel = new JLabel("选择excel：");
		add(lblexcel, "2, 2, left, center");

		excelpath_text = new JTextField();
		add(excelpath_text, "3, 2, fill, top");
		excelpath_text.setColumns(10);

		excelselect = new JButton("选择");
		excelselect.addActionListener(new SelectFileAction());
		excelselect.setIcon(new ImageIcon(FrameMain.class.getResource("/icon/folder_vertical_document.png")));
		add(excelselect, "5, 2");

		btn_import = new JButton("载入模板");
		btn_import.addActionListener(new SelectFileAction());
		btn_import.setIcon(new ImageIcon(FrameMain.class.getResource("/icon/saved_imports.png")));
		add(btn_import, "7, 2");

		btn_savetem = new JButton("保存模板");
		btn_savetem.addActionListener(new SaveFileAction());
		btn_savetem.setIcon(new ImageIcon(FrameMain.class.getResource("/icon/page_save.png")));
		add(btn_savetem, "9, 2");

		temp_text = new RSyntaxTextArea();
		temp_text.setSyntaxEditingStyle(SYNTAX_STYLE_SQL);
		temp_text.setFont(font);
		temp_text.setTabSize(3);
		temp_text.setCaretPosition(0);
		temp_text.requestFocusInWindow();
		temp_text.setMarkOccurrences(true);
		temp_text.setCodeFoldingEnabled(true);
		temp_text.setClearWhitespaceLinesEnabled(false);
		RTextScrollPane scrollPane = new RTextScrollPane(temp_text, true);
		Gutter gutter = scrollPane.getGutter();
		// gutter.setBookmarkingEnabled(true);				
		add(scrollPane, "1, 4, 9, 1, default, fill");

		JLabel label_1 = new JLabel("选择输出目录：");
		add(label_1, "2, 6, right, default");

		exportpath_text = new JTextField();
		add(exportpath_text, "3, 6, fill, default");
		exportpath_text.setColumns(10);

		btn_gensql = new JButton("生成SQL");
		btn_gensql.setBounds(new Rectangle(2, 2, 2, 2));
		btn_gensql.addActionListener(new GenSQLFromExcelAction());

		exportpathselect = new JButton("选择");
		exportpathselect.addActionListener(new SelectPathAction());
		exportpathselect.setIcon(new ImageIcon(FrameMain.class.getResource("/icon/folder_vertical_document.png")));
		add(exportpathselect, "5, 6");
		btn_gensql.setIcon(new ImageIcon(FrameMain.class.getResource("/icon/printer.png")));
		add(btn_gensql, "3, 8");
	}
	

	/**
	 * 根据excel中的元数据，来生成sql语句
	 * @author jinfang
	 *
	 */
	private class GenSQLFromExcelAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				genService.genSql(temp_text.getText(), excelpath_text.getText(), exportpath_text.getText());
			} catch (TemplateException e2) {
				e2.printStackTrace();
				log.error(e2);
			}
			log.info("模板字符：" + temp_text.getText());
			MessageBox.showInfoMessage("生成完成！");
			try {
				String cmd="cmd.exe /c start notepad " + exportpath_text.getText() + "\\create.sql";
				log.info(cmd);
				Runtime.getRuntime().exec(cmd);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	
	/**
	 * 选择模板文件
	 * 
	 * @author jinfang
	 * 
	 */
	private class SelectFileAction extends AbstractAction {

		public SelectFileAction() {
			putValue(NAME, "选择文件");
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			// 载入模板文件

			JFileChooser jfc = new JFileChooser("./");
			jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			jfc.showDialog(new JLabel(), "选择");
			FileFilter filter = null;
			if ( e.getSource() == btn_import) {
				String extj[] = { "ftl", "*" };
				filter = new FileNameExtensionFilter("freemarker模板(*.ftl)", extj);
			} else {
				String extj[] = { "xls", "*" };
				filter = new FileNameExtensionFilter("excel文件(*.xls)", extj);
			}
			jfc.setFileFilter(filter);
			File file = jfc.getSelectedFile();
			if (file != null && file.isFile()) {
				String temp = FileUtil.readTxtFile(file.getAbsolutePath());
				if (e.getSource() == btn_import) {
					temp_text.setText(temp);
				}
				if (e.getSource() == excelselect) {
					excelpath_text.setText(file.getAbsolutePath());
				}
			}

		}

	}
	
	/**
	 * 导出路径选择
	 * @author jinfang
	 *
	 */
	private class SelectPathAction extends AbstractAction {

		public SelectPathAction() {
			putValue(NAME, "Bookmarks");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfc = new JFileChooser("d:/");
			jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			jfc.showDialog(new JLabel(), "选择");
			File file = jfc.getSelectedFile();
			if (file != null && file.isDirectory()) {
				if (e.getSource() == exportpathselect) {
					exportpath_text.setText(file.getAbsolutePath());
				} 
			} else if (file != null && file.isFile()) {
				System.out.println("文件:" + file.getAbsolutePath());
			}
		}
	}
	
	/**
	 * 保存文件
	 * @author jinfang
	 *
	 */
	private class SaveFileAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String temp = null;
			if (e.getSource() == btn_savetem) {
				temp = temp_text.getText();
			} 			
			String filename = "./";
			String extj[] = { "ftl", "*" };
			FileFilter filter = new FileNameExtensionFilter("freemarker模板(*.ftl)", extj);

			JFileChooser jfc = new JFileChooser("./");
			jfc.setFileFilter(filter);
			jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			jfc.setDialogType(JFileChooser.SAVE_DIALOG);
			int index = jfc.showDialog(new JLabel(), "保存");
			if (index == JFileChooser.APPROVE_OPTION) {
				File f = jfc.getSelectedFile();
				String fileName = jfc.getName(f);
				if (fileName.indexOf(".ftl") == -1)
					fileName = jfc.getName(f) + ".ftl";
				filename = jfc.getCurrentDirectory().getAbsolutePath() + File.separator + fileName;
				log.info("文件名:" + filename);
				FileUtil.saveToFile(filename, temp, "utf-8");
			}
		}

	}
		
}
