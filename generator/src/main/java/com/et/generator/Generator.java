package com.et.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;

public class Generator {
	
	public static void main(String[] args) {
		// System.getProperty("user.dir") == get current project dir
		String projectDir ="D:\\IdeaProjects\\ETFramework\\generator";
		String outputDir = projectDir+"\\src\\main\\java";
		//String outputDir = System.getProperty("user.dir") + "/src/main/java";
//		String outputDir = "C://Users/VULCAN/Desktop/new";
		// table name, Pay attention to capitalization
		String[] tableNames = new String[]{"user_info"};
		// database url
		String url = "jdbc:mysql://localhost:3306/demo?useUnicode=true&characterEncoding=utf8";
		String userName = "root";
		String password = "123456";
		// parentPackage
		String parentPackage = "com.et.generator";
		// need to remove prefix from tablename
		String prefixTable = "";
		generate(projectDir,outputDir, tableNames, url, userName, password, parentPackage, prefixTable);
	}
	
	/**
	 * @param outputDir
	 * @param tableNames
	 * @param url
	 * @param userName
	 * @param password
	 * @param parentPackage
	 * @param prefixTable
	 */
	public static void generate(String projectDir,String outputDir, String[] tableNames, String url, String userName,
			String password, String parentPackage, String prefixTable) {
		// ===============  Global setting ==================
		GlobalConfig gc = new GlobalConfig();
		gc.setOutputDir(outputDir)
			.setActiveRecord(true)								//  enable AR,
	        .setAuthor("Harries") 							// set Author name
	        .setFileOverride(true) 								// enable FileOverride？
	        .setIdType(IdType.AUTO)								//primary strategy
	        .setBaseResultMap(true) 							// SQL mappingg
	        .setBaseColumnList(true)							// SQL BaseColumn
	        .setServiceName("%sService")						// service name
	        .setOpen(false);
		
		// =================  database setting   ===============
		DataSourceConfig dsc = new DataSourceConfig();
			dsc.setDbType(DbType.MYSQL)
			 .setDriverName("com.mysql.cj.jdbc.Driver");
		dsc.setUrl(url)
           .setUsername(userName)
           .setPassword(password);
		
		// =================  package setting  ===================
		 PackageConfig pc = new PackageConfig();
         pc.setParent(parentPackage)							// parentPackage path
//           .setModuleName("base")								// ModuleName path
           .setMapper("mapper")
           .setEntity("entity")
//           .setEntity("entity")
           .setService("service")
           //.setServiceImpl("service.impl"); 					// auto generate impl， no need to set
           .setController("controller");
         
       // ==================  custom setting  =================
         InjectionConfig cfg = new InjectionConfig() {
             @Override
             public void initMap() {
                 // to do nothing
             }
         };
         List<FileOutConfig> focList = new ArrayList<>();
         // adjust xml generate directory
         focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
             @Override
             public String outputFile(TableInfo tableInfo) {
                 // custom file name
                 return projectDir + "/src/main/resources/mybatis/"
                         + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
             }
         });
         cfg.setFileOutConfigList(focList);
         
         // ===================  strategy setting  ==================
         StrategyConfig strategy = new StrategyConfig();
         strategy.setNaming(NamingStrategy.underline_to_camel)					// table name：  underline_to_camel
                 .setColumnNaming(NamingStrategy.underline_to_camel)			// file name： underline_to_camel
                 .setInclude(tableNames)										// tableNames
                 .setCapitalMode(true)											// enable CapitalMod(ORACLE )
                 .setTablePrefix(prefixTable)									// remove table prefix
//                 .setFieldPrefix(pc.getModuleName() + "_")					// remove fields prefix
//                 .setSuperEntityClass("com.maoxs.pojo")						// Entity implement
//                 .setSuperControllerClass("com.maoxs.controller")				// Controller implement
//                 .setSuperEntityColumns("id") 								// Super Columns
//                 .setEntityLombokModel(true)									// enable lombok
                 .setControllerMappingHyphenStyle(true);						// controller MappingHyphenStyle
       
         // ==================  custome template setting：default mybatis-plus/src/main/resources/templates  ======================
         //default: src/main/resources/templates directory
         TemplateConfig tc = new TemplateConfig();
         tc.setXml(null)			        									// xml template
           .setEntity("/templates/entity.java")		        				// entity template
           .setMapper("/templates/mapper.java")		        				// mapper template
           .setController("/templates/controller.java")       				// service template
           .setService("/templates/service.java")							// serviceImpl template
           .setServiceImpl("/templates/serviceImpl.java");					// controller template
         
         // ====================  gen setting  ===================
         AutoGenerator mpg = new AutoGenerator();
         mpg.setCfg(cfg)
                 .setTemplate(tc)
                 .setGlobalConfig(gc)
                 .setDataSource(dsc)
                 .setPackageInfo(pc)
                 .setStrategy(strategy)
                 .setTemplateEngine(new FreemarkerTemplateEngine());		    // choose freemarker engine，pay attention to pom dependency！
         mpg.execute();
	}

}
