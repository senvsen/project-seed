package com.yupaits.generator;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Data;
import org.apache.commons.collections4.ListUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    public static final String PROJECT_PATH = "E:" + File.separator + "Demo" + File.separator + "project-seed";
    //Java文件路径
    public static final String JAVA_PATH = "/src/main/java";
    //资源文件路径
    private static final String RESOURCES_PATH = "/src/main/resources";
    //模板文件所在目录
    private static final String TEMPLATE_FILE_PATH = PROJECT_PATH + "/generator" + RESOURCES_PATH + "/templates";
    //Mapper xml文件路径
    private static final String MAPPER_XML_PATH = RESOURCES_PATH + "/mapper/";

    private static final String BASE_PACKAGE = "com.yupaits";
    private static final String BASE_PACKAGE_PATH = "/com/yupaits/";
    private static final String AUTHOR = "yupaits";

    private static final String BASE_DTO_CLASS = "com.yupaits.commons.core.BaseDTO";
    private static final String DELETED_FIELD_NAME = "deleted";
    private static final String VERSION_FIELD_NAME = "version";

    //控制台交互
    private static final String DELETED_COLUMN_TIP = "是否存在逻辑删除deleted字段";
    private static final String VERSION_COLUMN_TIP = "是否存在乐观锁version字段";
    private static final String TRUE_VAL = "y";

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
            globalConfig.setOutputDir(PROJECT_PATH + "/server" + JAVA_PATH);
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
        globalConfig.setActiveRecord(true);
        globalConfig.setIdType(IdType.ID_WORKER);
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

        //策略配置
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setInclude(scanner("表名"));
        strategy.setTableFillList(Lists.newArrayList(
                new TableFill("created_at", FieldFill.INSERT),
                new TableFill("created_by", FieldFill.INSERT),
                new TableFill(DELETED_FIELD_NAME, FieldFill.INSERT),
                new TableFill("updated_at", FieldFill.INSERT_UPDATE),
                new TableFill("updated_by", FieldFill.INSERT_UPDATE)
        ));
        if (scannerBool(DELETED_COLUMN_TIP)) {
            strategy.setLogicDeleteFieldName(DELETED_FIELD_NAME);
        }
        if (scannerBool(VERSION_COLUMN_TIP)) {
            strategy.setVersionFieldName(VERSION_FIELD_NAME);
        }
        strategy.setTablePrefix(packageConfig.getModuleName() + "_");
        mpg.setStrategy(strategy);

        //生成自定义文件配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                this.setMap(Maps.newHashMap());
                this.getMap().put("dtoPackage", BASE_PACKAGE + "." + packageConfig.getModuleName() + ".dto");
                this.getMap().put("voPackage", BASE_PACKAGE + "." + packageConfig.getModuleName() + ".vo");
                this.getMap().put("superDto", BASE_DTO_CLASS.substring(BASE_DTO_CLASS.lastIndexOf(StringPool.DOT) + 1));
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        //生成Mapper.xml文件
        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return PROJECT_PATH + "/server" + MAPPER_XML_PATH + packageConfig.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        if (StringUtils.isNotEmpty(strategy.getSuperEntityClass())) {
            //生成DTO Create类
            focList.add(new FileOutConfig("/templates/dto.create.java.ftl") {
                @Override
                public String outputFile(TableInfo tableInfo) {
//                    tableInfo.getImportPackages().remove(com.baomidou.mybatisplus.annotation.TableName.class.getCanonicalName());
//                    tableInfo.setImportPackages(BASE_DTO_CLASS);
                    return PROJECT_PATH + "/server" + JAVA_PATH + BASE_PACKAGE_PATH + packageConfig.getModuleName()
                            + "/dto/" + tableInfo.getEntityName() + "Create" + StringPool.DOT_JAVA;
                }
            });
            //生成DTO Update类
            focList.add(new FileOutConfig("/templates/dto.update.java.ftl") {
                @Override
                public String outputFile(TableInfo tableInfo) {
//                    tableInfo.getCommonFields().stream().filter(TableField::isKeyFlag).findFirst().ifPresent(idField -> {
//                        if (!tableInfo.getFields().contains(idField)) {
//                            tableInfo.getFields().add(0, idField);
//                        }
//                    });
                    return PROJECT_PATH + "/server" + JAVA_PATH + BASE_PACKAGE_PATH + packageConfig.getModuleName()
                            + "/dto/" + tableInfo.getEntityName() + "Update" + StringPool.DOT_JAVA;
                }
            });
            //生成VO类
            focList.add(new FileOutConfig("/templates/vo.java.ftl") {
                @Override
                public String outputFile(TableInfo tableInfo) {
//                    tableInfo.getImportPackages().remove(BASE_DTO_CLASS);
//                    tableInfo.setImportPackages(java.io.Serializable.class.getCanonicalName());
//                    tableInfo.setImportPackages(java.time.LocalDateTime.class.getCanonicalName());
//                    tableInfo.getCommonFields().stream().filter(tableField -> {
//                        return !tableField.isKeyFlag() && !DELETED_FIELD_NAME.equals(tableField.getPropertyName());
//                    }).forEach(tableField -> {
//                        if (!tableInfo.getFields().contains(tableField)) {
//                            tableInfo.getFields().add(tableField);
//                        }
//                    });
                    return PROJECT_PATH + "/server" + JAVA_PATH + BASE_PACKAGE_PATH + packageConfig.getModuleName()
                            + "/vo/" + tableInfo.getEntityName() + "VO" + StringPool.DOT_JAVA;
                }
            });
        }
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        //设置文件模板
        TemplateConfig templateConfig = new TemplateConfig().setXml(null);
        mpg.setTemplate(templateConfig);
        //设置文件模板引擎
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
    }

    /**
     * 读取控制台内容
     * @param tip 控制台输入提示
     * @return 读取到的内容
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入").append(tip).append("：");
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
     * 读取控制台输入的bool值
     * @param tip 控制台提示输入
     * @return 读取到的值
     */
    protected boolean scannerBool(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请选择").append(tip).append("(是: y, 否: 其他输入)?");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            return StringUtils.isNotEmpty(ipt) && TRUE_VAL.equals(ipt);
        }
        return false;
    }

    /**
     * 生成
     */
    protected void generate() {
        init();
        mpg.execute();
    }
}
