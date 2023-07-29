package com.videos.course.server.service.impl;

import com.videos.course.server.domin.RoleResource;
import com.videos.course.server.domin.RoleResourceExample;
import com.videos.course.server.dto.RoleResourceDto;
import com.videos.course.server.dto.PageDto;
import com.videos.course.server.mapper.RoleResourceMapper;
import com.videos.course.server.service.RoleResourceService;
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

/**
 * @author 隔壁_老陈
 * 代码风骚、效率恐怖、编译器深深鄙视智商。
 * 千里之外定位问题、瞬间修复依旧风骚……
 * @create 2021-04-11 23:24
 */
@Service
public class RoleResourceServiceImpl implements RoleResourceService {
    @Autowired
    private RoleResourceMapper roleResourceMapper;
    @Override
    public  void getList(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
            //设置排序 为 倒序方式
        RoleResourceExample roleResourceExample = new RoleResourceExample();
        List<RoleResource> roleResources = roleResourceMapper.selectByExample(roleResourceExample);
        PageInfo<RoleResource> roleResourcePageInfo = new PageInfo<>(roleResources);
        pageDto.setTotal(roleResourcePageInfo.getTotal());
        /**
         * 使用jdk8的stream()进行遍历与拷贝数据
         */
        List<RoleResourceDto> roleResourceDtoList = roleResources.stream().map(roleResource -> {
            RoleResourceDto roleResourceDto = new RoleResourceDto();
            BeanUtils.copyProperties(roleResource, roleResourceDto);
            return roleResourceDto;
        }).collect(Collectors.toList());

        pageDto.setData(roleResourceDtoList);

    }

    @Override
    public void saveAndUpdate(RoleResourceDto roleResourceDto) {
        RoleResource roleResource = new RoleResource();
        BeanUtils.copyProperties(roleResourceDto, roleResource);
        if (!StringUtils.isEmpty(roleResourceDto.getId())) {
            update(roleResource);
        }else {
           save(roleResource);
        }
    }

    @Override
    public void del(String id) {
        roleResourceMapper.deleteByPrimaryKey(id);
    }

    private  void save(RoleResource  roleResource){

        roleResource.setId(UUIDUtils.random32());
        roleResourceMapper.insert(roleResource);
    }
    private  void update(RoleResource  roleResource){
        roleResourceMapper.updateByPrimaryKey(roleResource);
    }

}
