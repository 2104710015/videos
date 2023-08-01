package com.videos.course.server.service.impl;

import com.videos.course.server.domin.*;
import com.videos.course.server.dto.RoleDto;
import com.videos.course.server.dto.PageDto;
import com.videos.course.server.enums.BusinessExceptionEnum;
import com.videos.course.server.exception.ServerVideosException;
import com.videos.course.server.mapper.RoleMapper;
import com.videos.course.server.mapper.RoleResourceMapper;
import com.videos.course.server.mapper.RoleUserMapper;
import com.videos.course.server.service.RoleService;
import com.videos.course.server.utils.UUIDUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;


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
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleResourceMapper roleResourceMapper;


    @Autowired
    private RoleUserMapper roleUserMapper;
    @Override
    public  void getList(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
            //设置排序 为 倒序方式
        RoleExample roleExample = new RoleExample();
        List<Role> roles = roleMapper.selectByExample(roleExample);
        PageInfo<Role> rolePageInfo = new PageInfo<>(roles);
        pageDto.setTotal(rolePageInfo.getTotal());
        /**
         * 使用jdk8的stream()进行遍历与拷贝数据
         */
        List<RoleDto> roleDtoList = roles.stream().map(role -> {
            RoleDto roleDto = new RoleDto();
            BeanUtils.copyProperties(role, roleDto);
            return roleDto;
        }).collect(Collectors.toList());

        pageDto.setData(roleDtoList);

    }

    @Override
    public void saveAndUpdate(RoleDto roleDto) {
        Role role = new Role();
        BeanUtils.copyProperties(roleDto, role);
        if (!StringUtils.isEmpty(roleDto.getId())) {
            update(role);
        }else {
           save(role);
        }
    }

    @Override
    public int del(String id) {
        /**
         * z在删除之前要进行查询当前的用户是否与用户关联上
         *  1、有关联，则不能删除，返回对应的数据信息提示用户，当前角色不能删除
         *  2、没有关联， -》 可以进行删除
         *
         */
        RoleUserExample roleUserExample = new RoleUserExample();
        roleUserExample.createCriteria().andRoleIdEqualTo(id);
        List<RoleUser> roleUsers = roleUserMapper.selectByExample(roleUserExample);
        if (CollectionUtils.isEmpty(roleUsers)){
            int i = roleMapper.deleteByPrimaryKey(id);
            if (i<0){
                throw new ServerVideosException(BusinessExceptionEnum.NOT_STATUS);
            }
            return i;
        }else {
            return 0;
        }
    }

    @Override
    public List<String> getListResourceByRoleId(String roleId) {
        RoleResourceExample roleResourceExample = new RoleResourceExample();
        roleResourceExample.createCriteria().andRoleIdEqualTo(roleId);
        //返回了对应某个角色的数据信息
        List<RoleResource> roleResources = roleResourceMapper.selectByExample(roleResourceExample);
        ArrayList<String> strings = new ArrayList<>();
        roleResources.stream().forEach(x->{
            strings.add(x.getResourceId());
        });
        return strings;
    }

    /**
     *      * 1、将之前的菜单数据进行删除
     *      * 2、再次重新添加对应的勾选上的数据信息
     *      由于需要执行两种操作需要事务
     * @param roleDto
     * @return
     */
    @Transactional
    @Override
    public int getSaveResource(RoleDto roleDto) {
        int j=0;
        String id = roleDto.getId();
        List<String> resourcesIds = roleDto.getResourcesIds();
        /**
         * 先进行查询，查询了之后如有数据则删除-》再添加
         */
        //将之前的菜单数据进行删除
        RoleResourceExample roleResourceExample = new RoleResourceExample();
        roleResourceExample.createCriteria().andRoleIdEqualTo(id);
        int i = roleResourceMapper.deleteByExample(roleResourceExample);
        //删除成功
        if (!CollectionUtils.isEmpty(resourcesIds)){
            //进行添加对应的数据信息
            for (String resourcesId : resourcesIds) {
                RoleResource roleResource = new RoleResource();
                roleResource.setRoleId(id);
                roleResource.setResourceId(resourcesId);
                roleResource.setId(UUIDUtils.random32());
                int r=roleResourceMapper.insert(roleResource);
                if (r>0){
                    j=r;
                }
            }
        }else {
            j=i;
        }
        return j;
    }

    @Override
    public List<String> roleAndUserList(String roleId) {

        RoleUserExample roleUserExample = new RoleUserExample();
        roleUserExample.createCriteria().andRoleIdEqualTo(roleId);
        List<RoleUser> roleUsers = roleUserMapper.selectByExample(roleUserExample);

        ArrayList<String> strings = new ArrayList<>();

        //循环清洗数据
        roleUsers.stream().forEach(x->{
            strings.add(x.getUserId());
        });

        return strings;
    }

    /**
     * 1、删除之前匹配的角色和用户的关联数据
     * 2、在进行添加新用户和角色关系的数据信息
     * @param roleDto
     * @return
     */
    @Transactional
    @Override
    public int saveUser(RoleDto roleDto) {
        int i=0;
        String roleId = roleDto.getRoleId();
        List<String> userIds = roleDto.getUserIds();
        RoleUserExample roleUserExample = new RoleUserExample();
        roleUserExample.createCriteria().andRoleIdEqualTo(roleId);
        //删除之前的匹配的角色和用户的关联数据
        int j =roleUserMapper.deleteByExample(roleUserExample);
        if (!CollectionUtils.isEmpty(userIds)){//bug
            //在进行添加新用户和角色关系的数据信息
//            userIds.stream().forEach(x->{
//                RoleUser roleUser = new RoleUser();
//                roleUser.setRoleId(roleId);
//                roleUser.setUserId(x);
//                roleUser.setId(UUIDUtils.random32());
//                int insert = roleUserMapper.insert(roleUser);
//
//            });
            for (String userId : userIds) {
                RoleUser roleUser = new RoleUser();
                roleUser.setRoleId(roleId);
                roleUser.setUserId(userId);
                roleUser.setId(UUIDUtils.random32());
                int insert = roleUserMapper.insert(roleUser);
                if (insert>0){
                    i=insert;
                }
            }
        }else {
            i=j;
        }
        return i;
    }

    private  void save(Role  role){

        role.setId(UUIDUtils.random32());
        roleMapper.insert(role);
    }
    private  void update(Role  role){
        roleMapper.updateByPrimaryKey(role);
    }

}
