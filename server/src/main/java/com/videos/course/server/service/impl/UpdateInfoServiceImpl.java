package com.videos.course.server.service.impl;

import com.videos.course.server.domin.UpdateInfo;
import com.videos.course.server.domin.UpdateInfoExample;
import com.videos.course.server.dto.UpdateInfoDto;
import com.videos.course.server.dto.PageDto;
import com.videos.course.server.mapper.UpdateInfoMapper;
import com.videos.course.server.service.UpdateInfoService;
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
public class UpdateInfoServiceImpl implements UpdateInfoService {
    @Autowired
    private UpdateInfoMapper updateInfoMapper;
    @Override
    public  void getList(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
            //设置排序 为 倒序方式
        UpdateInfoExample updateInfoExample = new UpdateInfoExample();
        List<UpdateInfo> updateInfos = updateInfoMapper.selectByExampleWithBLOBs(updateInfoExample);
        PageInfo<UpdateInfo> updateInfoPageInfo = new PageInfo<>(updateInfos);
        pageDto.setTotal(updateInfoPageInfo.getTotal());
        /**
         * 使用jdk8的stream()进行遍历与拷贝数据
         */
        List<UpdateInfoDto> updateInfoDtoList = updateInfos.stream().map(updateInfo -> {
            UpdateInfoDto updateInfoDto = new UpdateInfoDto();
            BeanUtils.copyProperties(updateInfo, updateInfoDto);
            return updateInfoDto;
        }).collect(Collectors.toList());

        pageDto.setData(updateInfoDtoList);

    }

    @Override
    public void saveAndUpdate(UpdateInfoDto updateInfoDto) {
        UpdateInfo updateInfo = new UpdateInfo();
        BeanUtils.copyProperties(updateInfoDto, updateInfo);
        if (!StringUtils.isEmpty(updateInfoDto.getId())) {
            update(updateInfo);
        }else {
           save(updateInfo);
        }
    }

    @Override
    public void del(String id) {
        updateInfoMapper.deleteByPrimaryKey(id);
    }

    private  void save(UpdateInfo  updateInfo){

        updateInfo.setId(UUIDUtils.random32());
        updateInfoMapper.insert(updateInfo);
    }
    private  void update(UpdateInfo  updateInfo){
        updateInfoMapper.updateByPrimaryKey(updateInfo);
    }

}
