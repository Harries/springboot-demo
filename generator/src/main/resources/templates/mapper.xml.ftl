<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${package.Mapper}.${table.mapperName}">

<!-- Common Setting Columns-->
<#if baseColumnList>
    <!-- Common Columns-->
    <sql id="Base_Column_List">
        <#list table.commonFields as field>
            ${field.name},
        </#list>
        ${table.fieldNames}
    </sql>

    <!--Common Condition-->
    <sql id="${entity}ByCondition">
    <#list table.fields as field>
        <#if field.propertyType == "LocalDateTime" || field.propertyType == "LocalDate"><#--generate  Columns-->
            <if test="${field.propertyName}!=null">
                AND ${field.name} = ${r"#{"}${field.propertyName}${r"}"}
            </if>
        </#if>
        <#if field.propertyType != "LocalDateTime" && field.propertyType != "LocalDate"><#--generate  Columns -->
            <if test="${field.propertyName}!=null and ${field.propertyName}!=''">
                AND ${field.name} = ${r"#{"}${field.propertyName}${r"}"}
            </if>
        </#if>
    </#list>
    </sql>

    <!-- Common Setting  Columns-->
    <sql id="${entity}SetColumns">
    <#list table.fields as field>
        <#if !field.keyFlag><#--generate  Columns-->
        <#if field.propertyType == "LocalDateTime" || field.propertyType == "LocalDate">
            <if test="${field.propertyName}!=null">
                ${field.name} = ${r"#{"}${field.propertyName}${r"}"},
            </if>
        </#if>
        <#if field.propertyType != "LocalDateTime" && field.propertyType != "LocalDate">
            <if test="${field.propertyName}!=null and ${field.propertyName}!=''">
                ${field.name} = ${r"#{"}${field.propertyName}${r"}"},
            </if>
        </#if>
        </#if>
    </#list>
    </sql>
    </#if>

    <#if baseResultMap>
    <!-- resultMap -->
    <resultMap id="${entity}Map" type="${package.Entity}.${entity}">
    <#list table.fields as field>
        <#if field.keyFlag><#--primary key First-->
            <id column="${field.name}" property="${field.propertyName}"/>
        </#if>
    </#list>
    <#list table.commonFields as field><#--generate  Columns  -->
        <result column="${field.name}" property="${field.propertyName}"/>
    </#list>
    <#list table.fields as field>
        <#if !field.keyFlag><#--generate  Columns  -->
            <result column="${field.name}" property="${field.propertyName}"/>
        </#if>
    </#list>
    </resultMap>
    </#if>

    <!-- query  table ${table.name} all records -->
    <select id="findAll${entity}" resultMap="${entity}Map">
        SELECT
        <include refid="Base_Column_List"/>
        FROM ${table.name}
    </select>

<#list table.fields as field>
<#if field.keyFlag>
    <!-- query table ${table.name} records   by ${field.propertyName} -->
    <select id="find${entity}By${field.propertyName}" resultMap="${entity}Map">
        SELECT
        <include refid="Base_Column_List"/>
        FROM ${table.name}
        WHERE ${field.name}=${r"#{"}${field.propertyName}${r"}"}
    </select>
</#if>
</#list>

    <!-- query table ${table.name} record by Condition -->
    <select id="find${entity}ByCondition" resultMap="${entity}Map">
        SELECT
        <include refid="Base_Column_List"/>
        FROM ${table.name}
        WHERE 1=1
        <include refid="${entity}ByCondition" />
    </select>

<#list table.fields as field>
<#if field.keyFlag>
    <!--delete table ${table.name} records by ${field.propertyName} -->
    <delete id="delete${entity}By${field.propertyName}">
        DELETE FROM
        ${table.name}
        WHERE ${field.name}=${r"#{"}${field.propertyName}${r"}"}
    </delete>
</#if>
</#list>

<#list table.fields as field>
<#if field.keyFlag>
    <!-- update table ${table.name} records by ${field.propertyName} -->
    <update id="update${entity}By${field.propertyName}" parameterType="${package.Entity}.${entity}">
        UPDATE ${table.name}
        <set>
            <include refid="${entity}SetColumns"/>
        </set>
        WHERE
        <#list table.fields as field><#if field.keyFlag>${field.name}=${r"#{"}${field.propertyName}${r"}"}</#if></#list>
    </update>
</#if>
</#list>

<#list table.fields as field>
<#if field.keyFlag>
    <!-- add table ${table.name} record -->
    <insert id="add${entity}">
        INSERT INTO ${table.name} (
        <#list table.fields as field>
            <#if field_index gt 0>,</#if>${field.name}
        </#list>
        ) VALUES (
        <#list table.fields as field>
            <#if field_index gt 0>,</#if>${r"#{"}${field.propertyName}${r"}"}
        </#list>
        )
    </insert>
</#if>
</#list>
</mapper>
