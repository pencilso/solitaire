package cn.pencilso.solitaire.start;

import cn.pencilso.solitaire.solitairedao.base.BaseIService;
import cn.pencilso.solitaire.solitairedao.base.BaseModel;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
//solitaire_user

/**
 * Create By pencilso on 2019/5/16
 */
public class MybatisPlusCodeGenerator {
    private static final String OUT_PUT_DIR_CODE = "core/solitaire-dao/src/main/java";
    private static final String OUT_PUT_DIR_MAPPER = "core/solitaire-dao/src/main/resources/mapper/";

    private static final String AUTHOR = "pencilso";

    private static final String JDBC_URL = "jdbc:mysql://10.211.55.6:3306/solitaire?useUnicode=true&useSSL=false&characterEncoding=utf8";

    private static final String JDBC_USERNAME = "root";

    private static final String JDBC_PASSWORD = "1237654321";


    private static final String TABLE_PREFIX = "solitaire";


    private static final String DAO_SUPER_PACKAGE = "cn.pencilso.solitaire.solitairedao";

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (!StringUtils.isBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
//        String projectPath = System.getProperty("public_api.dir");
//        gc.setOutputDir("src/main/java");
        gc.setOutputDir(OUT_PUT_DIR_CODE);
        gc.setAuthor(AUTHOR);
        gc.setOpen(false);

        gc.setEntityName("%sModel");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");

        // gc.setSwagger2(true); 实体属性 Swagger2 注解
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(JDBC_URL);
//         dsc.setSchemaName("inlan_");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername(JDBC_USERNAME);
        dsc.setPassword(JDBC_PASSWORD);
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
//        pc.setModuleName(scanner("模块名"));
//        pc.setModuleName("");
        pc.setEntity("model");
        pc.setServiceImpl("service.impl");
        pc.setParent(DAO_SUPER_PACKAGE);
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
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
                return OUT_PUT_DIR_MAPPER + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        /*
        cfg.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                // 判断自定义文件夹是否需要创建
                checkDir("调用默认方法创建的目录");
                return false;
            }
        });
        */
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
        // templateConfig.setService();
        // templateConfig.setController();

        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setSuperEntityClass(BaseModel.class.getName());
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(false);
        strategy.setSuperControllerClass("com.baomidou.ant.common.BaseController");
        strategy.setSuperServiceClass(BaseIService.class.getName());
        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        strategy.setSuperEntityColumns("id", "mid", "if_del", "version", "create_date", "update_date");
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(TABLE_PREFIX);
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();

        if (!StringUtils.isBlank(DAO_SUPER_PACKAGE)) {
            StringBuilder stringBuilder = new StringBuilder(gc.getOutputDir());
            String[] split = DAO_SUPER_PACKAGE.split("\\.");
            for (String s : split) {
                stringBuilder.append("/").append(s);
            }
            stringBuilder.append("/controller");
            File file = new File(stringBuilder.toString());
            deleteDir(file);
        }
    }

    private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }
}
