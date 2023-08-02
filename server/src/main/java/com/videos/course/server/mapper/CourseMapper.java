package com.videos.course.server.mapper;

import com.videos.course.server.domin.Course;
import com.videos.course.server.domin.CourseExample;
import java.util.List;

import com.videos.course.server.dto.SortDto;
import org.apache.ibatis.annotations.Param;

public interface CourseMapper {
    long countByExample(CourseExample example);

    int deleteByExample(CourseExample example);

    int deleteByPrimaryKey(String id);

    int insert(Course record);

    int insertSelective(Course record);

    List<Course> selectByExample(CourseExample example);

    Course selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Course record, @Param("example") CourseExample example);

    int updateByExample(@Param("record") Course record, @Param("example") CourseExample example);

    int updateByPrimaryKeySelective(Course record);

    int updateByPrimaryKey(Course record);

    int updateSortById(Course course);

    int updateSortForWord(SortDto sortDto);

    int updateSortBack(SortDto sortDto);
}