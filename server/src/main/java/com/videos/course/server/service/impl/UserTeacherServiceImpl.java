package com.videos.course.server.service.impl;

import com.videos.course.server.domin.UserTeacher;
import com.videos.course.server.domin.UserTeacherExample;
import com.videos.course.server.dto.UserTeacherDto;
import com.videos.course.server.dto.PageDto;
import com.videos.course.server.mapper.UserTeacherMapper;
import com.videos.course.server.service.UserTeacherService;
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
public class UserTeacherServiceImpl implements UserTeacherService {
    @Autowired
    private UserTeacherMapper userTeacherMapper;
    @Override
    public  void getList(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
            //设置排序 为 倒序方式
        UserTeacherExample userTeacherExample = new UserTeacherExample();
        List<UserTeacher> userTeachers = userTeacherMapper.selectByExample(userTeacherExample);
        PageInfo<UserTeacher> userTeacherPageInfo = new PageInfo<>(userTeachers);
        pageDto.setTotal(userTeacherPageInfo.getTotal());
        /**
         * 使用jdk8的stream()进行遍历与拷贝数据
         */
        List<UserTeacherDto> userTeacherDtoList = userTeachers.stream().map(userTeacher -> {
            UserTeacherDto userTeacherDto = new UserTeacherDto();
            BeanUtils.copyProperties(userTeacher, userTeacherDto);
            return userTeacherDto;
        }).collect(Collectors.toList());

        pageDto.setData(userTeacherDtoList);

    }

    @Override
    public void saveAndUpdate(UserTeacherDto userTeacherDto) {
        UserTeacher userTeacher = new UserTeacher();
        BeanUtils.copyProperties(userTeacherDto, userTeacher);
        if (!StringUtils.isEmpty(userTeacherDto.getId())) {
            update(userTeacher);
        }else {
           save(userTeacher);
        }
    }

    @Override
    public void del(String id) {
        userTeacherMapper.deleteByPrimaryKey(id);
    }

    private  void save(UserTeacher  userTeacher){

        userTeacher.setId(UUIDUtils.random32());
        userTeacherMapper.insert(userTeacher);
    }
    private  void update(UserTeacher  userTeacher){
        userTeacherMapper.updateByPrimaryKey(userTeacher);
    }

}
