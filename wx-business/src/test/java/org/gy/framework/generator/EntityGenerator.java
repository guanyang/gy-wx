package org.gy.framework.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 功能描述: 实例类生成工具
 * 
 */
public class EntityGenerator {

    private static final Logger logger                         = LoggerFactory.getLogger(EntityGenerator.class);

    public static final String  DEFAULT_PARENT_PATH            = "./src/test/java";

    public static final String  DEFAULT_JAVA_EXT               = ".java";
    public static final String  DEFAULT_XML_EXT                = ".xml";
    public static final String  DEFAULT_JS_EXT                 = ".js";
    public static final String  DEFAULT_FTL_EXT                = ".ftl";

    public static final String  DEFAULT_TEMPLATE_PATH          = "/conf/generator/template";

    public static final String  DEFAULT_ENTITY_KEY             = "entity";

    public static final String  DEFAULT_ENTITY_TEMPLATE        = "eo.ftl";
    public static final String  DEFAULT_BO_TEMPLATE            = "bo.ftl";
    public static final String  DEFAULT_CONTROLLER_TEMPLATE    = "controller.ftl";
    public static final String  DEFAULT_FTL_TEMPLATE           = "ftl.ftl";
    public static final String  DEFAULT_BIZ_TEMPLATE           = "biz.ftl";
    public static final String  DEFAULT_JS_TEMPLATE            = "js.ftl";
    public static final String  DEFAULT_SQL_TEMPLATE           = "sqlXml.ftl";

    public static final String  DEFAULT_BIZ_FILE_SUFFIX        = "Biz";
    public static final String  DEFAULT_BO_FILE_SUFFIX         = "Bo";
    public static final String  DEFAULT_CONTROLLER_FILE_SUFFIX = "Controller";
    public static final String  DEFAULT_FTL_FILE_SUFFIX        = "List";
    public static final String  DEFAULT_JS_FILE_SUFFIX         = "List";
    public static final String  DEFAULT_SQL_PREFIX             = "sqlMap_";

    private EntityGenerator() {

    }

    public static void main(String[] args) {
        // 生成文件之后，需要刷新工程，才能看到新生成的文件

        // 定义数据库配置
        String url = "jdbc:mysql://172.19.59.22:3306/wx?useUnicode=true&characterEncoding=utf-8";
        String user = "root";
        String password = "123456";
        String schema = "wx";
        String[] tableNames = {
            "wx_app_config"
        };

        // 设置基础参数
        GenerateParam param = new GenerateParam();
        param.setUrl(url);
        param.setUser(user);
        param.setPassword(password);
        param.setSchema(schema);
        param.setTableNames(tableNames);

        // 加载实体数据
        EntityGenerator.loadEntities(param);
        // 设置包的统一父路径，必须定义，否则biz，xml无法获取路径
        param.setRootPackage("org.gy.framework");

//        // 生成Biz
//        param.setTargetJavaPackage("org.gy.framework.biz");
//        EntityGenerator.generateBiz(param);
        //
//        // 生成Bo
//        param.setTargetJavaPackage("org.gy.framework.bo");
//        EntityGenerator.generateBo(param);

//        // 生成Controller
//        param.setTargetJavaPackage("org.gy.framework.controller");
//        EntityGenerator.generateController(param);

//        // 生成ftl
//        param.setTargetJavaPackage("freemarker.web.page");
//        EntityGenerator.generateFtl(param);

        // 生成js
        param.setTargetJavaPackage("project.web.js");
        EntityGenerator.generateJs(param);

//        // 生产成sql
//        param.setTargetJavaPackage("conf.sqlMap");
//        EntityGenerator.generateSqlXml(param);
    }

    /**
     * 功能描述: 生成Js
     * 
     * @param param
     */
    public static void generateSqlXml(GenerateParam param) {

        param.setFileSuffix(null);
        param.setFilePrefix(DEFAULT_SQL_PREFIX);
        param.setFileExt(DEFAULT_XML_EXT);
        param.setTargetPath(DEFAULT_PARENT_PATH);
        param.setTemplateName(DEFAULT_SQL_TEMPLATE);
        param.setTemplatePath(DEFAULT_TEMPLATE_PATH);

        generate(param);
    }

    /**
     * 功能描述: 生成Js
     * 
     * @param param
     */
    public static void generateJs(GenerateParam param) {

        param.setFileSuffix(DEFAULT_JS_FILE_SUFFIX);
        param.setFilePrefix(null);
        param.setFileExt(DEFAULT_JS_EXT);
        param.setTargetPath(DEFAULT_PARENT_PATH);
        param.setTemplateName(DEFAULT_JS_TEMPLATE);
        param.setTemplatePath(DEFAULT_TEMPLATE_PATH);

        generate(param);
    }

    /**
     * 功能描述: 生成ftl
     * 
     * @param param
     */
    public static void generateFtl(GenerateParam param) {

        param.setFileSuffix(DEFAULT_FTL_FILE_SUFFIX);
        param.setFilePrefix(null);
        param.setFileExt(DEFAULT_FTL_EXT);
        param.setTargetPath(DEFAULT_PARENT_PATH);
        param.setTemplateName(DEFAULT_FTL_TEMPLATE);
        param.setTemplatePath(DEFAULT_TEMPLATE_PATH);

        generate(param);
    }

    /**
     * 功能描述: 生成Controller
     * 
     * @param param
     */
    public static void generateController(GenerateParam param) {

        param.setFileSuffix(DEFAULT_CONTROLLER_FILE_SUFFIX);
        param.setFilePrefix(null);
        param.setFileExt(DEFAULT_JAVA_EXT);
        param.setTargetPath(DEFAULT_PARENT_PATH);
        param.setTemplateName(DEFAULT_CONTROLLER_TEMPLATE);
        param.setTemplatePath(DEFAULT_TEMPLATE_PATH);

        generate(param);
    }

    /**
     * 功能描述: 生成Biz
     * 
     * @param param
     */
    public static void generateBiz(GenerateParam param) {

        param.setFileSuffix(DEFAULT_BIZ_FILE_SUFFIX);
        param.setFilePrefix(null);
        param.setFileExt(DEFAULT_JAVA_EXT);
        param.setTargetPath(DEFAULT_PARENT_PATH);
        param.setTemplateName(DEFAULT_BIZ_TEMPLATE);
        param.setTemplatePath(DEFAULT_TEMPLATE_PATH);

        generate(param);
    }

    /**
     * 功能描述: 生成BO
     * 
     * @param param
     */
    public static void generateBo(GenerateParam param) {
        param.setFileSuffix(DEFAULT_BO_FILE_SUFFIX);
        param.setFilePrefix(null);
        param.setFileExt(DEFAULT_JAVA_EXT);
        param.setTargetPath(DEFAULT_PARENT_PATH);
        param.setTemplateName(DEFAULT_BO_TEMPLATE);
        param.setTemplatePath(DEFAULT_TEMPLATE_PATH);

        generate(param);
    }

    /**
     * 功能描述: 生成实体类
     * 
     * @param param
     */
    public static void generateEntity(GenerateParam param) {
        param.setFileSuffix(null);
        param.setFilePrefix(null);
        param.setFileExt(DEFAULT_JAVA_EXT);
        param.setTargetPath(DEFAULT_PARENT_PATH);
        param.setTemplateName(DEFAULT_ENTITY_TEMPLATE);
        param.setTemplatePath(DEFAULT_TEMPLATE_PATH);

        generate(param);

    }

    /**
     * 功能描述: 加载实体数据
     * 
     * @param param
     * @return
     */
    public static void loadEntities(GenerateParam param) {
        List<Entity> list = MysqlHelper.wrapEntities(param.getUrl(), param.getUser(), param.getPassword(), param.getSchema(), param.getTableNames());
        param.setEntities(list);
    }

    /**
     * 功能描述: 生成文件
     * 
     * @param param 模板生成参数封装
     */
    public static void generate(GenerateParam param) {
        try {
            Template template = initTemplate(param.getTemplatePath(), param.getTemplateName());
            if (CollectionUtils.isNotEmpty(param.getEntities())) {
                for (Entity entity : param.getEntities()) {
                    wrapEntity(entity, param);
                    createFile(template, param.getTargetPath(), entity);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 功能描述: 组装数据
     * 
     * @param entity 数据实体
     * @param param 参数封装实体
     */
    private static void wrapEntity(Entity entity,
                                   GenerateParam param) {
        entity.setJavaPackage(param.getTargetJavaPackage());
        String suffix = "";
        if (StringUtils.isNotBlank(param.getFileSuffix())) {
            suffix = param.getFileSuffix().trim();
        }
        String prefix = "";
        if (StringUtils.isNotBlank(param.getFilePrefix())) {
            prefix = param.getFilePrefix().trim();
        }
        entity.setFileName(prefix + entity.getClassName() + suffix + param.getFileExt());

        if (StringUtils.isBlank(param.getRootPackage())) {
            throw new IllegalArgumentException("包的根路径没有定义，将导致biz，xml无法获取路径");
        }
        entity.setRootPackage(param.getRootPackage());

    }

    /**
     * 功能描述: 根据模板创建文件
     * 
     * @param template 模板
     * @param outParentPath 输出路径
     * @param entity 数据封装
     * @throws TemplateException
     * @throws IOException
     * @version 2.0.0
     * @author guanyang
     */
    private static void createFile(Template template,
                                   String outParentPath,
                                   Entity entity) {
        File outDirFile = new File(outParentPath);
        if (!outDirFile.exists()) {
            outDirFile.mkdir();
        }
        Map<String, Entity> root = createDataModel(entity);
        File javaFile = buildJavaFilename(outDirFile, entity.getJavaPackage(), entity.getFileName());
        Writer javaWriter = null;
        try {
            javaWriter = new FileWriter(javaFile);
            template.process(root, javaWriter);
            javaWriter.flush();
            logger.debug("文件生成路径：{}", javaFile.getCanonicalPath());
        } catch (Exception e) {
            logger.error("模板构建异常：" + e.getMessage(), e);
        } finally {
            if (javaWriter != null) {
                try {
                    javaWriter.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }

    /**
     * 功能描述: 组装模板数据
     * 
     * @param entity
     * @return
     * @version 2.0.0
     * @author guanyang
     */
    private static Map<String, Entity> createDataModel(Entity entity) {
        Map<String, Entity> root = new HashMap();
        root.put(DEFAULT_ENTITY_KEY, entity);
        return root;
    }

    /**
     * 创建文件所在路径 和 返回文件File对象
     * 
     * @param outDirFile 生成文件路径
     * @param javaPackage java包名
     * @param fileName 文件名
     * @return
     */
    private static File buildJavaFilename(File outDirFile,
                                          String javaPackage,
                                          String fileName) {
        String packageSubPath = javaPackage.replace('.', '/');
        File packagePath = new File(outDirFile, packageSubPath);
        File file = new File(packagePath, fileName);
        if (!packagePath.exists()) {
            packagePath.mkdirs();
        }
        return file;
    }

    /**
     * 功能描述: 初始化模板引擎
     * 
     * @param templatePath 模板路径
     * @param templateName 模板名称
     * @return
     * @throws IOException
     */
    private static Template initTemplate(String templatePath,
                                         String templateName) throws IOException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
        ClassLoader classLoader = EntityGenerator.class.getClassLoader();
        cfg.setClassLoaderForTemplateLoading(classLoader, templatePath);
        cfg.setDefaultEncoding("UTF-8");
        return cfg.getTemplate(templateName);
    }

}
