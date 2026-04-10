package com.project.mall;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.DefaultCommentGenerator;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.Properties;

/**
 * 自定义注释生成器：根据列备注生成 {@code @Schema}（OpenAPI 3）。
 * <p>由 MBG 在生成代码时加载，不可单独 {@code javac} 运行；请执行 {@link Generator} 或
 * {@code mvn -pl mall-mbg exec:java}（需先配置 {@code generator.properties} 且数据库可连）。
 * Created by macro on 2018/4/26.
 */
public class CommentGenerator extends DefaultCommentGenerator {
    private boolean addRemarkComments = false;
    private static final String EXAMPLE_SUFFIX="Example";
    private static final String MAPPER_SUFFIX="Mapper";
    private static final String SCHEMA_FULL_CLASS_NAME = "io.swagger.v3.oas.annotations.media.Schema";

    /**
     * 读取 generatorConfig.xml 中 commentGenerator 的配置项。
     */
    @Override
    public void addConfigurationProperties(Properties properties) {
        super.addConfigurationProperties(properties);
        this.addRemarkComments = StringUtility.isTrue(properties.getProperty("addRemarkComments"));
    }

    /**
     * 为实体字段添加 {@code @Schema}（通过 MBG 的 {@code Field.addAnnotation}，便于 SpringDoc 扫描）。
     */
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable,
                                IntrospectedColumn introspectedColumn) {
        String remarks = introspectedColumn.getRemarks();
        if(addRemarkComments&&StringUtility.stringHasValue(remarks)){
//            addFieldJavaDoc(field, remarks);
            if(remarks.contains("\"")){
                remarks = remarks.replace("\"","'");
            }
            field.addAnnotation("@Schema(description = \"" + remarks + "\")");
        }
    }

    /**
     * 将列备注写成 JavaDoc（当前未使用）。
     */
    private void addFieldJavaDoc(Field field, String remarks) {
        field.addJavaDocLine("/**");
        String[] remarkLines = remarks.split(System.getProperty("line.separator"));
        for(String remarkLine:remarkLines){
            field.addJavaDocLine(" * "+remarkLine);
        }
        addJavadocTag(field, false);
        field.addJavaDocLine(" */");
    }

    @Override
    public void addJavaFileComment(CompilationUnit compilationUnit) {
        super.addJavaFileComment(compilationUnit);
        // 仅为实体导入 Schema，排除 Mapper / Example
        if(!compilationUnit.getType().getFullyQualifiedName().contains(MAPPER_SUFFIX)&&!compilationUnit.getType().getFullyQualifiedName().contains(EXAMPLE_SUFFIX)){
            compilationUnit.addImportedType(new FullyQualifiedJavaType(SCHEMA_FULL_CLASS_NAME));
        }
    }
}
