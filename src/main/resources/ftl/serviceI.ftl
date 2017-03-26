package ${table.packagename}.service;
import java.util.List;
import com.gdnyt.model.*;
import ${table.packagename}.*;
import com.gdnyt.service.IBaseService;
import java.util.Date;
import com.gdnyt.pageModel.base.PageFilter;

/**
* ${table.comment}服务接口层
* 公司：广东南粤通客运联网中心
*/
public interface ${table.classname}ServiceI<T> extends IBaseService<T>{
	<#assign val=''>
	<#list table.columnlist as item>
		<#if item.type='Date'>
			<#assign val=val+',Date '+item.name+'Start,Date '+item.name+'End'>			
		</#if>
	</#list>
	
	public List<T> dataGrid(T t, PageFilter ph ${val} );
	public Long count(T t, PageFilter ph ${val} );
}

