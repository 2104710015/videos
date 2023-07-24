package com.videos.course.server.dto;

import lombok.Data;

import java.util.List;

/**
 * @author 隔壁_老陈
 * 代码风骚、效率恐怖、编译器深深鄙视智商。
 * 千里之外定位问题、瞬间修复依旧风骚……
 * @create 2021-04-16 10:14
 */
@Data
public class PageDto<T> {
 private int  page;
 private   int  size;
 private  long  total;
 private List<T> data;
}
