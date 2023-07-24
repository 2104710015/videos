package com.videos.course.server.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 隔壁_老陈
 * 代码风骚、效率恐怖、编译器深深鄙视智商。
 * 千里之外定位问题、瞬间修复依旧风骚……
 * @create 2021-04-19 10:06
 */
@AllArgsConstructor
@Getter
public enum YesNoEnum {
    YES("1","是"),
    NO("0","否");
    private String code;

    private String desc;
}
