package com.gdnyt.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
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
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.gdnyt.dao.MysqlTableDao;
import com.gdnyt.model.Setting;
import com.gdnyt.model.Table;
import com.gdnyt.service.CodeGenService;
import com.gdnyt.utils.MessageBox;
import com.gdnyt.view.MyDefaultTreeCellRenderer;
import com.gdnyt.view.TabbedPane;

import freemarker.log.Logger;

@SpringBootApplication
public class FrameMain extends JFrame implements MouseListener, SyntaxConstants {
	public static  JProgressBar progressBar;
	public static JLabel status_text;
	public static JTree tree;
	public static  String[] tablenames;
	private static final String GEN_ON_TIME = "实时生成";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger log = Logger.getLogger(this.getName());
	private JComboBox db_comboBox;
	private static Font font = new Font("微软雅黑", Font.PLAIN, 14);
	private JTextField host_text, port_text, pwd_text;
	private Setting setting;
	private JTextField user_text;
	private JButton initbutton,connectButton, btnsql;
	private JButton btn_ModelGen,btn_foldGen;	
	private JMenu menu;
	private MysqlTableDao tableDao;
	private TabbedPane tabbedPane;
	private JScrollPane treeView;
	CodeGenService genService;
	private JPasswordField passwordField;
	
	/**
	 * Create the application.
	 */
	public FrameMain() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrameMain.class.getResource("/icon/basket_shopping.png")));
		initFont();
		setting = Setting.getInstance();		
		genService = new CodeGenService();
		tableDao = new MysqlTableDao();
		initialize();
		loadSetting();
		// loadTree();
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					FrameMain window = new FrameMain();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

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
			Table table = tableDao.getTable(setting.getDbname(),tablename);
			//检查此表是否已经打开
			int i = 0;
			for (; i < tabbedPane.getTabCount(); i++) {
				String title = tabbedPane.getTabAt(i).getTitleLabel();
				if (title.equals(table.getName())) {
					tabbedPane.setSelectedIndex(i);	//如果已经存在
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

	// [end]

	// [start] 界面布局

	// 添加中心内容块
	private void addContent() {
		tabbedPane = new TabbedPane();
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		tabbedPane.setCloseButtonEnabled(true);
	//	tabbedPane.addTab("模板生成", null, makeGenFromModel(), "根据现有的模板来生成代码");
		// tabbedPane.addTab("生成sql", null, makeGenSql(), "根据excel生成sql");
		tabbedPane.addTab("批量生成", null, new PanelGenFold());
	}

	/**
	 * 添加状态栏
	 */
	private void addStatusBar() {
		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_1.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		getContentPane().add(panel_1, BorderLayout.SOUTH);
		progressBar = new JProgressBar();
		panel_1.add(progressBar);
		status_text = new JLabel("就绪！");
		panel_1.add(status_text);
	}
	/**
	 * 创建菜单
	 * @param menuBar
	 */
	private void createMenu(JMenuBar menuBar){
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
	
	// 添加初始化工具栏
	private void addToolBar() {

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(3, 1));

		JMenuBar menuBar = new JMenuBar();
		panel.add(menuBar);
		createMenu(menuBar);//创建菜单
		
		JToolBar toolBar = new JToolBar();
		panel.add(toolBar);

		JLabel label = new JLabel(" 数据库地址：");
		toolBar.add(label);

		host_text = new JTextField();
		toolBar.add(host_text);
		host_text.setColumns(7);

		JLabel lblNewLabel_5 = new JLabel(" 端口：");
		toolBar.add(lblNewLabel_5);

		port_text = new JTextField();
		toolBar.add(port_text);
		port_text.setColumns(5);

		JLabel lblNewLabel = new JLabel(" 账号：");
		toolBar.add(lblNewLabel);

		user_text = new JTextField();
		toolBar.add(user_text);
		user_text.setColumns(6);

		JLabel lblNewLabel_1 = new JLabel(" 密码：");
		toolBar.add(lblNewLabel_1);

		pwd_text = new JPasswordField();
		pwd_text.setColumns(6);
		toolBar.add(pwd_text);
	

		connectButton = new JButton("连接数据库");
		connectButton.addActionListener(new ConnectDBAction());
		connectButton.setIcon(new ImageIcon(FrameMain.class.getResource("/icon/database_lightning.png")));
		toolBar.add(connectButton);

		JLabel lblNewLabel_2 = new JLabel(" 数据库：");
		toolBar.add(lblNewLabel_2);

		db_comboBox = new JComboBox();
		toolBar.add(db_comboBox);

		// 添加初始化按钮
		initbutton = new JButton(" 加载表");
		initbutton.setIcon(new ImageIcon(FrameMain.class.getResource("/icon/refresh.png")));
		initbutton.addActionListener(new LoadDBAction());
		toolBar.add(initbutton);

		JToolBar toolBar_1 = new JToolBar();
		panel.add(toolBar_1);

		btnsql = new JButton(GEN_ON_TIME);
		btnsql.addActionListener(new AddFrameAction());
		btnsql.setIcon(new ImageIcon(FrameMain.class.getResource("/icon/database_blue.png")));
		toolBar_1.add(btnsql);

		btn_ModelGen = new JButton("模板生成");
		btn_ModelGen.addActionListener(new AddFrameAction());
		btn_ModelGen.setToolTipText("根据自定义模板生成代码");
		btn_ModelGen.setIcon(new ImageIcon(FrameMain.class.getResource("/icon/apply_to_all.png")));
		toolBar_1.add(btn_ModelGen);
		
		btn_foldGen = new JButton("批量生成");
		btn_foldGen.addActionListener(new AddFrameAction());
		btn_foldGen.setToolTipText("根据自定义模板文件夹批量生成代码");
		btn_foldGen.setIcon(new ImageIcon(FrameMain.class.getResource("/icon/apply_to_all.png")));
		toolBar_1.add(btn_foldGen);
	

	}

	private void addTree() {
		tree = new JTree();
		tree.setBackground(Color.WHITE);
		tree.setForeground(Color.LIGHT_GRAY);
		tree.setFont(font);
		tree.setScrollsOnExpand(true);
		tree.addMouseListener(this);
		treeView = new JScrollPane(tree);
		treeView.setBounds(treeView.getX(), treeView.getY(), 200, treeView.getHeight());
		getContentPane().add(treeView, BorderLayout.WEST);
	}

	// [end]

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
		addTree();
		addToolBar();
		addContent();
		addStatusBar();
	}

	

	private void loadSetting() {
		host_text.setText(setting.getHost());
		port_text.setText(setting.getPort());
		user_text.setText(setting.getUser());
		pwd_text.setText(setting.getPwd());
	}

	/**
	 * 加载树
	 */
	private void loadTree() {

		String dbname = (String) db_comboBox.getSelectedItem();
		tablenames = tableDao.getTableNames(dbname);
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(dbname);
		for (String s : tablenames) {
			DefaultMutableTreeNode tableNode = new DefaultMutableTreeNode(s);
			node.add(tableNode);
		}
		tree.setModel(new DefaultTreeModel(node));
		// 设置自定义描述类
		tree.setCellRenderer(new MyDefaultTreeCellRenderer());
		treeView.setSize(200, treeView.getHeight());
		treeView.updateUI();
		tabbedPane.updateUI();
	}




	


	

	private class ConnectDBAction extends AbstractAction {

		public ConnectDBAction() {
			putValue(NAME, "Bookmarks");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			setting.setHost(host_text.getText());
			setting.setPort(port_text.getText());
			setting.setPwd(pwd_text.getText());
			setting.setUser(user_text.getText());
			loadDataBase();
		}
		
		private void loadDataBase() {

			try {
				String[] schema = tableDao.getSchemaList();
				db_comboBox.setModel(new DefaultComboBoxModel<>(schema));
				db_comboBox.setSelectedItem(setting.getDbname());
				JOptionPane.showMessageDialog(null, "数据库连接成功", "恭喜您", JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "数据库连接失败", "出错啦", JOptionPane.ERROR_MESSAGE);
			}
		}

	}

	private class LoadDBAction extends AbstractAction {

		public LoadDBAction() {
			putValue(NAME, "Bookmarks");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			setting.setDbname((String) db_comboBox.getSelectedItem());
			loadTree();
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

		public AddFrameAction() {
			putValue(NAME, "Bookmarks");
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

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if (e.getSource() == btnsql) {
				addFrame(GEN_ON_TIME, new PanelGenOnTime());
			}
			if (e.getSource() == btn_ModelGen) {
				addFrame("模板生成", new PanelGenFromModel());
			}
			if(e.getSource()==btn_foldGen){
				addFrame("批量生成", new PanelGenFold());
			}
		}
	}




	private class AboutAction extends AbstractAction {

		public AboutAction() {
			putValue(NAME, "关于");
			
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(FrameMain.this,
					"<html><b>代码生成工具</b> - 致力于快速的文档生成和代码开发！" +
					"<br>Version 1.0.1" +
					"<br>有问题请反馈作者。",
					"关于",
					JOptionPane.INFORMATION_MESSAGE);
		}

	}
	
	/**
	 * 常用变量说明
	 * @author jinfang
	 *
	 */
	private class CommonAction extends AbstractAction{

		public CommonAction(){
			putValue(NAME, "常用变量说明");
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JOptionPane.showMessageDialog(FrameMain.this,
					getText("./html/common.html"),
					"常用变量说明",
					JOptionPane.INFORMATION_MESSAGE);		
		}
		
		private String getText(String resource) {
			ClassLoader cl = getClass().getClassLoader();
			BufferedReader r = null;
			try {
				r = new BufferedReader(new InputStreamReader(
						cl.getResourceAsStream(resource), "UTF-8"));
				StringBuilder line=new StringBuilder();
				String lineTxt = null;
				while((lineTxt = r.readLine()) != null){
					line.append(lineTxt+"\n");
				}
				
				r.close();
				return line.toString();
			} catch (RuntimeException re) {
				throw re; // FindBugs
			} catch (Exception e) { // Never happens
				
			}
			return null;
		}

		
		
	}
}
