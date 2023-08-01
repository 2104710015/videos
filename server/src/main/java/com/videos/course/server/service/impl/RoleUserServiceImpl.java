package com.videos.course.server.service.impl;

import com.videos.course.server.domin.RoleUser;
import com.videos.course.server.domin.RoleUserExample;
import com.videos.course.server.dto.RoleUserDto;
import com.videos.course.server.dto.PageDto;
import com.videos.course.server.mapper.RoleUserMapper;
import com.videos.course.server.service.RoleUserService;
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
public class RoleUserServiceImpl implements RoleUserService {
    @Autowired
    private RoleUserMapper roleUserMapper;
    @Override
    public  void getList(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
            //设置排序 为 倒序方式
        RoleUserExample roleUserExample = new RoleUserExample();
        List<RoleUser> roleUsers = roleUserMapper.selectByExample(roleUserExample);
        PageInfo<RoleUser> roleUserPageInfo = new PageInfo<>(roleUsers);
        pageDto.setTotal(roleUserPageInfo.getTotal());
        /**
         * 使用jdk8的stream()进行遍历与拷贝数据
         */
        List<RoleUserDto> roleUserDtoList = roleUsers.stream().map(roleUser -> {
            RoleUserDto roleUserDto = new RoleUserDto();
            BeanUtils.copyProperties(roleUser, roleUserDto);
            return roleUserDto;
        }).collect(Collectors.toList());

        pageDto.setData(roleUserDtoList);

    }

    @Override
    public void saveAndUpdate(RoleUserDto roleUserDto) {
        RoleUser roleUser = new RoleUser();
        BeanUtils.copyProperties(roleUserDto, roleUser);
        if (!StringUtils.isEmpty(roleUserDto.getId())) {
            update(roleUser);
        }else {
           save(roleUser);
        }
    }

    @Override
    public void del(String id) {
        roleUserMapper.deleteByPrimaryKey(id);
    }

    private  void save(RoleUser  roleUser){

        roleUser.setId(UUIDUtils.random32());
        roleUserMapper.insert(roleUser);
    }
    private  void update(RoleUser  roleUser){
        roleUserMapper.updateByPrimaryKey(roleUser);
    }

}
