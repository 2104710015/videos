package com.videos.course.server.mapper;

import com.videos.course.server.domin.Anno;
import com.videos.course.server.domin.AnnoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AnnoMapper {
    long countByExample(AnnoExample example);

    int deleteByExample(AnnoExample example);

    int deleteByPrimaryKey(String id);

    int insert(Anno record);

    int insertSelective(Anno record);

    List<Anno> selectByExampleWithBLOBs(AnnoExample example);

    List<Anno> selectByExample(AnnoExample example);

    Anno selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Anno record, @Param("example") AnnoExample example);

    int updateByExampleWithBLOBs(@Param("record") Anno record, @Param("example") AnnoExample example);

    int updateByExample(@Param("record") Anno record, @Param("example") AnnoExample example);

    int updateByPrimaryKeySelective(Anno record);

    int updateByPrimaryKeyWithBLOBs(Anno record);

    int updateByPrimaryKey(Anno record);
}