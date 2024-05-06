package ${package.Controller};
import java.util.List;
import org.springframework.web.bind.annotation.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import ${package.Service}.${table.serviceName};
import com.et.generator.entity.${entity};
/**
 * Code generate by mybatis-plus template
 */
@RestController
@RequestMapping("/${table.entityPath}")
public class ${table.controllerName} {

	@Autowired
	private ${table.serviceName} ${table.entityPath}Service;

   /**
     *  query  table ${table.name} all records
     */
    @GetMapping("/findAll${entity}")
    public List<${entity}> findAll${entity}(){
        return ${table.entityPath}Service.findAll${entity}();
    }

    /**
     *  query table ${table.name} record by Condition
     *  @param  ${table.entityPath}
     */
    @GetMapping("/find${entity}ByCondition")
    public  List<${entity}> find${entity}ByCondition(@RequestBody  ${entity}  ${table.entityPath}){
        return  ${table.entityPath}Service.find${entity}ByCondition( ${table.entityPath});
    }
<#list table.fields as field>
<#if field.keyFlag>
    /**
     *  query table ${table.name} records   by ${field.propertyName}
     *  @param  ${field.propertyName}
     */
    @GetMapping("/find${entity}By${field.propertyName}")
    public ${entity} find${entity}By${field.propertyName}(@Param("${field.propertyName}") String ${field.propertyName}){
       return ${table.entityPath}Service.find${entity}By${field.propertyName}( ${field.propertyName});
    }
</#if>
</#list>
<#list table.fields as field>
<#if field.keyFlag>
    /**
     *  delete table ${table.name} records by ${field.propertyName}
     *  @param  ${field.propertyName}
     */
    @DeleteMapping("/delete${entity}By${field.propertyName}")
    public Integer delete${entity}By${field.propertyName}(@Param("${field.propertyName}") ${field.propertyType} ${field.propertyName}){
       return  ${table.entityPath}Service.delete${entity}By${field.propertyName}( ${field.propertyName});
    }
</#if>
</#list>
<#list table.fields as field>
<#if field.keyFlag>
    /**
     *  update table ${table.name} records by ${field.propertyName}
     *  @param ${table.entityPath}
     */
    @PutMapping("/update${entity}By${field.propertyName}")
    public Integer update${entity}By${field.propertyName}(@RequestBody ${entity} ${table.entityPath}){
        return ${table.entityPath}Service.update${entity}ByuserId(${table.entityPath});
    }
</#if>
</#list>
    /**
     *  add table ${table.name} record
     *  @param ${table.entityPath}
     */
    @PostMapping("/add${entity}")
    public Integer add${entity}(@RequestBody ${entity} ${table.entityPath}){
        return ${table.entityPath}Service.add${entity}(${table.entityPath});
    }
	

}
