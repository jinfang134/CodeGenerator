package ${table.packagename}.entity;
import java.util.Date;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
/**
* ${table.comment!}
*/
public class ${table.classname } implements java.io.Serializable { 
<#list table.columnlist as item >
	<#if item.name="id">
	private ${item.type } id;	
	<#else>
	/** ${item.comment! }*/
	private ${item.type }  ${item.name };
	</#if>	
</#list> 

	/** 默认构造器 */
	public ${table.classname }(){
		
	}
	/** 全构造器 */
	public ${table.classname}(<#list table.columnlist as item>
		${item.type } ${item.name }<#if item_has_next>,</#if></#list>) {
		super();
	<#list table.columnlist as item>
		this.${item.name }=${item.name};
	</#list>
	}	
	
<#list table.columnlist as item >
	/** 获得 ${item.comment!} */
<#if item.type=='Date'>
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")  
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")  
</#if>
	public ${item.type }  get${item.bigname }() { 
		return ${item.name }; 
	}
	/** 设置 ${item.comment!} */
	public void set${item.bigname }(${item.type} ${item.name }){
		this.${item.name }=${item.name };
	}	
	</#list> 
	
	@Override
	public String toString() {
		return "${table.comment!} ${table.classname } ["
			<#list table.columnlist as item>	+" ${item.name}=" + ${item.name}<#if (item_index>0&&item_index%4=0)>${'\n'}</#if></#list>
		+ "]\n";
	}	
	
}
	