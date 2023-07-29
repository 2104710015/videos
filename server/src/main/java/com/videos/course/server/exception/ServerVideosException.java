package com.videos.course.server.exception;

import com.videos.course.server.enums.BusinessExceptionEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author 隔壁_老陈
 * 代码风骚、效率恐怖、编译器深深鄙视智商。
 * 千里之外定位问题、瞬间修复依旧风骚……
 * @create 2021-04-17 16:51
 */
@NoArgsConstructor
@Getter
@Setter
public class ServerVideosException extends   RuntimeException {

    private BusinessExceptionEnum  code;

    public ServerVideosException(BusinessExceptionEnum  code) {
        super(code.getDesc());
        this.code=code;
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
