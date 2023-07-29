package com.videos.course.server.mapper;

import com.videos.course.server.domin.Resource;
import com.videos.course.server.domin.ResourceExample;
import java.util.List;

import com.videos.course.server.dto.ResourceDto;
import org.apache.ibatis.annotations.Param;

public interface ResourceMapper {
    long countByExample(ResourceExample example);

    int deleteByExample(ResourceExample example);

    int deleteByPrimaryKey(String id);

    int insert(Resource record);

    int insertSelective(Resource record);

    List<Resource> selectByExample(ResourceExample example);

    Resource selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Resource record, @Param("example") ResourceExample example);

    int updateByExample(@Param("record") Resource record, @Param("example") ResourceExample example);

    int updateByPrimaryKeySelective(Resource record);

    int updateByPrimaryKey(Resource record);

    /**
     * 通过账号的id进行查询对应的权限
     * @param id
     * @return
     */
    List<ResourceDto> findResourceByUsersId(@Param("id") String id);
}