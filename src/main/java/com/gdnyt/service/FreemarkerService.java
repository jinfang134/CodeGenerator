package com.gdnyt.service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import org.apache.log4j.Logger;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class FreemarkerService {
	Logger log = Logger.getLogger(FreemarkerService.class);

	/**
	 * 根据字符串的模板语句生成代码
	 * 
	 * @param model
	 *            模板
	 * @param map
	 *            数据
	 * @return 生成内容
	 * @throws TemplateException 
	 */
	public String genByTextModel(String model, Map map) throws TemplateException {
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
		StringTemplateLoader stringLoader = new StringTemplateLoader();
		stringLoader.putTemplate("myTemplate", model);
		cfg.setTemplateLoader(stringLoader);
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		String htmlText = null;
		try {
			Template temp = cfg.getTemplate("myTemplate", "utf-8");
			StringWriter sw = new StringWriter();
			temp.process(map, sw);
			return sw.toString();
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e);
		} 
		return htmlText;
	}

}
