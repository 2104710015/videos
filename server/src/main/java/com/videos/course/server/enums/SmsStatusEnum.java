package com.videos.course.server.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SmsStatusEnum {

    USED("U", "已使用"),
    NOT_USED("N", "未使用");

    private String code;

    private String desc;


}
