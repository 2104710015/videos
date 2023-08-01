package com.videos.course.server.service.impl;

import com.videos.course.server.domin.Course;
import com.videos.course.server.domin.CourseExample;
import com.videos.course.server.dto.CourseDto;
import com.videos.course.server.dto.PageDto;
import com.videos.course.server.mapper.CourseCategoryMapper;
import com.videos.course.server.mapper.CourseMapper;
import com.videos.course.server.service.CourseCategoryService;
import com.videos.course.server.service.CourseService;
import com.videos.course.server.utils.UUIDUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.videos.course.server.utils.ValidatorUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;



import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

/**
 * @author 隔壁_老陈
 * 代码风骚、效率恐怖、编译器深深鄙视智商。
 * 千里之外定位问题、瞬间修复依旧风骚……
 * @create 2021-04-11 23:24
 */
@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private CourseCategoryService courseCategoryService;

    @Override
    public  void getList(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
            //设置排序 为 倒序方式
        CourseExample courseExample = new CourseExample();
        if(!CollectionUtils.isEmpty(pageDto.getData())){
//            List<String> list=pageDto.getData();
//            String tid =list.get(0);
            String tid = (String) pageDto.getData().get(0);
            courseExample.createCriteria().andTeacherIdEqualTo(tid);
        }
                courseExample.setOrderByClause("sort asc");
        List<Course> courses = courseMapper.selectByExample(courseExample);
        PageInfo<Course> coursePageInfo = new PageInfo<>(courses);
        pageDto.setTotal(coursePageInfo.getTotal());
        /**
         * 使用jdk8的stream()进行遍历与拷贝数据
         */
        List<CourseDto> courseDtoList = courses.stream().map(course -> {
            CourseDto courseDto = new CourseDto();
            BeanUtils.copyProperties(course, courseDto);
            return courseDto;
        }).collect(Collectors.toList());

        pageDto.setData(courseDtoList);

    }

    @Override
    @Transactional
    public void saveAndUpdate(CourseDto courseDto) {
        Course course = new Course();
        BeanUtils.copyProperties(courseDto, course);
        /**
         * 对课程的操作 要么添加，要么删除
         */
        if (!StringUtils.isEmpty(courseDto.getId())) {
            update(course);
        }else {
           save(course);
        }
        String id=course.getId();
        ValidatorUtils.require(id,"课程编号");
        //对课程的分类信息进行保存=>对课程的分类表操作
        courseCategoryService.saveBatchs(id,courseDto.getCategorys());
    }

    @Override
    public void del(String id) {
        courseMapper.deleteByPrimaryKey(id);
    }

    private  void save(Course  course){
            LocalDateTime now = LocalDateTime.now();

                course.setCreatedAt(now);
                course.setUpdatedAt(now);
        course.setId(UUIDUtils.random32());
        courseMapper.insert(course);
    }
    private  void update(Course  course){
                course.setUpdatedAt(LocalDateTime.now());
        courseMapper.updateByPrimaryKey(course);
    }

}
