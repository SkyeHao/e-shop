package com.neu.edu.config;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.neu.edu.pojo.BasePojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Generator {
    public static void main(String[] args) {
        // 构建代码生成器
        AutoGenerator mpg = new AutoGenerator();
        // 构建配置器
        GlobalConfig gc = new GlobalConfig();
        gc.setAuthor("hsk");
        // 定义项目路径
        String PATH = System.getProperty("user.dir");
        gc.setOutputDir(PATH + "/pojo/src/main/java");
        mpg.setGlobalConfig(gc);
        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://192.168.40.100:3306/erupt?useUnicode=true&useSSL=false&characterEncoding=utf8");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("123456");
        mpg.setDataSource(dsc);
        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.neu.edu");
        pc.setEntity("pojo");
        pc.setMapper("mapper");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        pc.setController("controller");
        Map<String,String> pathInfo = new HashMap<>();
        pathInfo.put("xml_path",PATH + "/pojo/src/main/resources/com/neu/edu/mapper");
        pathInfo.put("entity_path",PATH + "/pojo/src/main/java/com/neu/edu/pojo");
        pathInfo.put("mapper_path",PATH + "/pojo/src/main/java/com/neu/edu/mapper");
        pathInfo.put("service_path",PATH + "/pojo/src/main/java/com/neu/edu/service");
        pathInfo.put("service_impl_path",PATH + "/pojo/src/main/java/com/neu/edu/service/impl");
        pathInfo.put("controller_path",PATH + "/pojo/src/main/java/com/neu/edu/controller");
        pc.setPathInfo(pathInfo);
        mpg.setPackageInfo(pc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setSuperEntityClass(BasePojo.class);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        // 公共父类

        // 写于父类中的公共字段
//        每次只能运行一个表
        strategy.setSuperEntityColumns("id");
//        strategy.setInclude("cmt_category");
        strategy.setInclude("omt_order");
//        strategy.setInclude("omt_order_item");
//        strategy.setInclude("omt_pay_info");
//        strategy.setInclude("pmt_cart");
//        strategy.setInclude("pmt_product");
//        strategy.setInclude("umt_shipping");
//        strategy.setInclude("umt_user");
//        strategy.setInclude("img");
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        // 生成代码
        mpg.execute();
    }
}
