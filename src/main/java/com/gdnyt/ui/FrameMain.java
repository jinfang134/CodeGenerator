package com.gdnyt.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.UIManager;

import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.springframework.jdbc.core.JdbcTemplate;

import com.gdnyt.dao.TableDao;
import com.gdnyt.model.Setting;
import com.gdnyt.model.Table;
import com.gdnyt.service.CodeGenService;
import com.gdnyt.ui.action.ConnectDBAction;
import com.gdnyt.ui.component.DbBar;
import com.gdnyt.ui.component.StatusBar;
import com.gdnyt.ui.component.TreeViewPanel;
import com.gdnyt.ui.panel.PanelGenFold;
import com.gdnyt.ui.panel.PanelGenFromModel;
import com.gdnyt.ui.panel.PanelGenFromTable;
import com.gdnyt.ui.panel.PanelGenOnTime;
import com.gdnyt.ui.panel.PanelTableMsg;
import com.gdnyt.utils.MessageBox;
import com.gdnyt.view.TabbedPane;

import freemarker.log.Logger;
import jodd.io.FileUtil;

@org.springframework.stereotype.Component
public class FrameMain extends JFrame implements MouseListener, SyntaxConstants {
	private class AboutAction extends AbstractAction {

		public AboutAction() {
			putValue(NAME, "关于");

		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(FrameMain.this,
					"<html><b>代码生成工具</b> - 致力于快速的文档生成和代码开发！" + "<br>Version 1.0.1" + "<br>有问题请反馈作者。", "关于",
					JOptionPane.INFORMATION_MESSAGE);
		}

	}

	/**
	 * 添加界面
	 * 
	 * @author jinfang
	 * 
	 */
	private class AddFrameAction extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private FrameMain parent;

		public AddFrameAction(FrameMain parent) {
			putValue(NAME, "Bookmarks");
			this.parent = parent;
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == btnsql) {
				addFrame(GEN_ON_TIME, new PanelGenOnTime(genService, parent));
			}
			if (e.getSource() == btn_ModelGen) {
				addFrame("模板生成", new PanelGenFromModel(genService));
			}
			if (e.getSource() == btn_foldGen) {
				addFrame("批量生成", new PanelGenFold(genService));
			}
			if (e.getSource() == btn_table) {
				addFrame("按表生成", new PanelGenFromTable(genService));
			}
		}

		/**
		 * 添加tab页
		 * 
		 * @param name
		 * @param component
		 */
		private void addFrame(String name, Component component) {
			int i = 0;
			for (; i < tabbedPane.getTabCount(); i++) {
				String title = tabbedPane.getTabAt(i).getTitleLabel();
				if (title.equals(name)) {
					tabbedPane.setSelectedIndex(i);
					break;
				}

			}
			if (i == tabbedPane.getTabCount()) {
				tabbedPane.addTab(name, null, component, name);
				tabbedPane.setSelectedIndex(i);
			}
		}
	}

	/**
	 * 常用变量说明
	 * 
	 * @author jinfang
	 *
	 */
	private class CommonAction extends AbstractAction {

		public CommonAction() {
			putValue(NAME, "常用变量说明");
		}

		String filePath = "/html/test.html";

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JOptionPane.showMessageDialog(FrameMain.this, getText(), "常用变量说明", JOptionPane.INFORMATION_MESSAGE);
			new FrameHelper();

		}

		private String getText() {
			URL url = FrameMain.class.getResource(filePath);
			File file = new File(url.getFile());
			String content = null;
			try {
				content = FileUtil.readString(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return content;
		}

	}

	public static JProgressBar progressBar;

	public static JLabel status_text;
	public static JTree tree;
	public static String[] tablenames;
	private static final String GEN_ON_TIME = "实时生成";
	private static final long serialVersionUID = 1L;

	private static Font font = new Font("微软雅黑", Font.PLAIN, 14);
	Logger log = Logger.getLogger(this.getName());
	private JdbcTemplate jdbcTemplate;
	private JdbcTemplate jdbcTemplateForTable;
	private TableDao tableDao;
	CodeGenService genService;
	private JComboBox db_comboBox;
	private JTextField host_text, port_text, pwd_text;
	private Setting setting;
	private JTextField user_text;
	private JButton initbutton, connectButton, btnsql, btn_table;

	private JButton btn_ModelGen, btn_foldGen;
	private JMenu menu;

	private TabbedPane tabbedPane;

	private JScrollPane treeView;

	/**
	 * Create the application.
	 */
	public FrameMain(JdbcTemplate jdbcTemplate, JdbcTemplate jdbcTemplate2, TableDao tableDao,
			CodeGenService genService) {
		this.jdbcTemplateForTable = jdbcTemplate2;
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrameMain.class.getResource("/icon/basket_shopping.png")));
		initFont();
		setting = Setting.getInstance();
		this.genService = genService;
		this.tableDao = tableDao;
		this.jdbcTemplate = jdbcTemplate;
		initialize();
		ConnectDBAction connectDBAction = new ConnectDBAction(tableDao, jdbcTemplate);
		// loadTree();
	}

	// 添加中心内容块
	private void addContent() {
		tabbedPane = new TabbedPane();
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		tabbedPane.setCloseButtonEnabled(true);
		// tabbedPane.addTab("模板生成", null, makeGenFromModel(), "根据现有的模板来生成代码");
		// tabbedPane.addTab("生成sql", null, makeGenSql(), "根据excel生成sql");
		PanelGenFold batchGen = new PanelGenFold(genService);
		tabbedPane.addTab("批量生成", null, batchGen);
	}

	// [end]

	// [start] 界面布局

	// 添加初始化工具栏
	private void addToolBar() {

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(3, 1));

		JMenuBar menuBar = new JMenuBar();
		panel.add(menuBar);
		createMenu(menuBar);// 创建菜单

		DbBar toolBar = new DbBar();
		panel.add(toolBar);

		JToolBar toolBar_1 = new JToolBar();
		panel.add(toolBar_1);

		btnsql = new JButton(GEN_ON_TIME);
		btnsql.addActionListener(new AddFrameAction(this));
		btnsql.setIcon(new ImageIcon(FrameMain.class.getResource("/icon/database_blue.png")));
		toolBar_1.add(btnsql);

		btn_table = new JButton("按表生成");
		btn_table.addActionListener(new AddFrameAction(this));
		btn_table.setIcon(new ImageIcon(FrameMain.class.getResource("/icon/database_blue.png")));
		toolBar_1.add(btn_table);

		btn_ModelGen = new JButton("模板生成");
		btn_ModelGen.addActionListener(new AddFrameAction(this));
		btn_ModelGen.setToolTipText("根据自定义模板生成代码");
		btn_ModelGen.setIcon(new ImageIcon(FrameMain.class.getResource("/icon/apply_to_all.png")));
		toolBar_1.add(btn_ModelGen);

		btn_foldGen = new JButton("批量生成");
		btn_foldGen.addActionListener(new AddFrameAction(this));
		btn_foldGen.setToolTipText("根据自定义模板文件夹批量生成代码");
		btn_foldGen.setIcon(new ImageIcon(FrameMain.class.getResource("/icon/apply_to_all.png")));
		toolBar_1.add(btn_foldGen);

	}

	/**
	 * 创建菜单
	 * 
	 * @param menuBar
	 */
	private void createMenu(JMenuBar menuBar) {
		menu = new JMenu("文件");
		menu.setIcon(new ImageIcon(FrameMain.class.getResource("/icon/document_yellow.png")));
		menuBar.add(menu);
		menu = new JMenu("设置");
		menu.setIcon(new ImageIcon(FrameMain.class.getResource("/icon/setting_tools.png")));
		JMenuItem menuItem = new JMenuItem("默认数据设置");
		menu.add(menuItem);
		menuBar.add(menu);
		menu = new JMenu("帮助");
		menu.setIcon(new ImageIcon(FrameMain.class.getResource("/icon/help.png")));
		JMenuItem item = new JMenuItem(new AboutAction());
		menu.add(item);
		item = new JMenuItem(new CommonAction());
		menu.add(item);
		menuBar.add(menu);

	}

	public void initFont() {
		UIManager.put("Button.font", font);
		UIManager.put("Label.font", font);
		UIManager.put("TextField.font", font);
		UIManager.put("ComboBox.font", font);
		UIManager.put("RadioButton.font", font);
		UIManager.put("TabbedPane.font", font);
		UIManager.put("Table.font", font);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		getContentPane().setLayout(new BorderLayout());
		setTitle("Java代码生成工具");
		setBounds(100, 100, 1000, 442);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(Frame.MAXIMIZED_BOTH); // 最大化
		getContentPane().add(new TreeViewPanel(), BorderLayout.WEST);
		addToolBar();
		addContent();
		getContentPane().add(new StatusBar(), BorderLayout.SOUTH);
	}

	// [end]

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
			String tablename = tablenames[index];
			Table table = tableDao.getTable(setting.getDbname(), tablename);
			// 检查此表是否已经打开
			int i = 0;
			for (; i < tabbedPane.getTabCount(); i++) {
				String title = tabbedPane.getTabAt(i).getTitleLabel();
				if (title.equals(table.getName())) {
					tabbedPane.setSelectedIndex(i); // 如果已经存在
					break;
				}
			}
			if (i == tabbedPane.getTabCount()) {
				JPanel panel = new PanelTableMsg(table);
				tabbedPane.addTab(table.getName(), null, panel, "表详细信息");
				tabbedPane.setSelectedIndex(i);
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void setStatusText(String text) {
		this.status_text.setText(text);
	}
}
