package com.xdcplus.mp.generator;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 代码生成器
 *
 * @author Rong.Jia
 * @date 2021/04/29 11:22
 */
public class CodeGenerator {

    public static void main(String[] args) {
        String database = "xdcweb";
        String username = "root";
        String password = "123456";
        String host = "localhost";
        String port = "3306";
        String author = "Rong.Jia";

        autoGenerator(database, username, password, host, port, author);

    }

    /**
     * 生成代码
     * @param database 数据库
     * @param username 用户名
     * @param password 密码
     * @param host IP
     * @param port 端口
     * @param author 作者
     */
    public static void autoGenerator(String database, String username,
                                      String password, String host, String port, String author) {

        AutoGenerator mpg = new AutoGenerator();
        mpg.setGlobalConfig(globalConfig(author));
        mpg.setDataSource(dataSourceConfig(database, username, password, host, port));
        mpg.setPackageInfo(packageConfig());
        mpg.setCfg(fileOutConfig());
        mpg.setTemplate(templateConfig());
        mpg.setStrategy(strategyConfig());
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();

    }

    private static StrategyConfig strategyConfig() {

        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityBooleanColumnRemoveIsPrefix(true);
        strategy.setEntitySerialVersionUID(true);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(false);
        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        strategy.setControllerMappingHyphenStyle(false);
        strategy.setTablePrefix("xdc_t_");

        return strategy;
    }

    private static TemplateConfig templateConfig() {

        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
        // templateConfig.setService();
        // templateConfig.setController();

        templateConfig.setXml(null);

        return templateConfig;
    }

    private static InjectionConfig  fileOutConfig() {

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return System.getProperty("user.dir") + "/src/main/resources/mappers/"
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        return cfg;

    }

    private static PackageConfig packageConfig() {
        PackageConfig pc = new PackageConfig();
//        pc.setModuleName(scanner("模块名"));
        pc.setParent("generator");
        return pc;
    }

    /**
     * 读取控制台内容
     */
    private static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("请输入" + tip + "：");
        System.out.println(stringBuilder.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StrUtil.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    /**
     * 全局配置
     */
    private static GlobalConfig globalConfig(String author) {

        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor(author);
        gc.setOpen(false);
        gc.setFileOverride(true);
        gc.setBaseColumnList(true);
        gc.setBaseResultMap(true);

        //实体属性 Swagger2 注解
        gc.setSwagger2(false);

        return gc;

    }

    /**
     * 数据源配置
     * @param database
     * @param username
     * @param password
     * @return
     */
    private static DataSourceConfig dataSourceConfig(
            String database, String username,
            String password, String host, String port) {

        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://"+ host +":"+port+"/"+ database +"?useUnicode=true&useSSL=false&characterEncoding=utf8");
         dsc.setSchemaName("private");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername(username);
        dsc.setPassword(password);

        return dsc;

    }





}
