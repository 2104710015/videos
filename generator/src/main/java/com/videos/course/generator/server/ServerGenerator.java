package com.videos.course.generator.server;


import com.videos.course.generator.freemarker.FreemarkerUtils;
import com.videos.course.generator.utils.DbUtil;
import com.videos.course.generator.utils.Field;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author 隔壁_老陈
 * 代码风骚、效率恐怖、编译器深深鄙视智商。
 * 千里之外定位问题、瞬间修复依旧风骚……
 * @create 2021-04-17 23:19
 */
public class ServerGenerator {
    //写入生成模块地址 - Service层 .system com\videos\course\server\\impl
    static   String MODEL="system";
    static  String  toServiceImplPath="server/src/main/java/com/videos/course/server/service/impl/";
    static  String  toServicePath="server/src/main/java/com/videos/course/server/service/";
    static  String  toDtoPath="server/src/main/java/com/videos/course/server/dto/";
    //写入生成模块地址 - Controller层
    static  String  toControllerPath=MODEL+"/src/main/java/com/videos/course/"+MODEL+"/controller/admin/";
    //读取xml配置文件
    static String generatorConfigPath = "server/src/main/resources/generator/generatorConfig.xml";
    public static void main(String[] args) throws Exception {

        String Model_NAME =MODEL.toUpperCase()+"_NAME";
        String model =MODEL;

        // 只生成配置文件中的第一个table节点
        File file = new File(generatorConfigPath);
        SAXReader reader=new SAXReader();
        //读取xml文件到Document中
        Document doc=reader.read(file);
        //获取xml文件的根节点
        Element rootElement=doc.getRootElement();
        //读取context节点
        Element contextElement = rootElement.element("context");
        //定义一个Element用于遍历
        Element tableElement;
        //取第一个“table”的节点
        tableElement=contextElement.elementIterator("table").next();
        String Domain = tableElement.attributeValue("domainObjectName");
        String tableName = tableElement.attributeValue("tableName");
        String tableNameCn = DbUtil.getTableComment(tableName);
        String domain = Domain.substring(0, 1).toLowerCase() + Domain.substring(1);
        System.out.println("表："+tableElement.attributeValue("tableName"));
        System.out.println("Domain："+tableElement.attributeValue("domainObjectName"));

        List<Field> fieldList = DbUtil.getColumnByTableName(tableName);
        Set<String> typeSet = getJavaTypes(fieldList);
        HashMap<String, Object> map = new HashMap<>();
        map.put("Domain",Domain);
        map.put("domain",domain);
        map.put("Model",Model_NAME);
        map.put("model",model);
        map.put("fieldList",fieldList);
        map.put("typeSet",typeSet);
        //生成controller层类
        FreemarkerUtils.initConfig("Controller.ftl");
        FreemarkerUtils.generter(toControllerPath+Domain+"Controller.java",map);

        //生成Service接口
       FreemarkerUtils.initConfig("ServiceFtl.ftl");
        FreemarkerUtils.generter(toServicePath+Domain+"Service.java",map);
        //生成service层实现类
        FreemarkerUtils.initConfig("ServiceImplFtl.ftl");
      FreemarkerUtils.generter(toServiceImplPath+Domain+"ServiceImpl.java",map);
        //生成Dto数据传输层类
        FreemarkerUtils.initConfig("ServiceDto.ftl");
        FreemarkerUtils.generter(toDtoPath+Domain+"Dto.java",map);
        System.out.println("成功生成模块");
    }

    /**
     * 获取所有的Java类型，使用Set去重
     */
    private static Set<String> getJavaTypes(List<Field> fieldList) {
        Set<String> set = new HashSet<>();
        for (int i = 0; i < fieldList.size(); i++) {
            Field field = fieldList.get(i);
            set.add(field.getJavaType());
        }
        return set;
    }
}
