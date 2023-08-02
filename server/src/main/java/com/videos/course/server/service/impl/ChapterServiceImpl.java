package com.videos.course.server.service.impl;

import com.videos.course.server.domin.Chapter;
import com.videos.course.server.domin.ChapterExample;
import com.videos.course.server.dto.ChapterDto;
import com.videos.course.server.dto.PageDto;
import com.videos.course.server.mapper.ChapterMapper;
import com.videos.course.server.service.ChapterService;
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
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    private ChapterMapper chapterMapper;
    @Override
    public  void getList(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
            //设置排序 为 倒序方式
        ChapterExample chapterExample = new ChapterExample();
        List<Chapter> chapters = chapterMapper.selectByExample(chapterExample);
        PageInfo<Chapter> chapterPageInfo = new PageInfo<>(chapters);
        pageDto.setTotal(chapterPageInfo.getTotal());
        /**
         * 使用jdk8的stream()进行遍历与拷贝数据
         */
        List<ChapterDto> chapterDtoList = chapters.stream().map(chapter -> {
            ChapterDto chapterDto = new ChapterDto();
            BeanUtils.copyProperties(chapter, chapterDto);
            return chapterDto;
        }).collect(Collectors.toList());

        pageDto.setData(chapterDtoList);

    }

    @Override
    public void saveAndUpdate(ChapterDto chapterDto) {
        Chapter chapter = new Chapter();
        BeanUtils.copyProperties(chapterDto, chapter);
        if (!StringUtils.isEmpty(chapterDto.getId())) {
            update(chapter);
        }else {
           save(chapter);
        }
    }

    @Override
    public void del(String id) {
        chapterMapper.deleteByPrimaryKey(id);
    }

    private  void save(Chapter  chapter){

        chapter.setId(UUIDUtils.random32());
        chapterMapper.insert(chapter);
    }
    private  void update(Chapter  chapter){
        chapterMapper.updateByPrimaryKey(chapter);
    }

}
