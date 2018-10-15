package com.yupaits.generator;

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
import lombok.Data;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 代码生成器
 *
 * @author yupaits
 * @date 2018/10/15
 */
@Data
public abstract class AbstractCodeGenerator {

    //JDBC配置
    private static final String DATABASE_NAME = "test";
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/" + DATABASE_NAME + "?useUnicode=true&useSSL=false&characterEncoding=utf8";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "sql123";
    private static final String JDBC_DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";

    //项目在硬盘的路径
    public static final String PROJECT_PATH = "D:" + File.separator + "Projects" + File.separator + "project-seed";
    //Java文件路径
    public static final String JAVA_PATH = "/src/main/java";
    //资源文件路径
    private static final String RESOURCES_PATH = "/src/main/resources";
    //模板文件所在目录
    private static final String TEMPLATE_FILE_PATH = PROJECT_PATH + "/generator" + RESOURCES_PATH + "/templates";
    //Mapper xml文件路径
    private static final String MAPPER_XML_PATH = RESOURCES_PATH + "/mapper/";

    private static final String BASE_PACKAGE = "com.yupaits";
    private static final String AUTHOR = "yupaits";

    private static final String BASE_ENTITY_CLASS = "com.yupaits.commons.core.BaseEntity";
    private static final String BASE_CONTROLLER_CLASS = "com.yupaits.commons.core.BaseController";

    private AutoGenerator mpg = new AutoGenerator();
    private GlobalConfig globalConfig = new GlobalConfig();
    private PackageConfig packageConfig = new PackageConfig();
    private StrategyConfig strategy = new StrategyConfig();

    /**
     * @param moduleName      模块名称，为空时需要在控制台手动输入
     * @param outputDir       文件输出路径，为空时使用默认路径
     */
    public AbstractCodeGenerator(String moduleName, String outputDir) {
        if (StringUtils.isEmpty(moduleName)) {
            packageConfig.setModuleName(scanner("模块名"));
        } else {
            packageConfig.setModuleName(moduleName);
        }
        if (StringUtils.isNotEmpty(outputDir)) {
            globalConfig.setOutputDir(outputDir);
        } else {
            globalConfig.setOutputDir(PROJECT_PATH + "/" + packageConfig.getModuleName() + JAVA_PATH);
        }
    }

    private void init() {
        //全局配置
        globalConfig.setAuthor(AUTHOR);
        globalConfig.setFileOverride(true);
        globalConfig.setOpen(false);
        globalConfig.setBaseResultMap(true);
        globalConfig.setBaseColumnList(true);
        globalConfig.setSwagger2(true);
        mpg.setGlobalConfig(globalConfig);

        //数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDriverName(JDBC_DRIVER_CLASS_NAME);
        dsc.setUrl(JDBC_URL);
        dsc.setUsername(JDBC_USERNAME);
        dsc.setPassword(JDBC_PASSWORD);
        mpg.setDataSource(dsc);

        //包配置
        packageConfig.setParent(BASE_PACKAGE);
        mpg.setPackageInfo(packageConfig);

        //自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return PROJECT_PATH + "/" + packageConfig.getModuleName() + MAPPER_XML_PATH
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
        mpg.setTemplate(new TemplateConfig().setXml(null));

        //策略配置
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setSuperEntityClass(BASE_ENTITY_CLASS);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        strategy.setSuperControllerClass(BASE_CONTROLLER_CLASS);
        strategy.setSuperEntityColumns("id");
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setInclude(scanner("表名"));
        strategy.setTablePrefix(packageConfig.getModuleName() + "_");
        mpg.setStrategy(strategy);

        //设置文件模板引擎
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
    }

    /**
     * 读取控制台内容
     *
     * @param tip 控制台输入提示
     * @return 读取到的内容
     */
    protected String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    /**
     * 生成
     */
    protected void generate() {
        init();
        mpg.execute();
    }
}
