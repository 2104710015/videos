package com.videos.course.server.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SmsUseEnum {

    REGISTER("R", "注册"),
    FORGET("F", "忘记密码");

    private String code;

    private String desc;

}
