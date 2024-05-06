package ${package.Mapper};

import ${package.Entity}.${entity};
import java.util.List;
import org.apache.ibatis.annotations.Param;
/**
 * Code generate by mybatis-plus template
 */
public interface ${table.mapperName}{
	
	 /**
	  *   query  table ${table.name} all records
	  */
	 List<${entity}> findAll${entity}();
	  
	<#list table.fields as field>
	<#if field.keyFlag>
	 /**
	  *  query table ${table.name} records   by ${field.propertyName}
	  *  @param ${field.propertyName}
	  */
	 ${entity} find${entity}By${field.propertyName}(@Param("${field.propertyName}") ${field.propertyType} ${field.propertyName});
	</#if>
	</#list>

	/**
	*  query table ${table.name} record by Condition
	*  @param ${table.entityPath}
	*/
	List<${entity}> find${entity}ByCondition(${entity} ${table.entityPath});

	<#list table.fields as field>
	<#if field.keyFlag>
	 /**
	  *  delete table ${table.name} records by ${field.propertyName}
	  *  @param ${field.propertyName}
	  */
	 Integer delete${entity}By${field.propertyName}(@Param("${field.propertyName}") ${field.propertyType} ${field.propertyName});
	</#if>
	</#list>
	
	<#list table.fields as field>
	<#if field.keyFlag>
	 /**
	  *  update table ${table.name} records by ${field.propertyName}
	  *  @param ${table.entityPath}
	  */
	 Integer update${entity}By${field.propertyName}(${entity} ${table.entityPath});
	</#if>
	</#list>
	
	<#list table.fields as field>
	<#if field.keyFlag>
	 /**
	  *  add table ${table.name} record
	  *  @param ${table.entityPath}
	  */
	 Integer add${entity}(${entity} ${table.entityPath});
	</#if>
	</#list>
	
}

