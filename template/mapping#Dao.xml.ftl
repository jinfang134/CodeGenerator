<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!-- 为这个mapper指定一个唯一的namespace，namespace的值习惯上设置成包名+sql映射文件名，这样就能够保证namespace的值是唯一的 
	例如namespace="me.gacl.mapping.${table.classname }Mapper"就是me.gacl.mapping(包名)+${table.classname }Mapper(${table.classname }Mapper.xml文件去除后缀) -->
<mapper namespace="${table.packagename}.dao.${table.classname }Dao">

	<resultMap type="${table.packagename}.entity.${table.classname }" id="BaseResultMap">
		
<#list table.columnlist as item >
	<#if item.name="id">
		<!-- 用id属性来映射主键字段 -->
		<id property="id" column="id" />
	<#else>
		<result column="${item.columnname}"  property="${item.name}" />
	</#if>	
</#list> 
	</resultMap>

	<!-- 全部字段 -->
	<sql id="Full_Column_List">
<#list table.columnlist as item >	
		${item.columnname}<#if item!=table.columnlist?last>,</#if>
</#list>
	</sql>
	
	<!-- 字段 -->
	<sql id="Column_List">
<#list table.columnlist as item >	
	<#if item.name!="id">		
		${item.columnname}<#if item!=table.columnlist?last>,</#if>
	</#if>
</#list>
	</sql>
	
	<select id="count" resultType="int">
		select count(*) from ${table.name}
	</select>

	
	<!-- 根据id查询得到一个${table.comment }对象 -->
	<select id="get${table.classname }ById" parameterType="long" resultType="${table.packagename}.entity.${table.classname }">
		select  <include refid="Full_Column_List" />
		from ${table.name} where id=${r"#{id}"}
	</select>

	<!-- 创建${table.comment }(Create) -->
	<insert id="insert${table.classname }" parameterType="${table.packagename}.entity.${table.classname }">
		insert into
		${table.name}( <include refid="Column_List" />)
		values(
<#list table.columnlist as item >
	<#if item.name!="id">		
		${r"#{"}${item.name}}<#if item!=table.columnlist?last>,</#if>		
	</#if>
</#list> 
		)
	</insert>

	<!-- 删除${table.comment }(Remove) -->
	<delete id="delete${table.classname }" parameterType="long">
		delete from ${table.name} where
		id=${r"#{id}"}
	</delete>

	<!-- 修改${table.comment }(Update) -->
	<update id="update${table.classname }" parameterType="${table.packagename}.entity.${table.classname }">
		update ${table.name} set
<#list table.columnlist as item >
	<#if item.name!="id">
		${item.columnname}=${r"#{"} ${item.name} }<#if item!=table.columnlist?last>,</#if>
	</#if>
</#list>
		where id=${r"#{id}"}
	</update>

	<!-- 查询全部${table.comment } -->
	<select id="queryAll" resultType="${table.packagename}.entity.${table.classname }">
		select 
		 <include refid="Full_Column_List" /> 
		 from ${table.name}		  
		 limit ${r"#{"} offset},${r"#{"} limit}
	</select>
</mapper>