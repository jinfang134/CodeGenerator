package ${table.packagename!}.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import com.gdnyt.controller.base.BaseController;
import com.gdnyt.pageModel.base.Grid;
import com.gdnyt.pageModel.base.Json;
import com.gdnyt.pageModel.base.PageFilter;

import ${table.packagename!}.model.${table.classname!};
import ${table.packagename!}.service.${table.classname!}ServiceI;

/**
* @author 左金芳
*${table.comment!}的控制器
*/
@Controller
@RequestMapping("/${table.lowername}")
public class ${table.classname}Controller extends BaseController{

	Logger logger=Logger.getLogger(${table.classname}Controller.class);
	
	@Resource
	private ${table.classname}ServiceI ${table.lowername}Service;
	
	@RequestMapping("/manager")
	public String get(){		
		return "${viewset!}/${table.lowername}";
	}
	
	<#assign val=''>
	<#assign canshu=''>
	<#list table.columnlist as item>
		<#if item.type='Date'>
			<#assign val=val+',String '+item.name+'Start,String '+item.name+'End'>
			<#assign canshu=canshu+',DateTimeHelper.parse('+item.name+'Start),DateTimeHelper.parse('+item.name+'End)'>
		</#if>
	</#list>
	
	/**
	* 数据表
	*/
	@RequestMapping("/dataGrid")
	@ResponseBody
	@Log(description="查看${table.comment!}")
	public Grid dataGrid(${table.classname} ${table.lowername}, PageFilter ph ${val} ) {
		Grid grid = new Grid();
		grid.setRows(${table.lowername}Service.dataGrid(${table.lowername}, ph ${canshu}));
		grid.setTotal(${table.lowername}Service.count(${table.lowername}, ph ${canshu} ));
		return grid;
	}
	
	@RequestMapping("/add")
	@ResponseBody
	@Log(description="添加${table.comment!}")
	public Json add(${table.classname} ${table.lowername}) {
		${table.lowername}.setId(UUID.randomUUID().toString().replace("-", ""));
		Json j = new Json();
		${table.lowername}Service.add(${table.lowername});
		j.setSuccess(true);
		j.setMsg("添加成功");
		return j;
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public Json save(@RequestBody ${table.classname} ${table.lowername}) {
		${table.lowername}.setId(UUID.randomUUID().toString().replace("-", ""));
		Json j = new Json();
		${table.lowername}Service.add(${table.lowername});
		j.setSuccess(true);
		j.setMsg("添加成功");
		return j;
	}

	@RequestMapping("/delete")
	@ResponseBody
	@Log(description="删除${table.comment!}")
	public Json delete(String ids) {
		Json j = new Json();
		${table.lowername}Service.delete(ids);
		j.setMsg("删除成功");
		j.setSuccess(true);
		return j;
	}

	@RequestMapping("/get")
	@ResponseBody
	public ${table.classname} get(String id)  {
		return (${table.classname})${table.lowername}Service.get(id);
	}

	@RequestMapping("/edit")
	@ResponseBody
	@Log(description="编辑${table.comment!}")
	public Json edit(${table.classname} ${table.lowername}) {
		Json j = new Json();
		${table.lowername}Service.edit(${table.lowername});
		j.setSuccess(true);
		j.setMsg("编辑成功");
		return j;
	}
	
	/**
	 * 导出为excel或者word
	 * @param data 
	 * @param response
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/export")
	@Log(description="导出${table.comment!}")
	public void exportExcelAndWord(String data,String type,HttpServletResponse response) throws UnsupportedEncodingException{
    
        String exportname="${table.comment}";  
        data=StringEscapeUtils.unescapeHtml4(data);
    //    data=URLDecoder.decode(data,"UTF-8");
        logger.info(data);
        try {  
            if(type.equals("excel"))  
            {  
            	exportname+=".xls";  
            	response.reset();
            	  response.setContentType("application/vnd.ms-excel;;charset=UTF-8");
                  response.setHeader("Content-Disposition", "attachment;filename="
                          .concat(new String((exportname).getBytes("gbk"),"iso8859-1")));
            }  
            else if(type.equals("word"))  
            {  
                exportname+=".doc";  
                response.setHeader("Content-disposition", "attachment; filename="
                		 .concat(new String((exportname).getBytes("gbk"),"iso8859-1")));  
                response.setContentType("application/ms-word;charset=GBK");  
            }  
        } catch (UnsupportedEncodingException e) {  
        // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        
        PrintWriter out;  
        try {  
            out = response.getWriter();  
            out.print("<html>\n<head>\n"); 
            out.print("<style type=\"text/css\">\n.pb{font-size:13px;border-collapse:collapse;} "+  
                    "\n.pb th{font-weight:bold;text-align:center;border:0.5pt solid windowtext;padding:2px;} " +  
                    "\n.pb td{border:0.5pt solid windowtext;padding:2px;}\n</style>\n</head>\n"); 
            out.println("<body>"+data+"</body></html>");  
            out.close();  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  	
	}
	
}

	