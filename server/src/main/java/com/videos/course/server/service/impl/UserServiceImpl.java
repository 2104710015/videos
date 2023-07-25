package com.videos.course.server.service.impl;

import com.videos.course.server.domin.User;
import com.videos.course.server.domin.UserExample;
import com.videos.course.server.dto.UserDto;
import com.videos.course.server.dto.PageDto;
import com.videos.course.server.mapper.UserMapper;
import com.videos.course.server.service.UserService;
import com.videos.course.server.utils.UUIDUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;



import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 隔壁_老陈
 * 代码风骚、效率恐怖、编译器深深鄙视智商。
 * 千里之外定位问题、瞬间修复依旧风骚……
 * @create 2021-04-11 23:24
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public  void getList(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
            //设置排序 为 倒序方式
        UserExample userExample = new UserExample();
        List<User> users = userMapper.selectByExample(userExample);
        PageInfo<User> userPageInfo = new PageInfo<>(users);
        pageDto.setTotal(userPageInfo.getTotal());
        /**
         * 使用jdk8的stream()进行遍历与拷贝数据
         */
        List<UserDto> userDtoList = users.stream().map(user -> {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(user, userDto);
            return userDto;
        }).collect(Collectors.toList());

        pageDto.setData(userDtoList);

    }

    @Override
    public void saveAndUpdate(UserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        if (!StringUtils.isEmpty(userDto.getId())) {
            update(user);
        }else {
           save(user);
        }
    }

    @Override
    public void del(String id) {
        userMapper.deleteByPrimaryKey(id);
    }

    private  void save(User  user){

        user.setId(UUIDUtils.random32());
        userMapper.insert(user);
    }
    private  void update(User  user){
        userMapper.updateByPrimaryKey(user);
    }

}
