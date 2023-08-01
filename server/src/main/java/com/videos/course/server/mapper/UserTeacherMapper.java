package com.videos.course.server.mapper;

import com.videos.course.server.domin.UserTeacher;
import com.videos.course.server.domin.UserTeacherExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserTeacherMapper {
    long countByExample(UserTeacherExample example);

    int deleteByExample(UserTeacherExample example);

    int deleteByPrimaryKey(String id);

    int insert(UserTeacher record);

    int insertSelective(UserTeacher record);

    List<UserTeacher> selectByExample(UserTeacherExample example);

    UserTeacher selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") UserTeacher record, @Param("example") UserTeacherExample example);

    int updateByExample(@Param("record") UserTeacher record, @Param("example") UserTeacherExample example);

    int updateByPrimaryKeySelective(UserTeacher record);

    int updateByPrimaryKey(UserTeacher record);
}