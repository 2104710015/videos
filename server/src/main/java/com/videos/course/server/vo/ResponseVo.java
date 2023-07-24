package com.videos.course.server.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author 隔壁_老陈
 * 代码风骚、效率恐怖、编译器深深鄙视智商。
 * 千里之外定位问题、瞬间修复依旧风骚……
 * @create 2021-04-16 14:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain=true)
public class ResponseVo<T> {
    //返回状态是否成功
    private Boolean boo = true;
    //返回信息的状态码
    private String code;
    //返回信息的描述
    private String message;
    //返回数据
    private T content;

    public ResponseVo(Boolean boo, String code, String message) {
        this.boo = boo;
        this.code = code;
        this.message = message;
    }
}
