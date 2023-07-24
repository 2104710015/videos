package com.videos.course.server.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 隔壁_老陈
 * 代码风骚、效率恐怖、编译器深深鄙视智商。
 * 千里之外定位问题、瞬间修复依旧风骚……
 * @create 2021-04-27 14:19
 */
@AllArgsConstructor
@Getter
public enum  BusinessExceptionEnum {
    USER_LOGIN_NAME(222,"登陆名称已经存在了"),
    USER_LOGIN_ERROR(111,"账号与密码不正确"),
    MOBILE_CODE_TOO_FREQUENT(100,"短信请求过于频繁"),
    MOBILE_CODE_ERROR(101,"短信验证码不正确"),
    MOBILE_CODE_EXPIRED(102,"短信验证码不存在或已过期，请重新发送短信"),
    MEMBER_NOT_EXIST(000,"会员不存在"),
    ;
    private  Integer   code;
    private  String    desc;



}
