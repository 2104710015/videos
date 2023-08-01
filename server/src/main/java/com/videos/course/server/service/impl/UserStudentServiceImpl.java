package com.videos.course.server.service.impl;

import com.videos.course.server.domin.UserStudent;
import com.videos.course.server.domin.UserStudentExample;
import com.videos.course.server.dto.UserStudentDto;
import com.videos.course.server.dto.PageDto;
import com.videos.course.server.mapper.UserStudentMapper;
import com.videos.course.server.service.UserStudentService;
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
public class UserStudentServiceImpl implements UserStudentService {
    @Autowired
    private UserStudentMapper userStudentMapper;
    @Override
    public  void getList(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
            //设置排序 为 倒序方式
        UserStudentExample userStudentExample = new UserStudentExample();
        List<UserStudent> userStudents = userStudentMapper.selectByExample(userStudentExample);
        PageInfo<UserStudent> userStudentPageInfo = new PageInfo<>(userStudents);
        pageDto.setTotal(userStudentPageInfo.getTotal());
        /**
         * 使用jdk8的stream()进行遍历与拷贝数据
         */
        List<UserStudentDto> userStudentDtoList = userStudents.stream().map(userStudent -> {
            UserStudentDto userStudentDto = new UserStudentDto();
            BeanUtils.copyProperties(userStudent, userStudentDto);
            return userStudentDto;
        }).collect(Collectors.toList());

        pageDto.setData(userStudentDtoList);

    }

    @Override
    public void saveAndUpdate(UserStudentDto userStudentDto) {
        UserStudent userStudent = new UserStudent();
        BeanUtils.copyProperties(userStudentDto, userStudent);
        if (!StringUtils.isEmpty(userStudentDto.getId())) {
            update(userStudent);
        }else {
           save(userStudent);
        }
    }

    @Override
    public void del(String id) {
        userStudentMapper.deleteByPrimaryKey(id);
    }

    private  void save(UserStudent  userStudent){

        userStudent.setId(UUIDUtils.random32());
        userStudentMapper.insert(userStudent);
    }
    private  void update(UserStudent  userStudent){
        userStudentMapper.updateByPrimaryKey(userStudent);
    }

}
