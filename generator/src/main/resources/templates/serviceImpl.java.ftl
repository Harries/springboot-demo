package ${package.ServiceImpl};

import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * Code generate by mybatis-plus template
 */
@Service
public class ${table.serviceImplName} implements ${table.serviceName} {
	
	@Autowired
	private ${table.mapperName} ${table.entityPath}Mapper;

	/**
	*  query  table ${table.name} all records
	*/
	@Override
	public List<${entity}> findAll${entity}() { return ${table.entityPath}Mapper.findAll${entity}();}

<#list table.fields as field>
<#if field.keyFlag>
	/**
	*  query table ${table.name} records   by ${field.propertyName}
	*  @param ${field.propertyName}
	*/
	@Override
	public ${entity} find${entity}By${field.propertyName}(@Param("${field.propertyName}") ${field.propertyType} ${field.propertyName}) { return ${table.entityPath}Mapper.find${entity}By${field.propertyName}(${field.propertyName});}
</#if>
</#list>

	/**
	*  query table ${table.name} record by Condition
	*  @param ${table.entityPath}
	*/
	@Override
	public List<${entity}> find${entity}ByCondition(${entity} ${table.entityPath}) { return ${table.entityPath}Mapper.find${entity}ByCondition(${table.entityPath});}

<#list table.fields as field>
<#if field.keyFlag>
	/**
	*  delete table ${table.name} records by ${field.propertyName}
	*  @param ${field.propertyName}
	*/
	@Override
	public Integer delete${entity}By${field.propertyName}(@Param("${field.propertyName}") ${field.propertyType} ${field.propertyName}) { return ${table.entityPath}Mapper.delete${entity}By${field.propertyName}(${field.propertyName});}
</#if>
</#list>

<#list table.fields as field>
<#if field.keyFlag>
	/**
	*  update table ${table.name} records by ${field.propertyName}
	*  @param ${table.entityPath}
	*/
	@Override
	public Integer update${entity}By${field.propertyName}(${entity} ${table.entityPath}) { return ${table.entityPath}Mapper.update${entity}By${field.propertyName}(${table.entityPath});}
</#if>
</#list>

<#list table.fields as field>
<#if field.keyFlag>
	/**
	*  add table ${table.name} record
	*  @param ${table.entityPath}
	*/
	@Override
	public Integer add${entity}(${entity} ${table.entityPath}) { return ${table.entityPath}Mapper.add${entity}(${table.entityPath});}
</#if>
</#list>

}
