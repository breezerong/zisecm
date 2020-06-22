<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${package.Mapper}.${table.mapperName}">

<#if enableCache>
    <!-- 开启二级缓存 -->
    <cache type="org.mybatis.caches.ehcache.LoggingEhcache"/>

</#if>
<#if baseResultMap>
    <!-- 通用查询映射结果 -->
    <resultMap id="BaseResultMap" type="${package.Entity}.${entity}">
<#list table.fields as field>
<#if field.keyFlag><#--生成主键排在第一位-->
        <id column="${field.name}" property="${field.propertyName}" jdbcType="${getJdbcType(field.type)}" />
</#if>
</#list>
<#list table.commonFields as field><#--生成公共字段 -->
        <result column="${field.name}" property="${field.propertyName}" jdbcType="${getJdbcType(field.type)}"/>
</#list>
<#list table.fields as field>
<#if !field.keyFlag><#--生成普通字段 -->
        <result column="${field.name}" property="${field.propertyName}" jdbcType="${getJdbcType(field.type)}" />
</#if>
</#list>
    </resultMap>

</#if>
<#if baseColumnList>
    <!-- 通用查询结果列 -->
    <sql id="Base_Column_List">
<#list table.commonFields as field>
        ${field.columnName},
</#list>
        ${table.fieldNames}
    </sql>

</#if>

  <select id="selectByPrimaryKey" parameterType="java.lang.String" resultMap="BaseResultMap">
    select 
    <include refid="Base_Column_List" />
    from ${table.name}
    where ID = <#noparse>#{</#noparse>id<#noparse>,</#noparse>jdbcType=VARCHAR}
  </select>
  
  <select id="selectAll" parameterType="java.lang.String" resultMap="BaseResultMap">
  	select
    <include refid="Base_Column_List" />
    from ${table.name} order by ID
  </select>
  
  <select id="executeSQL" parameterType="java.lang.String" resultType="java.util.Map"> 
	<#noparse>$</#noparse>{sqlStr}
  </select>
  
   <delete id="deleteByPrimaryKey" parameterType="java.lang.String">
    delete from ${table.name}
    where ID = <#noparse>#{</#noparse>id<#noparse>,</#noparse>jdbcType=VARCHAR}
  </delete>
  
  <insert id="insert" parameterType="${package.Entity}.${entity}">
    insert into ${table.name} (
    	<#list table.fields as field>
		    ${field.name}<#if field_has_next>,</#if>
		</#list>
      )
    values (
    	<#list table.fields as field>
    		 <#noparse>#{</#noparse>${field.propertyName}}<#if field_has_next>,</#if>
    	</#list>
    	)
  </insert>
  
  
  <insert id="insertSelective" parameterType="${package.Entity}.${entity}">
    insert into ${table.name}
    <trim prefix="(" suffix=")" suffixOverrides=",">
    	<#list table.fields as field>
    	<if test="${field.propertyName} != null">
    		 ${field.name},
    	</if>
    	</#list>      
    </trim>
    <trim prefix="values (" suffix=")" suffixOverrides=",">
    <#list table.fields as field>
    	<if test="${field.propertyName} != null">
    		<#noparse>#{</#noparse>${field.propertyName},jdbcType=${getJdbcType(field.type)}}, 		 
    	</if>
    </#list>      
    </trim>
  </insert>
  
  
  <update id="updateByPrimaryKeySelective" parameterType="${package.Entity}.${entity}">
    update ${table.name}
    <set>
      <#list table.fields as field>
    	<if test="${field.propertyName} != null">
    		${field.name}=<#noparse>#{</#noparse>${field.propertyName},jdbcType=${getJdbcType(field.type)}}, 		 
    	</if>
    </#list>
    </set>
    where ID = <#noparse>#{</#noparse>id,jdbcType=VARCHAR}
  </update>
  <update id="updateByPrimaryKey" parameterType="${package.Entity}.${entity}">
    update ecm_component
    set     
    <#list table.fields as field>
   		${field.name}=<#noparse>#{</#noparse>${field.propertyName}}<#if field_has_next>,</#if>
    </#list>    
    where ID = <#noparse>#{</#noparse>id,jdbcType=VARCHAR}
  </update>
  
</mapper>

<#function getJdbcType type>
	<#if type?last_index_of('(') gt 0 >
		<#assign typev = type?substring(0,type?last_index_of('('))?upper_case >
		<#if typev == 'INT'>
			<#assign typev="INTEGER" >
		</#if>
		<#return typev >
	<#else>
	<#assign typev = type?upper_case>
		<#if typev == 'DATETIME'>
			<#assign typev="TIMESTAMP" >
		</#if>
		<#if typev == 'INT'>
			<#assign typev="INTEGER" >
		</#if>
		<#if typev == 'DATETIME2'>
			<#assign typev="TIMESTAMP" >
		</#if>
		
		<#return typev >
	</#if>
</#function>
