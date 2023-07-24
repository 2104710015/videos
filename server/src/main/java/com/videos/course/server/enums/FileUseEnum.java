package com.videos.course.server.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author 隔壁_老陈
 * 代码风骚、效率恐怖、编译器深深鄙视智商。
 * 千里之外定位问题、瞬间修复依旧风骚……
 * @create 2021-04-19 14:08
 */
@AllArgsConstructor
@Getter
public enum FileUseEnum {
    COURSE("C", "课程"), TEACHER("T", "讲师");
    private  String code;
    private  String desc;

    /**
     * 通过枚举code进行判断当前code值，并返回对应的code与vuale值
     * @param code   枚举的code值
     * @return
     */
    public static FileUseEnum getByCode(String code){
        FileUseEnum fileUseEnum = Arrays.stream(FileUseEnum.values()).map(e -> {
            return e;
        }).filter(e -> {
            if (StringUtils.isEmpty(code)) {
                return e.getCode().equals("C");
            }
            return e.getCode().equals(code);
        }).collect(Collectors.toList()).get(0);
        return  fileUseEnum;
    }


}
