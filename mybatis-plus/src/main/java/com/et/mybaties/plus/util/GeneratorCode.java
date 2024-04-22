package com.et.mybaties.plus.util;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class GeneratorCode {
    /**
     * database connect
     * */
    private static final String dbUrl = "jdbc:mysql://localhost:3306/demo?useUnicode=true&useSSL=false&characterEncoding=utf8";
    /**
     * username
     * */
    private static final String username = "root";
    /**
     * pasword
     * */
    private static final String password = "123456";
    /**
     * moduleName
     * */
    private static final String moduleName = "/mybatis-plus";

    /**
     * <p>
     * read console content
     * @param
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("please input:" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("please right conntent:" + tip + "！");
    }

    public static void main(String[] args) {
        // Code Generateor
        AutoGenerator mpg = new AutoGenerator();
        String module = scanner("please input module");
        // GlobalCOnfig
        GlobalConfig gc = new GlobalConfig();
        //D:\IdeaProjects\ETFramework
        String basedir ="D:/IdeaProjects/ETFramework/";
        String projectPath = basedir+moduleName;
        System.out.println(projectPath);
        //OutputDir
        gc.setOutputDir(projectPath+"/src/main/java");
        gc.setAuthor("stopping");
        //some generate rule
        gc.setMapperName("%sMapper");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImp");
        gc.setControllerName("%sController");
        gc.setXmlName("%sMapper");
        gc.setIdType(IdType.AUTO);
        gc.setOpen(false);
        //IsOverride
        gc.setFileOverride(true);
        //isSwagger2
        gc.setSwagger2(false);
        mpg.setGlobalConfig(gc);

        //datasource
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(dbUrl);
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername(username);
        dsc.setPassword(password);
        mpg.setDataSource(dsc);

        // PackageConfig
        PackageConfig pc = new PackageConfig();
        //package path
        pc.setParent("com.et.mybaties.plus");
        //subpackage path
        pc.setMapper("mapper."+module);
        pc.setController("controller."+module);
        pc.setService("service."+module);
        pc.setServiceImpl("service."+module+".imp");
        pc.setEntity("model.entity");
        pc.setXml("Mapper");
        mpg.setPackageInfo(pc);

        //custom config
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        //  freemarker
        String templatePath = "/templates/mapper.xml.ftl";

        // FileOutConfig
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // Mapper
                String xmlUrl = projectPath + "/src/main/resources/mapper/" + module
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
                System.out.println("xml path:"+xmlUrl);
                return xmlUrl;
            }
        });
  
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // templateConfig
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // StrategyConfig
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        // common file
        //strategy.setSuperEntityColumns("id");
        strategy.setInclude(scanner("tablename，multi can be seperated ,").split(","));
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");
        //isAnnotationEnable
        strategy.setEntityTableFieldAnnotationEnable(true);
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }
}

