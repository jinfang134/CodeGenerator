package com.worksap.company.hue.controller.hr.webapi.recruiting.ifx.ie.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.worksap.company.hue.com.webapi.spec.inputengine.vo.meta.column.InputEngineColumnMst;
import com.worksap.company.hue.com.webapi.spec.inputengine.vo.meta.column.description.InputEngineColumnDescription;
import com.worksap.company.hue.com.webapi.spec.inputengine.vo.meta.column.format.InputEngineLength;
import com.worksap.company.hue.com.webapi.spec.inputengine.vo.meta.column.format.InputEngineLocalDate;
import com.worksap.company.hue.com.webapi.spec.inputengine.vo.meta.column.format.InputEngineNumber;
import com.worksap.company.hue.com.webapi.spec.inputengine.vo.meta.column.format.InputEngineZonedLocalDateTime;
import com.worksap.company.hue.com.webapi.spec.inputengine.vo.meta.validate.InputEngineValidateMst.IeTargetMode;

public class IfxColumnListProvider {
    private final static List<IeTargetMode> TARGET_LIST = Arrays.asList(
            IeTargetMode.INSERT,
            IeTargetMode.UPDATE,
            IeTargetMode.SYNC);
    private static final InputEngineColumnDescription COL_DES_NULL = null;
    private static final boolean IS_KEY = true;
    private static final boolean NOT_KEY = false;

    private static final boolean NOT_NULL = false;
    private static Set<String> requiredFields = new HashSet<>();
    static {
        requiredFields.addAll(Arrays.asList(
                "name", "address", "email", "mobile"
                ));
    }

    public List<InputEngineColumnMst> getColumnList() {
        List<InputEngineColumnMst> columnList = new ArrayList<InputEngineColumnMst>();
        
<#list ApplicantInfo.columnlist as item >	
	<#if item.columntype!="blob">	
		columnList.add(get${item.type}ColumnMst("${item.name}"));
	</#if>
</#list> 

<#list tables as table>
<#if table.name!="applicant_info">

	<#list table.columnlist as col>
	columnList.add(get${col.type}ColumnMst("${table.lowername}1${col.bigname}"));
	</#list>
	
	<#list table.columnlist as col>
	columnList.add(get${col.type}ColumnMst("${table.lowername}1${col.bigname}"));
	</#list>	
</#if>
</#list>

        return columnList;
    }

   private InputEngineColumnMst getStringColumnMst(String field) {
        String textId = "applicantEntity.applicantInfo." + field;
        if (requiredFields.contains(field)) {
            return InputEngineColumnMst.stringFormat(field,
                    textId, COL_DES_NULL, true, true, false, TARGET_LIST,
                    InputEngineLength.notCheckLength(), InputEngineLength.notCheckLength(), 0);
        } else {
            return InputEngineColumnMst.stringFormat(field,
                    textId, COL_DES_NULL, false, false, false, TARGET_LIST,
                    InputEngineLength.notCheckLength(), InputEngineLength.notCheckLength(), 0);
        }
    }

    private InputEngineColumnMst getIntegerColumnMst(String field) {
        String textId = "applicantEntity.applicantInfo." + field;

        // integer type column (flag/null is not possible/INSERT/support UPDATE mode/common value check[0-5])
        InputEngineColumnMst columnMst = InputEngineColumnMst.intFormat(field, textId, COL_DES_NULL, false,
                false, false, Arrays.asList(IeTargetMode.INSERT, IeTargetMode.UPDATE, IeTargetMode.SYNC),
                InputEngineNumber.of(0),
                InputEngineNumber.of(100), 0);
        return columnMst;
    }

    private InputEngineColumnMst getBigDecimalColumnMst(String field) {
        String textId = "applicantEntity.applicantInfo." + field;
        // decimal type column (score/INSERT/support UPDATE mode/no common value check/no common decimal length check)
        InputEngineColumnMst score = InputEngineColumnMst.decimalFormat(field, textId, COL_DES_NULL, false,
                false, false, TARGET_LIST,
                InputEngineNumber.notCheckNumber(),
                InputEngineNumber.notCheckNumber(), InputEngineLength.notCheckLength(),
                InputEngineLength.notCheckLength(), 0);
        return score;
    }

    private InputEngineColumnMst getLocalDateColumnMst(String field) {
        String textId = "applicantEntity.applicantInfo." + field;
        // date type column (update date/INSERT/support UPDATE mode/common date range check[no lower limit-2000/01/01])
        InputEngineLocalDate startDate = InputEngineLocalDate.notCheckDate();
        InputEngineLocalDate endDate = InputEngineLocalDate.notCheckDate();// InputEngineLocalDate.of(LocalDate.of(2000,
                                                                           // 01, 01))
        InputEngineColumnMst updDate = InputEngineColumnMst.dateFormat(field, textId, COL_DES_NULL,
                false,
                false, false, TARGET_LIST,
                InputEngineLocalDate.notCheckDate(), endDate, 0);
        return updDate;
    }

    private InputEngineColumnMst getLocalDateTimeColumnMst(String field) {
        String textId = "applicantEntity.applicantInfo." + field;
        // datetime type column (update date/INSERT/support UPDATE mode/
        // common date range check[no lower limit-2000/01/01 0:30:0 -0200])
        InputEngineZonedLocalDateTime endDateTime = InputEngineZonedLocalDateTime.notCheckDateTime();
        InputEngineColumnMst updDatetime = InputEngineColumnMst.dateTimeFormat(field, textId, COL_DES_NULL,
                false, false, false, TARGET_LIST,
                InputEngineZonedLocalDateTime.notCheckDateTime(), endDateTime, 0);
        return updDatetime;
    }

    private InputEngineColumnMst getBooleanColumnMst(String field) {
        String textId = "applicantEntity.applicantInfo." + field;
        // boolean type column
        InputEngineColumnMst columnMst = InputEngineColumnMst.boolFormat(field, textId, COL_DES_NULL, false, false,
                false,
                TARGET_LIST, 0);
        return columnMst;
    }

}
