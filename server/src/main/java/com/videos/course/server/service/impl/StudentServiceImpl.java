package com.videos.course.server.service.impl;

import com.videos.course.server.domin.Student;
import com.videos.course.server.domin.StudentExample;
import com.videos.course.server.dto.StudentDto;
import com.videos.course.server.dto.PageDto;
import com.videos.course.server.mapper.StudentMapper;
import com.videos.course.server.service.StudentService;
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
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;
    @Override
    public  void getList(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
            //设置排序 为 倒序方式
        StudentExample studentExample = new StudentExample();
        List<Student> students = studentMapper.selectByExample(studentExample);
        PageInfo<Student> studentPageInfo = new PageInfo<>(students);
        pageDto.setTotal(studentPageInfo.getTotal());
        /**
         * 使用jdk8的stream()进行遍历与拷贝数据
         */
        List<StudentDto> studentDtoList = students.stream().map(student -> {
            StudentDto studentDto = new StudentDto();
            BeanUtils.copyProperties(student, studentDto);
            return studentDto;
        }).collect(Collectors.toList());

        pageDto.setData(studentDtoList);

    }

    @Override
    public void saveAndUpdate(StudentDto studentDto) {
        Student student = new Student();
        BeanUtils.copyProperties(studentDto, student);
        if (!StringUtils.isEmpty(studentDto.getId())) {
            update(student);
        }else {
           save(student);
        }
    }

    @Override
    public void del(String id) {
        studentMapper.deleteByPrimaryKey(id);
    }

    private  void save(Student  student){

        student.setId(UUIDUtils.random32());
        studentMapper.insert(student);
    }
    private  void update(Student  student){
        studentMapper.updateByPrimaryKey(student);
    }

}
