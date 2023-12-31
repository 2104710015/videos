package com.videos.course.server.service.impl;

import com.videos.course.server.domin.CategoryExample;
import com.videos.course.server.domin.CourseCategory;
import com.videos.course.server.domin.CourseCategoryExample;
import com.videos.course.server.dto.CategoryDto;
import com.videos.course.server.dto.CourseCategoryDto;
import com.videos.course.server.dto.PageDto;
import com.videos.course.server.mapper.CourseCategoryMapper;
import com.videos.course.server.service.CourseCategoryService;
import com.videos.course.server.utils.UUIDUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;



import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 隔壁_老陈
 * 代码风骚、效率恐怖、编译器深深鄙视智商。
 * 千里之外定位问题、瞬间修复依旧风骚……
 * @create 2021-04-11 23:24
 */
@Slf4j
@Service
public class CourseCategoryServiceImpl implements CourseCategoryService {
    @Autowired
    private CourseCategoryMapper courseCategoryMapper;
    @Override
    public  void getList(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
            //设置排序 为 倒序方式
        CourseCategoryExample courseCategoryExample = new CourseCategoryExample();
        List<CourseCategory> courseCategorys = courseCategoryMapper.selectByExample(courseCategoryExample);
        PageInfo<CourseCategory> courseCategoryPageInfo = new PageInfo<>(courseCategorys);
        pageDto.setTotal(courseCategoryPageInfo.getTotal());
        /**
         * 使用jdk8的stream()进行遍历与拷贝数据
         */
        List<CourseCategoryDto> courseCategoryDtoList = courseCategorys.stream().map(courseCategory -> {
            CourseCategoryDto courseCategoryDto = new CourseCategoryDto();
            BeanUtils.copyProperties(courseCategory, courseCategoryDto);
            return courseCategoryDto;
        }).collect(Collectors.toList());

        pageDto.setData(courseCategoryDtoList);

    }

    @Override
    public void saveAndUpdate(CourseCategoryDto courseCategoryDto) {
        CourseCategory courseCategory = new CourseCategory();
        BeanUtils.copyProperties(courseCategoryDto, courseCategory);
        if (!StringUtils.isEmpty(courseCategoryDto.getId())) {
            update(courseCategory);
        }else {
           save(courseCategory);
        }
    }

    @Override
    public void del(String id) {
        courseCategoryMapper.deleteByPrimaryKey(id);
    }

    /**
     * 通过课程编号进行保存
     *
     */

    @Transactional
    @Override
    public int saveBatchs(String id, List<CategoryDto> categorys) {
        log.info("开始保存课程：{}的分类",id,categorys);
        //进行删除之前的课程对应的分类
        CourseCategoryExample courseCategoryExample = new CourseCategoryExample();
        courseCategoryExample.createCriteria().andCourseIdEqualTo(id);
        courseCategoryMapper.deleteByExample(courseCategoryExample);
        //进行添加新课程的分类
        categorys.stream().forEach(x->{
            CourseCategory courseCategory = new CourseCategory();
            courseCategory.setCourseId(id);
            courseCategory.setCategoryId(x.getId());
            save(courseCategory);
        });


        return 0;
    }

    @Override
    public List<CourseCategoryDto> getListCategory(String courseId) {
        CourseCategoryExample courseCategoryExample = new CourseCategoryExample();
        courseCategoryExample.createCriteria().andCourseIdEqualTo(courseId);
        List<CourseCategory> courseCategories = courseCategoryMapper.selectByExample(courseCategoryExample);
        //转换对象
        List<CourseCategoryDto> collect = courseCategories.stream().map(x -> {
            CourseCategoryDto courseCategoryDto = new CourseCategoryDto();
            BeanUtils.copyProperties(x, courseCategoryDto);
            return courseCategoryDto;
        }).collect(Collectors.toList());

        return collect;
    }

    private  void save(CourseCategory  courseCategory){

        courseCategory.setId(UUIDUtils.random32());
        courseCategoryMapper.insert(courseCategory);
    }
    private  void update(CourseCategory  courseCategory){
        courseCategoryMapper.updateByPrimaryKey(courseCategory);
    }

}
