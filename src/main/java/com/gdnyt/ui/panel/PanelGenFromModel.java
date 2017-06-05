package com.gdnyt.ui.panel;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.annotation.Resource;
import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
import org.springframework.stereotype.Component;

import com.gdnyt.model.Setting;
import com.gdnyt.service.CodeGenService;
import com.gdnyt.ui.Constants;
import com.gdnyt.ui.FrameMain;
import com.gdnyt.utils.FileUtil;
import com.gdnyt.utils.MessageBox;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import freemarker.template.TemplateException;


/**
 * 根据写入的模板来生成代码
 * 
 * @author jinfang
 *
 */
@Component
public class PanelGenFromModel extends JPanel implements SyntaxConstants{
	Logger log = Logger.getLogger(PanelGenFromModel.class);
	
	private RSyntaxTextArea text_model;
	private JTextField 		text_mode_prefix,text_model_append,text_model_exportpath;
	private JButton btn_model_saveModel,btn_model_import,btn_GenCodeFromModel,btn_model_exportpath;
	private JComboBox comboBox_suffix;
	String[] suffix=new String[]{".java",".cs",".html",".sql",".jsp",".aspx",".xml",".js"};
	private Setting setting;
	
	private CodeGenService genService;
	
	
	//构造器
	public PanelGenFromModel(CodeGenService genService){
		initView();
		setting = Setting.getInstance();
		this.genService=genService;
	}
	
	//初始化界面
	private void initView(){
	
		setLayout(new FormLayout(
				new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("84px"), FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("150px"), FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("80px"),
						FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("120px"),
						FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("100px:grow"),// FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("126px"), }, new RowSpec[] { FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("25px"),
						FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("172px:grow"), FormFactory.RELATED_GAP_ROWSPEC,
						RowSpec.decode("25px"), FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("25px"), }));

		JLabel label_2 = new JLabel("表名前缀：");
		add(label_2, "2, 2, right, center");

		
		text_mode_prefix = new JTextField();
		add(text_mode_prefix, "4, 2, fill, center");
		text_mode_prefix.setColumns(10);

		JLabel label_3 = new JLabel("文件后缀：");
		add(label_3, "6, 2, right, center");

		comboBox_suffix = new JComboBox();
		comboBox_suffix.setModel(new DefaultComboBoxModel<>(suffix));		
		add(comboBox_suffix, "8, 2, fill, center");
		

		JLabel lblNewLabel_15 = new JLabel("文件名附加：");
		add(lblNewLabel_15, "10, 2, right, default");

		text_model_append = new JTextField();
		add(text_model_append, "12, 2, fill, default");
		text_model_append.setColumns(10);
		
				btn_model_import = new JButton("载入模板");
				btn_model_import.addActionListener(new SelectFileAction());
				
						btn_model_import.setIcon(new ImageIcon(FrameMain.class.getResource("/icon/saved_imports.png")));
						add(btn_model_import, "14, 2, left, top");

		text_model = new RSyntaxTextArea();
		text_model.setSyntaxEditingStyle(SYNTAX_STYLE_JAVA);
		text_model.setFont(Constants.font);
		text_model.setTabSize(3);
		text_model.setCaretPosition(0);
		text_model.requestFocusInWindow();
		text_model.setMarkOccurrences(true);
		text_model.setCodeFoldingEnabled(true);
		text_model.setClearWhitespaceLinesEnabled(false);
		
				btn_model_saveModel = new JButton("保存模板");
				btn_model_saveModel.addActionListener(new SaveFileAction());
				btn_model_saveModel.setIcon(new ImageIcon(FrameMain.class.getResource("/icon/page_save.png")));
				add(btn_model_saveModel, "16, 2, left, top");
		RTextScrollPane scrollPane = new RTextScrollPane(text_model, true);
		Gutter gutter = scrollPane.getGutter();
		// gutter.setBookmarkingEnabled(true);

		add(scrollPane, "1, 4, 17, 1, fill, fill");

		JLabel label_1 = new JLabel("选择输出目录：");
		add(label_1, "2, 6, right, center");

		btn_GenCodeFromModel = new JButton("生成代码");
		btn_GenCodeFromModel.setBounds(new Rectangle(2, 2, 2, 2));
		btn_GenCodeFromModel.addActionListener(new GenFromModelAction());

		text_model_exportpath = new JTextField();
		add(text_model_exportpath, "4, 6, 3, 1, fill, center");
		text_model_exportpath.setColumns(10);

		btn_model_exportpath = new JButton("选择");
		btn_model_exportpath.addActionListener(new SelectPathAction());
		btn_model_exportpath.setIcon(new ImageIcon(FrameMain.class.getResource("/icon/folder_vertical_document.png")));
		add(btn_model_exportpath, "8, 6, fill, top");

		btn_GenCodeFromModel.setIcon(new ImageIcon(FrameMain.class.getResource("/icon/printer.png")));
		add(btn_GenCodeFromModel, "4, 8, left, top");

	}


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
				 if (e.getSource() == btn_model_exportpath) {
					text_model_exportpath.setText(file.getAbsolutePath());
				}
				
			} else if (file != null && file.isFile()) {
				System.out.println("文件:" + file.getAbsolutePath());
			}
		}

	}


	/**
	 * 选择文件
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
//			if (e.getSource() == btn_model_import ) {
//				String extj[] = { "ftl", "*" };
//				filter = new FileNameExtensionFilter("freemarker模板(*.ftl)", extj);
//			} else {
//				String extj[] = { "xls", "*" };
//				filter = new FileNameExtensionFilter("excel文件(*.xls)", extj);
//			}
//			jfc.setFileFilter(filter);
			File file = jfc.getSelectedFile();

			if (file != null && file.isFile()) {
				String temp = FileUtil.readTxtFile(file.getAbsolutePath());
				
				if (e.getSource() == btn_model_import) {
					text_model.setText(temp);
				}
			
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
			temp = text_model.getText();
			
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
	

	/**
	 * 根据模板，选表生成代码
	 * @author jinfang
	 *
	 */
	private class GenFromModelAction extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			// 根据模板生成代码
			if (e.getSource() == btn_GenCodeFromModel) {
				int[] rows = FrameMain.tree.getSelectionRows();
				if (rows.length <= 0) {
					MessageBox.showErrorMessage("请选择要生成的表");
					return;
				}
				if (text_model.getText().length() == 0) {
					MessageBox.showErrorMessage("请先载入或者编辑模板");
					return;
				}
				
				setting.setPrefix(text_mode_prefix.getText());
				StringBuilder sb = new StringBuilder();
				for (int i : rows) {
					sb.append(FrameMain.tablenames[i - 1] + ",");
				}
				String tablenames = sb.toString();
				
				try {
					genService.genCodeByTemplate(setting.getDbname(),text_model.getText(), text_model_exportpath.getText(), tablenames, comboBox_suffix.getSelectedItem().toString(),
							text_model_append.getText());
				} catch (TemplateException e2) {
					e2.printStackTrace();
					log.error(e2);
					MessageBox.showErrorMessage("生成出错,错误内容："+e2.getMessage());
				}
				MessageBox.showInfoMessage("生成完成！");
				try {
					Runtime.getRuntime().exec("cmd /c start explorer " + text_model_exportpath.getText());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}

	}
}
