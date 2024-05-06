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
		// 生成地址 : // System.getProperty("user.dir") == 得到当前项目的实际地址
		String projectDir ="D:\\IdeaProjects\\ETFramework\\generator";
		String outputDir = projectDir+"\\src\\main\\java";
		//String outputDir = System.getProperty("user.dir") + "/src/main/java";
//		String outputDir = "C://Users/VULCAN/Desktop/new";
		// 表名, 注意大小写
		String[] tableNames = new String[]{"user_info"};
		// 数据库地址
		String url = "jdbc:mysql://localhost:3306/demo?useUnicode=true&characterEncoding=utf8";
		// 用户名
		String userName = "root";
		// 密码
		String password = "123456";
		// 父包路径
		String parentPackage = "com.et.generator";
		// 需要去掉的表名前缀
		String prefixTable = "";
		generate(projectDir,outputDir, tableNames, url, userName, password, parentPackage, prefixTable);
	}
	
	/**
	 * @param outputDir   生成地址
	 * @param tableNames  表名
	 * @param url		    数据库地址
	 * @param userName	   用户名
	 * @param password    密码
	 * @param parentPackage  父包路径
	 * @param prefixTable  需要去掉的表名前缀
	 */
	public static void generate(String projectDir,String outputDir, String[] tableNames, String url, String userName,
			String password, String parentPackage, String prefixTable) {
		// ===============  全局配置  ==================
		GlobalConfig gc = new GlobalConfig();
		gc.setOutputDir(outputDir)
			.setActiveRecord(true)								// 是否支持 AR, 实体类只需继承 Model 类即可进行强大的 CRUD 操作
	        .setAuthor("GrassPrince") 							// 设置作者名字
	        .setFileOverride(true) 								// 文件覆盖(全新文件)
	        .setIdType(IdType.AUTO)								// 主键策略
	        .setBaseResultMap(true) 							// SQL 映射文件
	        .setBaseColumnList(true)							// SQL 片段
	        .setServiceName("%sService")						// service的名字
	        .setOpen(false);
		
		// =================  数据源配置   ===============
		DataSourceConfig dsc = new DataSourceConfig();
			dsc.setDbType(DbType.MYSQL)
			 .setDriverName("com.mysql.cj.jdbc.Driver");
		dsc.setUrl(url)
           .setUsername(userName)
           .setPassword(password);
		
		// =================  包配置  ===================
		 PackageConfig pc = new PackageConfig();
         pc.setParent(parentPackage)							// 配置父包路径
//           .setModuleName("base")								// 配置业务包路径
           .setMapper("mapper")
           .setEntity("entity")
//           .setEntity("entity")
           .setService("service")
           //.setServiceImpl("service.impl"); 					// 会自动生成 impl，可以不设定
           .setController("controller");
         
       // ==================  自定义配置  ================= 
         InjectionConfig cfg = new InjectionConfig() {
             @Override
             public void initMap() {
                 // to do nothing
             }
         };
         List<FileOutConfig> focList = new ArrayList<>();
         // 调整 xml 生成目录演示
         focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
             @Override
             public String outputFile(TableInfo tableInfo) {
                 // 自定义输入文件名称
                 return projectDir + "/src/main/resources/mybatis/"
                         + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
             }
         });
         cfg.setFileOutConfigList(focList);
         
         // ===================  策略配置  ================== 
         StrategyConfig strategy = new StrategyConfig();
         strategy.setNaming(NamingStrategy.underline_to_camel)					// 表名命名：  underline_to_camel 底线变驼峰
                 .setColumnNaming(NamingStrategy.underline_to_camel)			// 字段命名： underline_to_camel 底线变驼峰
                 .setInclude(tableNames)										// 需要生成的 表名
                 .setCapitalMode(true)											// 全局大写命名 ORACLE 注意 
                 .setTablePrefix(prefixTable)									// 去掉 表的前缀
//                 .setFieldPrefix(pc.getModuleName() + "_")					// 去掉字段前缀
//                 .setSuperEntityClass("com.maoxs.pojo")						// 继承类
//                 .setSuperControllerClass("com.maoxs.controller")				// 继承类
//                 .setSuperEntityColumns("id") 								// 设置超级超级列
//                 .setEntityLombokModel(true)									// 是否加入lombok
                 .setControllerMappingHyphenStyle(true);						// 设置controller映射联字符
       
         // ==================  自定义模板配置： 默认配置位置 mybatis-plus/src/main/resources/templates  ======================
         // 放置自己项目的 src/main/resources/templates 目录下, 默认名称一下可以不配置，也可以自定义模板名称
         TemplateConfig tc = new TemplateConfig();
         tc.setXml(null)			        									// 设置生成xml的模板
           .setEntity("/templates/entity.java")		        				// 设置生成entity的模板
           .setMapper("/templates/mapper.java")		        				// 设置生成mapper的模板
           .setController("/templates/controller.java")       				// 设置生成service的模板
           .setService("/templates/service.java")							// 设置生成serviceImpl的模板
           .setServiceImpl("/templates/serviceImpl.java");					// 设置生成controller的模板
         
         // ====================  生成配置  =================== 
         AutoGenerator mpg = new AutoGenerator();
         mpg.setCfg(cfg)
                 .setTemplate(tc)
                 .setGlobalConfig(gc)
                 .setDataSource(dsc)
                 .setPackageInfo(pc)
                 .setStrategy(strategy)
                 .setTemplateEngine(new FreemarkerTemplateEngine());		    // 选择 freemarker引擎，注意 pom 依赖必须有！
         mpg.execute();
	}

}
