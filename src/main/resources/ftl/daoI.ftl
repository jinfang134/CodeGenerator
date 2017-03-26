package ${table.packagename}.dao;
import com.gdnyt.dao.IDao;
import java.util.Date;
import java.util.List;
import com.gdnyt.pageModel.base.PageFilter;

/**
* ${table.comment}dao 接口
*/
public interface ${table.classname}DaoI<T>  extends IDao<T> {
	<#assign val=''>
	<#list table.columnlist as item>
		<#if item.type='Date'>
			<#assign val=val+',Date '+item.name+'Start,Date '+item.name+'End'>			
		</#if>
	</#list>
	
	public List<T> dataGrid(T t, PageFilter ph ${val} );
	public Long count(T payment, PageFilter ph ${val} );
			
}


