package com.videos.course.server.service.impl;

import com.videos.course.server.domain.${Domain};
import com.videos.course.server.domain.${Domain}Example;
import com.videos.course.server.domain.TestUsers;
import com.videos.course.server.dto.${Domain}Dto;
import com.videos.course.server.dto.PageDto;
import com.videos.course.server.mapper.${Domain}Mapper;
import com.videos.course.server.mapper.TestUsersMapper;
import com.videos.course.server.service.${Domain}Service;
import com.videos.course.server.utils.UUIDUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;



import java.util.List;
import java.util.stream.Collectors;
<#list typeSet as type>
    <#if type=='LocalDateTime'>
import java.time.LocalDateTime;
    </#if>
</#list>

/**
 * @author 隔壁_老陈
 * 代码风骚、效率恐怖、编译器深深鄙视智商。
 * 千里之外定位问题、瞬间修复依旧风骚……
 * @create 2021-04-11 23:24
 */
@Service
public class ${Domain}ServiceImpl implements ${Domain}Service {
    @Autowired
    private ${Domain}Mapper ${domain}Mapper;
    @Override
    public  void getList(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
            //设置排序 为 倒序方式
        ${Domain}Example ${domain}Example = new ${Domain}Example();
        <#list fieldList as field>
            <#if field.nameHump=='sort'>
                ${domain}Example.setOrderByClause("sort asc");
            </#if>
        </#list>
        List<${Domain}> ${domain}s = ${domain}Mapper.selectByExample(${domain}Example);
        PageInfo<${Domain}> ${domain}PageInfo = new PageInfo<>(${domain}s);
        pageDto.setTotal(${domain}PageInfo.getTotal());
        /**
         * 使用jdk8的stream()进行遍历与拷贝数据
         */
        List<${Domain}Dto> ${domain}DtoList = ${domain}s.stream().map(${domain} -> {
            ${Domain}Dto ${domain}Dto = new ${Domain}Dto();
            BeanUtils.copyProperties(${domain}, ${domain}Dto);
            return ${domain}Dto;
        }).collect(Collectors.toList());

        pageDto.setData(${domain}DtoList);

    }

    @Override
    public void saveAndUpdate(${Domain}Dto ${domain}Dto) {
        ${Domain} ${domain} = new ${Domain}();
        BeanUtils.copyProperties(${domain}Dto, ${domain});
        if (!StringUtils.isEmpty(${domain}Dto.getId())) {
            update(${domain});
        }else {
           save(${domain});
        }
    }

    @Override
    public void del(String id) {
        ${domain}Mapper.deleteByPrimaryKey(id);
    }

    private  void save(${Domain}  ${domain}){
    <#list typeSet as type>
        <#if type=='LocalDateTime'>
            LocalDateTime now = LocalDateTime.now();
        </#if>
    </#list>

        <#list fieldList as field>
            <#if field.nameHump=='createdAt'>
                ${domain}.setCreatedAt(now);
            </#if>
            <#if field.nameHump=='updatedAt'>
                ${domain}.setUpdatedAt(now);
            </#if>
        </#list>
        ${domain}.setId(UUIDUtils.random32());
        ${domain}Mapper.insert(${domain});
    }
    private  void update(${Domain}  ${domain}){
        <#list fieldList as field>
            <#if field.nameHump=='updatedAt'>
                ${domain}.setUpdatedAt(LocalDateTime.now());
            </#if>
        </#list>
        ${domain}Mapper.updateByPrimaryKey(${domain});
    }

}
