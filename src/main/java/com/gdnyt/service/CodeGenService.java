package com.gdnyt.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import com.gdnyt.dao.MysqlTableDao;
import com.gdnyt.dao.TableDao;
import com.gdnyt.model.Setting;
import com.gdnyt.model.Table;
import com.gdnyt.utils.FileUtil;

import freemarker.template.TemplateException;

public class CodeGenService {
	Logger log = Logger.getLogger(CodeGenService.class);	
	private TableDao tableDao;	
	private Setting setting;
	private FreemarkerService freemarkerService;
	String charset="UTF-8";	
	
	String path="";
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}


	public CodeGenService(){
		freemarkerService=new FreemarkerService();
		setting= Setting.getInstance();
		tableDao=new MysqlTableDao();		
	}

	
	/**
	 * 根据model模板，用excel中的元数据，生成sql语句。
	 * @param model 模板字符串
	 * @param excelfile excel文件
	 * @param exportPath 输出文件目录
	 * @throws TemplateException 
	 */
	public void genSql(String model,String excelfile,String exportPath) throws TemplateException{
		List<Table> tables=ExcelService.readExcel(excelfile);
		Map map=new HashMap<>();
		map.put("tables", tables);
		String sql=freemarkerService.genByTextModel(model, map);
		if(sql!=null)
			FileUtil.saveToFile(exportPath+File.separator+"create.sql", sql,"UTF-8");
		else{
			JOptionPane.showMessageDialog(null, "生成失败");
		}
	}
	/**
	 * 根据模板字符串写入文件
	 */
	public String genCode(String template,String dbName,String tableName) throws TemplateException{
		Map<String, Object> map = setMap(dbName, tableName);
		String code=freemarkerService.genByTextModel(template, map);
		return code;
	}
	private Map<String, Object> setMap(String dbName, String tableName) {
		List<Table> tabless=getTables(dbName,tableName);
		Map<String ,Object> map=new HashMap<>();
		map.put("table", tabless.get(0));
		map.put("tables", tabless);
		for(Table table:tabless){
			map.put(table.getClassname(), table);
		}
		return map;
	}
	
	/**
	 * 写入单个文件
	 * @param dbname 数据库名字
	 * @param tables 表名
	 * @param tempStr 模板字符串
	 * @param outPath 输出路径
	 * @throws TemplateException
	 */
	public void genOneFile(String dbname,String tables,String tempStr,String outPath)throws TemplateException{
		List<Table> tabless=getTables(dbname,tables);
		Map<String ,Object> map=new HashMap<>();
		map.put("tables", tabless);
		String code=freemarkerService.genByTextModel(tempStr, map);
		if(code!=null){
			FileUtil.saveToFile(outPath, code,"UTF-8");
		}
	}
	
	
	/**
	 * 根据模板字符串，选择相应的表来生成代码
	 * @param template 模板字符串
	 * @param exportpath 生成的路径
	 * @param tables 表名字符串，以，间隔
	 * @param suffix 文件名后缀
	 * @param nameAppend 附加到文件名后的字符串
	 * @throws TemplateException 
	 */
	public void genCodeByTemplate(String dbname,String template,String exportpath,String tables,String suffix,String nameAppend) throws TemplateException{
		List<Table> tabless=getTables(dbname,tables);
		for(Table t:tabless){
			Map map=new HashMap<>();
			map.put("table", t);
			map.put("tables", tables);
			String code=freemarkerService.genByTextModel(template, map);
			if(code!=null){
				FileUtil.saveToFile(exportpath+File.separator+t.getClassname()+nameAppend+suffix, code,"UTF-8");
			}
		}
		
	}
	

	/**
	 * 根据表名字符串得到表模型
	 * @param tabless 以，分隔的表名串
	 * @return
	 */
	public List<Table> getTables(String dbname,String tabless){	
		Table table=null;
		String[] ss=tabless.split(",");
		if(ss.length==0)
			return new ArrayList<Table>();
		List<Table> tables=new ArrayList<>();
		for(String s:ss){
			table=tableDao.getTable(dbname,s);
			tables.add(table);
		}
		return tables;		
	}
	


	
}
