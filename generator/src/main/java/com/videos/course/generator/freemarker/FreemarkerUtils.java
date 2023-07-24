package com.videos.course.generator.freemarker;


import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * @author 隔壁_老陈
 * 代码风骚、效率恐怖、编译器深深鄙视智商。
 * 千里之外定位问题、瞬间修复依旧风骚……
 * @create 2021-04-17 23:18
 */
public class FreemarkerUtils {
    //模版地址
    public  static String ftlPath="generator/src/main/java/com/videos/course/generator/ftl";
    static Template template;

    /**
     * 获取模版数据性文件
     * @param ftlName  需要生成的模版文件
     * @throws IOException
     */
    public   static  void   initConfig(String  ftlName) throws IOException {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_31);
        configuration.setDirectoryForTemplateLoading(new File(ftlPath));
        configuration.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_31));
         template = configuration.getTemplate(ftlName);
    }

    /**
     * 使用IO写入生成的模版
     * @param ftlName  模版名称如:xxx.java
     * @param map     变量名:模版包中的class名与参数名
     * @throws IOException
     * @throws TemplateException
     */
    public  static  void generter(String  ftlName, Map<String ,Object> map) throws IOException, TemplateException {
        FileWriter fileWriter = new FileWriter(ftlName);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        template.process(map,bufferedWriter);
        bufferedWriter.close();
        fileWriter.close();
    }

}
