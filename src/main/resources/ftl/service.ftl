package ${table.packagename}.service.impl;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import com.gdnyt.pageModel.base.PageFilter;
import ${table.packagename}.dao.${table.classname}DaoI;
import ${table.packagename}.service.${table.classname}ServiceI;

@Service
public class ${table.classname}ServiceImpl<${table.classname}> implements ${table.classname}ServiceI<${table.classname}>{
	Logger log=Logger.getLogger(${table.classname}ServiceImpl.class);
	
	@Resource(name="${table.lowername}Dao")
	private ${table.classname}DaoI<${table.classname}> ${table.lowername}Dao;
	
	<#assign val=''>
	<#assign canshu=''>
	<#list table.columnlist as item>
		<#if item.type='Date'>
			<#assign val=val+',Date '+item.name+'Start,Date '+item.name+'End'>
			<#assign canshu=canshu+','+item.name+'Start,'+item.name+'End'>
		</#if>
	</#list>
	
	@Override
	public List<${table.classname}> dataGrid(${table.classname} ${table.lowername}, PageFilter ph ) {
		// TODO Auto-generated method stub		
		return ${table.lowername}Dao.dataGrid(${table.lowername}, ph );
	}	
	
	
	@Override
	public List<${table.classname}> dataGrid(${table.classname} ${table.lowername}, PageFilter ph ${val} ) {
		// TODO Auto-generated method stub		
		return ${table.lowername}Dao.dataGrid(${table.lowername}, ph ${canshu});
	}	
	
	@Override
	public Long count(${table.classname} ${table.lowername}, PageFilter ph) {
		// TODO Auto-generated method stub
		return  ${table.lowername}Dao.count( ${table.lowername}, ph );
	}
	
	@Override
	public Long count(${table.classname} ${table.lowername}, PageFilter ph ${val}) {
		// TODO Auto-generated method stub
		return  ${table.lowername}Dao.count( ${table.lowername}, ph ${canshu});
	}

	@Override
	public void add(${table.classname} ${table.lowername}) {
		// TODO Auto-generated method stub
		${table.lowername}Dao.save(${table.lowername});
	}

	@Override
	public void delete(String ids) {
		// TODO Auto-generated method stub
		${table.lowername}Dao.delete(ids);	
	}

	@Override
	public void edit(${table.classname} ${table.lowername}) {
		// TODO Auto-generated method stub
		${table.lowername}Dao.saveOrUpdate(${table.lowername});
	}

	@Override
	public ${table.classname} get(String id) {
		// TODO Auto-generated method stub
		return ${table.lowername}Dao.get(id);
	}

}

	