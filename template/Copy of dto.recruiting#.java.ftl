package ${table.packagename}.dto.recruiting;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

import com.worksap.company.framework.serializer.HueSerializable;
import com.worksap.company.framework.serializer.wrapper.InputSource;
import com.worksap.company.framework.serializer.wrapper.OutputSource;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ${table.classname }  implements HueSerializable { 
	private static final int CURR_VERSION = 0;
<#list table.columnlist as item >	
	<#if item.columntype="blob">
	private ${item.comment} ${item.name};	
	<#else>
	private ${item.type }  ${item.name };	
	</#if>
</#list> 

 	@Override
    public void write(OutputSource outputSource) {
    	outputSource.writeInt(CURR_VERSION);
<#list table.columnlist as item >	
	<#if item.columntype="blob">
		outputSource.writeGenericObject(${item.name});
	<#elseif item.type="LocalDate"||item.type="LocalDateTime"||item.type="BigDecimal">
		outputSource.writeConcreteObject(${item.name}, ${item.type}.class);
	<#else>
    	outputSource.write${item.type}(${item.name});
   	</#if>
</#list> 
	}

	 @Override
    public void read(InputSource inputSource) {
        int version = inputSource.readInt();
        if (version == CURR_VERSION) {
<#list table.columnlist as item >	
	<#if item.columntype="blob">			
			${item.name} = (${item.comment})inputSource.readGenericObject();
	<#elseif item.type="LocalDate"||item.type="LocalDateTime"||item.type="BigDecimal">			
			${item.name} = inputSource.readConcreteObject(${item.type}.class);
	<#else>
    		${item.name} = inputSource.read${item.type?cap_first}();
   	</#if>
</#list>         	
        }
    }


	
}
	