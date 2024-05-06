package ${package.Entity};

import java.io.Serializable;
import java.util.Date;
<#list table.importPackages as pkg>
<#if pkg == "java.util.Date">
import ${pkg};
</#if>
</#list>

/**
 * Code generate by mybatis-plus template
 * ${table.name} : ${table.comment!}
 */
@TableName("${table.name}")
@ApiModel("${table.comment!}")
public class ${entity}  implements Serializable {

	private static final long serialVersionUID = 1L;
<#-- ---------- private  ---------->
<#list table.fields as field>
    <#if field.keyFlag>
        <#assign keyPropertyName="${field.propertyName}"/>
    </#if>

    <#if field.keyFlag>
    <#-- primary key -->
    /**
     *  primary key : ${field.name},  ${field.comment!}
     */
    <#-- plain field -->
    <#elseif !field.keyFlag>
    /**
     * ${field.name},  ${field.comment!}
     */
    </#if>
<#-- version -->
    <#if (versionFieldName!"") == field.name>
    @Version
    </#if>
<#-- logicDelete -->
    <#if (logicDeleteFieldName!"") == field.name>
    @TableLogic
    </#if>
    <#if field.propertyType == "LocalDateTime">
        @TableField("${field.name}")
        @ApiModelProperty("${field.comment!}")
        private Date ${field.propertyName};
    </#if>
    <#if field.propertyType != "LocalDateTime">
        @TableField("${field.name}")
        @ApiModelProperty("${field.comment!}")
        private ${field.propertyType} ${field.propertyName};
    </#if>
</#list>

<#------------  construct   ----------- -->
	public ${entity}(<#list table.fields as field><#if field.propertyType == "LocalDateTime">Date ${field.propertyName}</#if><#if field.propertyType != "LocalDateTime">${field.propertyType} ${field.propertyName}</#if><#sep>,</#list>){
		<#list table.fields as field>
			this.${field.propertyName} = ${field.propertyName};
		</#list>
	}
	
	public ${entity}(){
	}

<#------------  getter.setter ---------->
<#if !entityLombokModel>
    <#list table.fields as field>
        <#if field.propertyType == "boolean">
            <#assign getprefix="is"/>
        <#else>
            <#assign getprefix="get"/>
        </#if>
    public <#if field.propertyType == "LocalDateTime">Date</#if><#if field.propertyType != "LocalDateTime">${field.propertyType}</#if> ${getprefix}${field.capitalName}() {
        return ${field.propertyName};
    }
        <#if entityBuilderModel>
    public ${entity} set${field.capitalName}(${field.propertyType} ${field.propertyName}) {
        <#else>
    public void set${field.capitalName}(<#if field.propertyType == "LocalDateTime">Date</#if><#if field.propertyType != "LocalDateTime">${field.propertyType}</#if> ${field.propertyName}) {
        </#if>
        this.${field.propertyName} = ${field.propertyName};
        <#if entityBuilderModel>
        return this;
        </#if>
    }
    </#list>
</#if>

<#-------------  @Override toString()  ----------------->
<#if !entityLombokModel>
    @Override
    public String toString() {
        return "${entity}{" +
    <#list table.fields as field>
        <#if field_index==0>
        "${field.propertyName}=" + ${field.propertyName} +
        <#else>
        ", ${field.propertyName}=" + ${field.propertyName} +
        </#if>
    </#list>
        "}";
    }
</#if>
}
