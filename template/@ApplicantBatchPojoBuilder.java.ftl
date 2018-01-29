package com.worksap.company.hue.controller.hr.webapi.recruiting.ifx.ie.util;

import java.time.LocalDate;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.worksap.company.hue.com.webapi.spec.inputengine.vo.fw.IfxIELineInfo;
import com.worksap.company.hue.hr.biz.recruiting.pojo.applicant.ApplicationPojo;
import com.worksap.company.hue.hr.biz.recruiting.pojo.ifx.ApplicantBatchPojo;
import com.worksap.company.hue.hr.bizcore.recruiting.utils.BigDecimalUtils;
import com.worksap.company.hue.hr.bizcore.recruiting.utils.LocalDateUtils;

public class ApplicantBatchPojoBuilder {
    private static final Logger log = LoggerFactory.getLogger(ApplicantBatchPojoBuilder.class);

    /**
     * convert lineinfo to a applicationPojo.
     * 
     * @param ifxLineinfo
     * @return
     */
    public static ApplicationPojo convertToApplicationPojo(IfxIELineInfo ifxLineinfo) {
        return fromApplicantBatchPojo(convertIfxToBatchPojo(ifxLineinfo));
    }

    public static ApplicationPojo fromApplicantBatchPojo(ApplicantBatchPojo pojo) {
        // TODO finish applicationPojo logic
        return ApplicationPojo.builder()
<#list ApplicantInfo.columnlist as item >
<#if item.columntype!="blob">
            .${item.name}(pojo.get${item.bigname}())
</#if>
</#list> 

<#list tables as table>
<#if table.name!="applicant_info">
            .${table.lowername}s(pojo.get${table.classname}s())     
</#if>
</#list>
            .build();           
    }

      public static ApplicantBatchPojo convertIfxToBatchPojo(IfxIELineInfo ifxLineinfo) {
        ApplicantBatchPojo pojo = new ApplicantBatchPojo();
<#list ApplicantInfo.columnlist as item >
<#if item.columntype!="blob">            
            pojo.set${item.bigname}(get${item.type?cap_first}(ifxLineinfo, "${item.name}"));
</#if>
</#list> 

<#list tables as table>
<#if table.name!="applicant_info">

    <#list table.columnlist as item>
            pojo.set${table.classname}1${item.bigname}(get${item.type?cap_first}(ifxLineinfo, "${table.name}1${item.bigname}")); 
    </#list>
    
    <#list table.columnlist as item>
            pojo.set${table.classname}2${item.bigname}(get${item.type?cap_first}(ifxLineinfo, "${table.name}2${item.name}")); 
    </#list>    
</#if>
</#list>
        return pojo;

    }

   private static Integer getInteger(IfxIELineInfo ifxLineinfo, String string) {
        return ifxLineinfo.getColumnSet().getInt(string).orElse(0);
    }

    private static boolean getBoolean(IfxIELineInfo ifxLineinfo, String string) {
        return ifxLineinfo.getColumnSet().getBoolean(string).orElse(false);
    }

    private static BigDecimal getBigDecimal(IfxIELineInfo ifxLineinfo, String string) {
        return ifxLineinfo.getColumnSet().getBigDecimal(string).orElse(new BigDecimal(0));
    }

    private static Object getLocalDateTime(IfxIELineInfo ifxLineinfo, String string) {
        return ifxLineinfo.getColumnSet().getLocalDate(string).orElse(null);
    }

    private static String getString(IfxIELineInfo ifxLineinfo, String string) {
        return ifxLineinfo.getColumnSet().getString(string).orElse(null);
    }

    private static LocalDate getLocalDate(IfxIELineInfo ifxLineinfo, String field) {
        return ifxLineinfo.getColumnSet().getLocalDate(field).orElse(null);
    }
}
