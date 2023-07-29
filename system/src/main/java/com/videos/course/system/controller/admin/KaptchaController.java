package com.videos.course.system.controller.admin;


import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.videos.course.server.enums.BusinessExceptionEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Api(tags = "验证码模块")
@RestController
@RequestMapping("/admin/kaptcha")
@Slf4j
public class KaptchaController {
    //输出日志的服务名称
    public static final String BUSINESS_NAME="SYSTEM_NAME服务之(KaptchaController)";

    //引入对应的验证码的坐标

    @Qualifier("getDefaultKaptcha")
    @Autowired
    DefaultKaptcha defaultKaptcha;
    /**
     * 验证码逻辑
     *  调用了验证码接口
     *      ->返回验证码->进行存储到redis（有时间限制存储）
     *      ->登录进行验证需要从redis中取验证码
     *
     */
    @Autowired
    RedisTemplate redisTemplate;

    @ApiOperation(value = "验证信息")
    @GetMapping("/imageCode/{imagesToken}")
    public void imageCode(@PathVariable("imagesToken") String imagesToken,
                          HttpServletRequest request, HttpServletResponse response) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
             //生成一个随机的一个字符串->就是对应的验证码
            String text = defaultKaptcha.createText();
            //验证码存到redis中
            redisTemplate.opsForValue().set(imagesToken,text,3000, TimeUnit.SECONDS);
            //将验证码生成一个图片
            BufferedImage image = defaultKaptcha.createImage(text);

            ImageIO.write(image,"jpg",outputStream);
        } catch (IOException e) {
//            throw new RuntimeException(e);
                response.sendError(BusinessExceptionEnum.KAPTCHA_NOT_EXIST.getCode());
            return;
        }
        //定义response 进行输出验证码图片 字节的方式进行传输
        byte[] bytes = outputStream.toByteArray();
        response.setHeader("Cache-Control","no-cache");
        response.setHeader("Pragma","no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpg");
        ServletOutputStream outputStream1 = response.getOutputStream();
        outputStream1.write(bytes);
        outputStream1.flush();
        outputStream1.close();





    }


}
