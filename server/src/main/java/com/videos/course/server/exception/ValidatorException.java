package com.videos.course.server.exception;

/**
 * @author 隔壁_老陈
 * 代码风骚、效率恐怖、编译器深深鄙视智商。
 * 千里之外定位问题、瞬间修复依旧风骚……
 * @create 2021-04-17 16:51
 */
public class ValidatorException  extends   RuntimeException {
    public ValidatorException() {
    }

    public ValidatorException(String message) {
        super(message);
    }
}
