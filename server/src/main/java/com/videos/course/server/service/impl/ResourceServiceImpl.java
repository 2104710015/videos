package com.videos.course.server.service.impl;

import com.videos.course.server.domin.Resource;
import com.videos.course.server.domin.ResourceExample;
import com.videos.course.server.dto.ResourceDto;
import com.videos.course.server.dto.PageDto;
import com.videos.course.server.mapper.ResourceMapper;
import com.videos.course.server.service.ResourceService;
import com.videos.course.server.utils.CopyUtils;
import com.videos.course.server.utils.UUIDUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 隔壁_老陈
 * 代码风骚、效率恐怖、编译器深深鄙视智商。
 * 千里之外定位问题、瞬间修复依旧风骚……
 * @create 2021-04-11 23:24
 */
@Service
public class ResourceServiceImpl implements ResourceService {
    @Autowired
    private ResourceMapper resourceMapper;
    @Override
    public  void getList(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
            //设置排序 为 倒序方式
        ResourceExample resourceExample = new ResourceExample();
        List<Resource> resources = resourceMapper.selectByExample(resourceExample);
        PageInfo<Resource> resourcePageInfo = new PageInfo<>(resources);
        pageDto.setTotal(resourcePageInfo.getTotal());
        /**
         * 使用jdk8的stream()进行遍历与拷贝数据
         */
        List<ResourceDto> resourceDtoList = resources.stream().map(resource -> {
            ResourceDto resourceDto = new ResourceDto();
            BeanUtils.copyProperties(resource, resourceDto);
            return resourceDto;
        }).collect(Collectors.toList());

        pageDto.setData(resourceDtoList);

    }

    @Override
    public void saveAndUpdate(ResourceDto resourceDto) {
        Resource resource = new Resource();
        BeanUtils.copyProperties(resourceDto, resource);
        if (!StringUtils.isEmpty(resourceDto.getId())) {
            update(resource);
        }else {
           save(resource);
        }
    }

    @Override
    public void del(String id) {
        resourceMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<ResourceDto> getResourceListTree() {
        ResourceExample resourceExample = new ResourceExample();
        //查询所有的资源信息，并进行排序
        resourceExample.setOrderByClause("id ASC");
        List<Resource> resources = resourceMapper.selectByExample(resourceExample);

        //进行转换对象
        List<ResourceDto> resourceDtoList = CopyUtils.copy(resources, ResourceDto.class);
        //自定义父子菜单的排序
        for (int i = resourceDtoList.size()-1; i >=0 ; i--) {//倒序的方式进行遍历
            //进行移除当前的记录
            ResourceDto resourceDto = resourceDtoList.get(i);
            //如当前节点没有对应的父节点，则不进行向下执行
            if (StringUtils.isEmpty(resourceDto.getParent())){
                continue;
            }
            //进行查询对应的父节点
            for (int j = i-1; j >=0; j--) {
                ResourceDto dto = resourceDtoList.get(j);

                if (resourceDto.getParent().equals(dto.getId())) {
                    if (CollectionUtils.isEmpty(dto.getChildren())) {//如没有对象的情况下
                        dto.setChildren(new ArrayList<>());
                    }
                    //进行添加

                    dto.getChildren().add(0,resourceDto);//添加对应的子菜单
                    resourceDtoList.remove(resourceDto);//子菜单找到的父菜单后，进行删除列表中的子菜单
                }
            }
        }
        return resourceDtoList;
    }

    private  void save(Resource  resource){

        resource.setId(UUIDUtils.random32());
        resourceMapper.insert(resource);
    }
    private  void update(Resource  resource){
        resourceMapper.updateByPrimaryKey(resource);
    }

}
