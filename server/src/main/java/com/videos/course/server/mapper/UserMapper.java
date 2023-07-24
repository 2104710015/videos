package com.videos.course.server.mapper;

import com.videos.course.server.domin.User;
import com.videos.course.server.domin.UserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    long countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int  updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int deleteByPrimaryKey(@Param("id")String id);

    int updateByPrimaryKey(User user);
}