package com.videos.course.server.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 隔壁_老陈
 * 代码风骚、效率恐怖、编译器深深鄙视智商。
 * 千里之外定位问题、瞬间修复依旧风骚……
 * @create 2021-04-19 14:07
 */
@AllArgsConstructor
@Getter
public enum  CourseLevelEnum {
    ONE("1", "初级"),TWO("2", "中级"),THREE("3", "高级");
    private  String code;
    private  String desc;
    
}
