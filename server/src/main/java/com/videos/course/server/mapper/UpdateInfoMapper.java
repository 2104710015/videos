package com.videos.course.server.mapper;

import com.videos.course.server.domin.UpdateInfo;
import com.videos.course.server.domin.UpdateInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UpdateInfoMapper {
    long countByExample(UpdateInfoExample example);

    int deleteByExample(UpdateInfoExample example);

    int deleteByPrimaryKey(String id);

    int insert(UpdateInfo record);

    int insertSelective(UpdateInfo record);

    List<UpdateInfo> selectByExampleWithBLOBs(UpdateInfoExample example);

    List<UpdateInfo> selectByExample(UpdateInfoExample example);

    UpdateInfo selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") UpdateInfo record, @Param("example") UpdateInfoExample example);

    int updateByExampleWithBLOBs(@Param("record") UpdateInfo record, @Param("example") UpdateInfoExample example);

    int updateByExample(@Param("record") UpdateInfo record, @Param("example") UpdateInfoExample example);

    int updateByPrimaryKeySelective(UpdateInfo record);

    int updateByPrimaryKeyWithBLOBs(UpdateInfo record);

    int updateByPrimaryKey(UpdateInfo record);
}