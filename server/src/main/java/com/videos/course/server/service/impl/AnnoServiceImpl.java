package com.videos.course.server.service.impl;

import com.videos.course.server.domin.Anno;
import com.videos.course.server.domin.AnnoExample;
import com.videos.course.server.dto.AnnoDto;
import com.videos.course.server.dto.PageDto;
import com.videos.course.server.mapper.AnnoMapper;
import com.videos.course.server.service.AnnoService;
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
public class AnnoServiceImpl implements AnnoService {
    @Autowired
    private AnnoMapper annoMapper;
    @Override
    public  void getList(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
            //设置排序 为 倒序方式
        AnnoExample annoExample = new AnnoExample();
        List<Anno> annos = annoMapper.selectByExampleWithBLOBs(annoExample);
        PageInfo<Anno> annoPageInfo = new PageInfo<>(annos);
        pageDto.setTotal(annoPageInfo.getTotal());
        /**
         * 使用jdk8的stream()进行遍历与拷贝数据
         */
        List<AnnoDto> annoDtoList = annos.stream().map(anno -> {
            AnnoDto annoDto = new AnnoDto();
            BeanUtils.copyProperties(anno, annoDto);
            return annoDto;
        }).collect(Collectors.toList());

        pageDto.setData(annoDtoList);

    }

    @Override
    public void saveAndUpdate(AnnoDto annoDto) {
        Anno anno = new Anno();
        BeanUtils.copyProperties(annoDto, anno);
        if (!StringUtils.isEmpty(annoDto.getId())) {
            update(anno);
        }else {
           save(anno);
        }
    }

    @Override
    public void del(String id) {
        annoMapper.deleteByPrimaryKey(id);
    }

    private  void save(Anno  anno){

        anno.setId(UUIDUtils.random32());
        annoMapper.insert(anno);
    }
    private  void update(Anno  anno){
        annoMapper.updateByPrimaryKey(anno);
    }

}
