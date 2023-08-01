package com.videos.course.server.mapper;

import com.videos.course.server.domin.UserStudent;
import com.videos.course.server.domin.UserStudentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserStudentMapper {
    long countByExample(UserStudentExample example);

    int deleteByExample(UserStudentExample example);

    int deleteByPrimaryKey(String id);

    int insert(UserStudent record);

    int insertSelective(UserStudent record);

    List<UserStudent> selectByExample(UserStudentExample example);

    UserStudent selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") UserStudent record, @Param("example") UserStudentExample example);

    int updateByExample(@Param("record") UserStudent record, @Param("example") UserStudentExample example);

    int updateByPrimaryKeySelective(UserStudent record);

    int updateByPrimaryKey(UserStudent record);
}