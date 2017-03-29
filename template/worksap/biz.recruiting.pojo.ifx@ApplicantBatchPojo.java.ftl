package com.worksap.company.hue.hr.biz.recruiting.pojo.ifx;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

import org.apache.commons.lang3.StringUtils;

import com.worksap.company.dto.annotation.Key;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class ApplicantBatchPojo {
	
<#list ApplicantInfo.columnlist as item >	
	<#if item.columntype!="blob">
	private ${item.type} ${item.name};		
	</#if>
</#list> 

<#list tables as table>
<#if table.name!="applicant_info">

	<#list table.columnlist as col>
	private ${col.type} ${table.lowername}1${col.bigname};
	</#list>
	
	<#list table.columnlist as col>
	private ${col.type} ${table.lowername}2${col.bigname};
	</#list>	
</#if>
</#list>

<#list tables as table>
<#if table.name!="applicant_info">

 public List<${table.classname}Pojo> get${table.classname}s() {
        List<${table.classname}Pojo> list = new ArrayList<>();
        if (StringUtils.isNotBlank(${table.lowername}1${table.columnlist[0].bigname}) {
            list.add(${table.classname}Pojo.builder()
            <#list table.columnlist as col>
                    .${col.name}(${table.lowername}1${col.bigname})
            </#list>
                    .build()
                    );
        }
        if (StringUtils.isNotBlank(${table.lowername}1${table.columnlist[0].bigname}) {
             list.add(${table.classname}Pojo.builder()
            <#list table.columnlist as col>
                    .${col.name}(${table.lowername}2${col.bigname})
            </#list>
                    .build()
                    );
        }
        return list;
    }

</#if>
</#list>


}