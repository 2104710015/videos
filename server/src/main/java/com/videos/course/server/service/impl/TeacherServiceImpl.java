package com.videos.course.server.service.impl;

import com.videos.course.server.domin.Teacher;
import com.videos.course.server.domin.TeacherExample;
import com.videos.course.server.dto.TeacherDto;
import com.videos.course.server.dto.PageDto;
import com.videos.course.server.mapper.TeacherMapper;
import com.videos.course.server.service.TeacherService;
import com.videos.course.server.utils.CopyUtils;
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
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherMapper teacherMapper;
    @Override
    public  void getList(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
            //设置排序 为 倒序方式
        TeacherExample teacherExample = new TeacherExample();
        List<Teacher> teachers = teacherMapper.selectByExample(teacherExample);
        PageInfo<Teacher> teacherPageInfo = new PageInfo<>(teachers);
        pageDto.setTotal(teacherPageInfo.getTotal());
        /**
         * 使用jdk8的stream()进行遍历与拷贝数据
         */
        List<TeacherDto> teacherDtoList = teachers.stream().map(teacher -> {
            TeacherDto teacherDto = new TeacherDto();
            BeanUtils.copyProperties(teacher, teacherDto);
            return teacherDto;
        }).collect(Collectors.toList());

        pageDto.setData(teacherDtoList);

    }

    @Override
    public void saveAndUpdate(TeacherDto teacherDto) {
        Teacher teacher = new Teacher();
        BeanUtils.copyProperties(teacherDto, teacher);
        if (!StringUtils.isEmpty(teacherDto.getId())) {
            update(teacher);
        }else {
           save(teacher);
        }
    }

    @Override
    public void del(String id) {
        teacherMapper.deleteByPrimaryKey(id);
    }

    @Override
    public TeacherDto getByUsersId(String id) {
        Teacher teacher = teacherMapper.selectByUsersId(id);
        TeacherDto copy = CopyUtils.copy(teacher, TeacherDto.class);
        return copy;
    }

    private  void save(Teacher  teacher){

        teacher.setId(UUIDUtils.random32());
        teacherMapper.insert(teacher);
    }
    private  void update(Teacher  teacher){
        teacherMapper.updateByPrimaryKey(teacher);
    }

}
