package ${table.packagename}.dao;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import com.gdnyt.dao.BaseDaoImpl;
import com.gdnyt.pageModel.base.PageFilter;
import ${table.packagename}.model.Payment;
import java.util.Date;

/**
* hibernate dao实现层自动模板
* 作者：左金芳
* 公司：南粤通
* ${table.comment!}
*/
@Repository(value="${table.lowername}Dao")
public class ${table.classname}Dao extends BaseDaoImpl<${table.classname}> implements ${table.classname}DaoI<${table.classname}>{
	Logger log=Logger.getLogger(${table.classname}Dao.class);
	
	@Resource(name="${table.lowername}Dao")
	private ${table.classname}DaoI<${table.classname}> ${table.lowername}Dao;
	
	<#assign val=''>
	<#assign canshu=''>
	<#list table.columnlist as item>
		<#if item.type='Date'>
			<#assign val=val+',Date '+item.name+'Start,Date '+item.name+'End'>
			<#assign canshu=canshu+',DateTimeHelper.parse('+item.name+'Start),DateTimeHelper.parse('+item.name+'End)'>
		</#if>
	</#list>
	
	
	@Override
	public List< ${table.classname}> dataGrid( ${table.classname} ${table.lowername}, PageFilter ph) {
		// TODO Auto-generated method stub		
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from ${table.classname} t ";
		List<${table.classname}> l = find(hql + whereHql(${table.lowername}, params)
				+ orderHql(ph), params, ph.getPage(), ph.getRows());
		return l;
	}
	
	@Override
	public List< ${table.classname}> dataGrid( ${table.classname} ${table.lowername}, PageFilter ph ${val} ) {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from ${table.classname} t ";
		hql+=whereHql(${table.lowername}, params);
		<#list table.columnlist as item>
		<#if item.type='Date'>
		if(${item.name}End!=null){
			hql+=" and t.${item.name}< :${item.name}End";			
			params.put("${item.name}End", ${item.name}End);
		}
		if(${item.name}Start!=null){
			hql+=" and t.${item.name} > :${item.name}Start ";			
			params.put("${item.name}Start", ${item.name}Start);
		}
		</#if>
		</#list>
		List<${table.classname}> l = find(hql+ orderHql(ph), params, ph.getPage(), ph.getRows());
		return l;
	}
	

	/**
	 * 构造排序语句
	 * @param ph
	 * @return
	 */
	private String orderHql(PageFilter ph) {
		String orderString = "";
		if ((ph.getSort() != null) && (ph.getOrder() != null)) {
			orderString = " order by t." + ph.getSort() + " " + ph.getOrder();
		}
		return orderString;
	}
	
	/**
	 * 构造where语句
	 * @param station
	 * @param params
	 * @return
	 */
	private String whereHql(${table.classname} ${table.lowername}, Map<String, Object> params) {
		String hql = "";
		if (${table.lowername} != null) {
			hql += " where 1=1 ";
		}
	<#list table.columnlist as item>
	<#if (item.type="String"&&item.name!="id")>
		if(${table.lowername}.get${item.bigname}()!=null&&!${table.lowername}.get${item.bigname}().equals("")){
			hql+= " and t.${item.name} like :${item.name}";
			params.put("${item.name}", "%%" + ${table.lowername}.get${item.bigname}() + "%%");			
		}
	</#if>
	</#list> 		
		return hql;
	}
	
	@Override
	public Long count(${table.classname} ${table.lowername}, PageFilter ph) {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from ${table.classname} t ";
		return count("select count(*) " + hql + whereHql(${table.lowername}, params), params);
	}

	@Override
	public Long count(${table.classname} ${table.lowername}, PageFilter ph ${val} ){
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from ${table.classname} t ";
		hql+=whereHql(${table.lowername}, params);		
		<#list table.columnlist as item>
		<#if item.type='Date'>
		if(${item.name}End!=null){
			hql+=" and t.${item.name}< :${item.name}End";			
			params.put("${item.name}End", ${item.name}End);
		}
		if(${item.name}Start!=null){
			hql+=" and t.${item.name} > :${item.name}Start ";			
			params.put("${item.name}Start", ${item.name}Start);
		}
		</#if>
		</#list>		
		return count("select count(*) " + hql, params);
		
	}
	

	@Override
	public void delete(String ids) {
		// TODO Auto-generated method stub
		if(ids!=null&&!ids.equals("")){
			for (String s : ids.split(",")) {
				${table.classname} ${table.lowername}=get(${table.classname}.class, s);
				delete(${table.lowername});
			}
		}			
	}

	@Override
	public ${table.classname} get(String id) {
		// TODO Auto-generated method stub
		return get(${table.classname}.class, id);
	}

	
}

	