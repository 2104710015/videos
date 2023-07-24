package com.videos.course.${model}.controller.admin;

import com.videos.course.server.dto.${Domain}Dto;
import com.videos.course.server.dto.PageDto;
import com.videos.course.server.service.${Domain}Service;
import com.videos.course.server.utils.ValidatorUtils;
import com.videos.course.server.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author 隔壁_老陈
 * 代码风骚、效率恐怖、编译器深深鄙视智商。
 * 千里之外定位问题、瞬间修复依旧风骚……
 * @create 2021-04-11 18:07
 */
@RestController
@RequestMapping("/admin/${domain}")
@Slf4j
public class ${Domain}Controller {
        //输出日志的服务名称
     public static final String BUSINESS_NAME="${Model}服务之(${Domain}Controller)";
    @Autowired
    private ${Domain}Service ${domain}Service;

    /**
     * 查询所有的数据信息
     * @param pageDto  分页的共用类
     * @return    返回共用的json数据vo模版格式
     */
    @PostMapping("/list")
    public ResponseVo getList(@RequestBody PageDto pageDto){
        ${domain}Service.getList(pageDto);

       ResponseVo responseVo = new ResponseVo();
       responseVo.setCode("200").setMessage("查询成功").setContent(pageDto);
        return  responseVo;
    }

    /**
     * 添加和修改的共用类
     * @param ${domain}Dto  添加与修改的dto实体类
     * @return   返回共用的json数据vo模版格式
     */
    @PostMapping("/saveAndUpdate")
    public ResponseVo saveAndUpdate(@RequestBody ${Domain}Dto ${domain}Dto){
        /**
         * 进行数据的长度和非空验证
         */
   <#--     ValidatorUtils.require(${domain}Dto.getName(),"名称");
        ValidatorUtils.require(${domain}Dto.getCourseId(),"课程ID");
        ValidatorUtils.length(${domain}Dto.getCourseId(),"课程ID",1,8);-->

    <#list fieldList as field>
       <#if field.name!="id" && field.nameHump!="createdAt" && field.nameHump!="updatedAt" && field.nameHump!="sort">
        <#if !field.nullAble>
            ValidatorUtils.require(${domain}Dto.get${field.nameBigHump}(), "${field.nameCn}");
        </#if>
        <#if (field.length > 0)>
            ValidatorUtils.length(${domain}Dto.get${field.nameBigHump}(), "${field.nameCn}", 1, ${field.length?c});
        </#if>
       </#if>
    </#list>

        ${domain}Service.saveAndUpdate(${domain}Dto);
        ResponseVo responseVo = new ResponseVo();
        responseVo.setCode("200").setMessage("操作成功").setContent(${domain}Dto);
        return  responseVo;
    }

    /**
     * 通过id编号进行 物理删除
     * @param id  删除的id编号
     * @return   返回共用的json数据vo模版格式
     */
    @RequestMapping("/del/{id}")
    public ResponseVo del(@PathVariable String id){
<#--

        ValidatorUtils.require(id,"章节ID");
        ValidatorUtils.length(id,"章节ID",1,8);
-->


        ${domain}Service.del(id);
        ResponseVo responseVo = new ResponseVo();
        responseVo.setCode("200").setMessage("操作成功");
        return  responseVo;
    }



}
