package com.videos.course.server.service;



import com.videos.course.server.dto.UpdateInfoDto;
import com.videos.course.server.dto.PageDto;

import java.util.List;

/**
 * @author 隔壁_老陈
 * 代码风骚、效率恐怖、编译器深深鄙视智商。
 * 千里之外定位问题、瞬间修复依旧风骚……
 * @create 2021-04-11 23:24
 */
public interface UpdateInfoService {
    public void getList(PageDto pageDto);
    public void saveAndUpdate(UpdateInfoDto updateInfoDto);

    public void del(String id);
}
