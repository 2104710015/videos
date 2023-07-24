package com.videos.course.server.utils;

import com.videos.course.server.exception.ValidatorException;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author 隔壁_老陈
 * 代码风骚、效率恐怖、编译器深深鄙视智商。
 * 千里之外定位问题、瞬间修复依旧风骚……
 * @create 2021-04-17 16:48
 */
public class ValidatorUtils {

    /**
     * 验证不能为空数据
     * @param ages   被验证的数据
     * @param message   提示用户信息
     */
    public static   void  require(Object ages ,String message){
        if (StringUtils.isEmpty(ages)) {
          throw new ValidatorException(message+"不能为空");
        }
    }

    /**
     * 进行验证集合不能空
     * @param ages  被验证的数据
     * @param message  提示用户信息
     */
    public static   void  require(List ages , String message){
        if (CollectionUtils.isEmpty(ages)) {
            throw new ValidatorException(message+"不能为空");
        }
    }
    /**
     * 长度数据的验证
     * @param ages   被验证的数据
     * @param message  提示用户的信息
     * @param min  最小长度
     * @param max  最大长度
     */
    public  static void  length(String ages ,String message,int min,int  max){
        //可放行，如视频
        if (StringUtils.isEmpty(ages)) {
            return;
        }
        int  length=0;
        if (!StringUtils.isEmpty(ages)) {
            length=ages.trim().length();
        }
        if (length > max || length < min) {
            throw new ValidatorException(message+"长度"+min+"~"+max+"之间");
        }
    }
}
