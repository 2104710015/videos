package com.videos.course.server.servcie;



import com.videos.course.server.dto.${Domain}Dto;
import com.videos.course.server.dto.PageDto;

import java.util.List;

/**
 * @author 隔壁_老陈
 * 代码风骚、效率恐怖、编译器深深鄙视智商。
 * 千里之外定位问题、瞬间修复依旧风骚……
 * @create 2021-04-11 23:24
 */
public interface ${Domain}Service {
    public void getList(PageDto pageDto);
    public void saveAndUpdate(${Domain}Dto ${domain}Dto);

    public void del(String id);
}