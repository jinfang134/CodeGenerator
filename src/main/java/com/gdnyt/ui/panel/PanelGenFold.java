package com.gdnyt.ui.panel;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gdnyt.dao.MysqlTableDao;
import com.gdnyt.model.Setting;
import com.gdnyt.service.CodeGenService;
import com.gdnyt.ui.FrameMain;
import com.gdnyt.utils.FileUtil;
import com.gdnyt.utils.MessageBox;

import freemarker.template.TemplateException;
import net.miginfocom.swing.MigLayout;

import java.awt.Color;

/**
 * 
 * 
 * 选中一个文件夹，批量生成文件夹中的模板的代码。
 * 
 * @author jinfang
 *
 */

public class PanelGenFold extends JPanel {

	private JTextField text_temp, path_text, package_text, prefix_text, viewset;
	private JButton pathButton, genButton, btn_temppath;
	private JRadioButton mustOpenButton;	
	private CodeGenService genService;
	private Setting setting;
	private JTextField textField;
	private JTextArea txtarea_msg;
	private JLabel label;

	
	
	public PanelGenFold(CodeGenService genService) {
		initView();
		setting = Setting.getInstance();
		this.genService=genService;
		path_text.setText(setting.getPath() == null ? "" : setting.getPath());
		package_text.setText(setting.getPackagename());
		prefix_text.setText(setting.getPrefix());
	}

	/**
	 * 界面布局
	 */
	private void initView() {

		setLayout(new MigLayout("", "[40px][200px,grow][130px]", "[23px][23px][][][][][23px][23px][][grow]"));

		JLabel lblNewLabel_5 = new JLabel("模板目录：");
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblNewLabel_5, "cell 0 0,alignx right");
		text_temp = new JTextField();
		add(text_temp, "cell 1 0,growx,aligny center");
		text_temp.setColumns(10);

		btn_temppath = new JButton("选择");
		btn_temppath.setIcon(new ImageIcon(FrameMain.class.getResource("/icon/document_yellow.png")));
		btn_temppath.addActionListener(new SelectPathAction());
		add(btn_temppath, "cell 2 0,alignx left,aligny top");

		JLabel lblNewLabel_4 = new JLabel("生成目录：");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblNewLabel_4, "cell 0 1,alignx right");

		path_text = new JTextField();
		add(path_text, "cell 1 1,growx,aligny center");
		path_text.setColumns(10);

		pathButton = new JButton("选择");
		pathButton.setIcon(new ImageIcon(FrameMain.class.getResource("/icon/document_yellow.png")));
		pathButton.addActionListener(new SelectPathAction());
		add(pathButton, "cell 2 1,alignx left,aligny top");
		
		label = new JLabel("注意：生成代码时会先清空此目录，请慎重！");
		label.setForeground(Color.RED);
		add(label, "cell 1 2");

		JLabel lblNewLabel_6 = new JLabel("包名：");
		lblNewLabel_6.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblNewLabel_6, "cell 0 3,alignx right");

		package_text = new JTextField();
		add(package_text, "cell 1 3,growx");
		package_text.setColumns(10);

		JLabel lblNewLabel_14 = new JLabel("基础包名");
		add(lblNewLabel_14, "cell 2 3");

		JLabel lblNewLabel_8 = new JLabel("表名前缀：");
		lblNewLabel_8.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblNewLabel_8, "cell 0 4,alignx right");

		prefix_text = new JTextField();
		add(prefix_text, "cell 1 4,alignx left");
		prefix_text.setColumns(10);

		JLabel lbla = new JLabel("参数a：");
		lbla.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lbla, "cell 0 5,alignx right");

		viewset = new JTextField();
		add(viewset, "cell 1 5,alignx left");
		viewset.setColumns(10);

		ButtonGroup gentype = new ButtonGroup();

		genButton = new JButton("生成代码");
		genButton.setIcon(new ImageIcon(FrameMain.class.getResource("/icon/printer.png")));
		genButton.addActionListener(new GenFrameCodeAction());

		JLabel lblb = new JLabel("参数b：");
		add(lblb, "cell 0 6,alignx trailing");

		textField = new JTextField();
		textField.setColumns(10);
		add(textField, "cell 1 6,alignx left");
		add(genButton, "cell 1 8,alignx left,aligny center");

		JLabel lblNewLabel_3 = new JLabel("完成后打开：");
		add(lblNewLabel_3, "cell 0 7,alignx right");

		ButtonGroup group = new ButtonGroup();

		mustOpenButton = new JRadioButton("是");
		mustOpenButton.setSelected(true);
		add(mustOpenButton, "flowx,cell 1 7");

		JRadioButton radioButton_1 = new JRadioButton("否");
		add(radioButton_1, "cell 1 7");

		txtarea_msg = new JTextArea();
		txtarea_msg.setColumns(5);
		txtarea_msg.setRows(3);
		add(txtarea_msg, "cell 0 9 3 1,grow");

		group.add(mustOpenButton);
		group.add(radioButton_1);
	}

	/**
	 * 选择生成的路径
	 * 
	 * @author jinfang
	 *
	 */
	private class SelectPathAction extends AbstractAction {
		org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(SelectPathAction.class);

		public SelectPathAction() {
			putValue(NAME, "Bookmarks");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfc = new JFileChooser("./");
			jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			jfc.showDialog(new JLabel(), "选择");
			File file = jfc.getSelectedFile();
			if (file != null && file.isDirectory()) {
				if (e.getSource() == pathButton) {
					path_text.setText(file.getAbsolutePath());
				}
				if (e.getSource() == btn_temppath) {
					text_temp.setText(file.getAbsolutePath());
				}
			} else if (file != null && file.isFile()) {
				System.out.println("文件:" + file.getAbsolutePath());
			}
		}
	}

	/**
	 * 生成代码的事件 *
	 * 
	 * @author jinfang
	 * 
	 */
	private class GenFrameCodeAction extends AbstractAction implements Runnable {
		Logger logger = Logger.getLogger(GenFrameCodeAction.class);

		String selectedTableNames;

		public GenFrameCodeAction() {
			putValue(NAME, "Genframecode");
		}

		/**
		 * 保存配置信息
		 */
		private void saveConfig() {
			setting.setPackagename(package_text.getText());
			setting.setPrefix(prefix_text.getText());
			setting.setPath(path_text.getText());
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			int[] rows = FrameMain.tree.getSelectionRows();
			if (rows.length <= 0) {
				MessageBox.showErrorMessage("请选择要生成的表");
				return;
			}
			StringBuilder sb = new StringBuilder();
			for (int i : rows) {
				sb.append(FrameMain.tablenames[i - 1] + ",");
			}
			selectedTableNames = sb.toString();
			String path = path_text.getText();
			genService.setPath(path);
			saveConfig();
			Thread thread = new Thread(this);
			thread.start();
		}

		/**
		 * 根据某个模板文件的名字生成各个表的代码
		 * 
		 * @param tempName
		 */
		private void genForOneTemp(String tempName) {
			txtarea_msg.append("使用模板" + tempName + "\n");
			String temp = FileUtil.readTxtFile(text_temp.getText() + File.separator + tempName);
			if (tempName.contains("#")) {
				String[] ss = tempName.split("#");
				String packagename = package_text.getText() + "." + ss[0];
				packagename = packagename.replace(".", File.separator);
				String outpath = path_text.getText() + File.separator + packagename;
				FileUtil.makeDir(outpath);
				txtarea_msg.append("输出路径：" + outpath + "\n");
				String[] kk = ss[1].split("\\.");
				String suffix = kk[1];
				String append = kk[0];
				try {
					genService.genCodeByTemplate(setting.getDbname(), temp, outpath, selectedTableNames, "." + suffix,
							append);
				} catch (TemplateException e) {
					e.printStackTrace();
					txtarea_msg.append("异常：" + e.getMessage());
					logger.error(e);
				}
			}else if(tempName.contains("@")){
				String[] strings=tempName.split("@");
				String fileName=strings[1].substring(0, strings[1].indexOf(".ftl"));
				String outPath=path_text.getText()+File.separator+strings[0];
				FileUtil.makeDir(outPath);
				txtarea_msg.append("输出路径：" + outPath + "\n");
				try {
					genService.genOneFile(setting.getDbname(), selectedTableNames, temp, outPath+File.separator+fileName);
				} catch (TemplateException e) {
					e.printStackTrace();
					txtarea_msg.append("异常：" + e.getMessage());
					logger.error(e);
				}
				
			}else{
				MessageBox.showErrorMessage(tempName+"命名不规范！");
			}
		}

		@Override
		public void run() {
			String path = text_temp.getText();
			File file = new File(path);
			try {
				if (file.exists() && file.isDirectory()) {
					String[] list = file.list();
					FileUtil.removeDir(path_text.getText());
					txtarea_msg.setText("===================开始生成代码=====================\n");
					for (String filename : list) {
						genForOneTemp(filename);
					}
				}
			} catch (Exception e) {
				txtarea_msg.append("异常：" + e.getMessage());
				e.printStackTrace();
			}
		}

	}

}
