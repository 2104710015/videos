package com.videos.course.system.controller.admin;

import com.alibaba.fastjson.JSON;
import com.videos.course.server.dto.LoginUsersDto;
import com.videos.course.server.dto.UserDto;
import com.videos.course.server.dto.PageDto;
import com.videos.course.server.enums.BusinessExceptionEnum;
import com.videos.course.server.service.UserService;
import com.videos.course.server.utils.UUIDUtils;
import com.videos.course.server.utils.ValidatorUtils;
import com.videos.course.server.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.concurrent.TimeUnit;

/**
 * @author 隔壁_老陈
 * 代码风骚、效率恐怖、编译器深深鄙视智商。
 * 千里之外定位问题、瞬间修复依旧风骚……
 * @create 2021-04-11 18:07
 */
@Api(tags = "User模块")
@RestController
@RequestMapping("/admin/user")
@Slf4j
public class UserController {
        //输出日志的服务名称
     public static final String BUSINESS_NAME="SYSTEM_NAME服务之(UserController)";
    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 账号登陆
     * @param userDto
     * @return
     */
    @ApiOperation(value = "后台系统的等入")
    @PostMapping("/login")
    public   ResponseVo  getLogin(@RequestBody UserDto  userDto){
        ResponseVo responseVo = new ResponseVo();
        //进行记录的log
        log.info("----后台用户进程登陆--->开始验证(验证码):{}--token:{}",userDto.getImageCode(),userDto.getCodeToKen());
        //进行判断验证码 时候为空
        ValidatorUtils.require(userDto.getCodeToKen(),"验证码");
        //获取redis中验证码
        String code = (String) redisTemplate.opsForValue().get(userDto.getCodeToKen());
        log.info("----正确的验证码:{}",code);
        //再次进行验证是否有查询到对应的验证码
        if(StringUtils.isEmpty(code)){
            //如true 就是没有查询到数据
            //验证码过期了
            responseVo.setBoo(false);
            responseVo.setCode(String.valueOf(BusinessExceptionEnum.KAPTCHA_NOT_LOSE.getCode()));
            responseVo.setMessage(BusinessExceptionEnum.KAPTCHA_NOT_LOSE.getDesc());
            return     responseVo;
        }
        /**
         *获取到了验证码有数据的情况下
         *    1.判断获取的验证码与传递的是否一致
         *       (不区别大小写)
         */
        if(!code.toLowerCase().equals(userDto.getImageCode().toLowerCase())){
            //如true 就是没有查询到数据
            //验证码 不对
            responseVo.setBoo(false);
            responseVo.setCode(String.valueOf(BusinessExceptionEnum.KAPTCHA_NOT_EROOR.getCode()));
            responseVo.setMessage(BusinessExceptionEnum.KAPTCHA_NOT_EROOR.getDesc());
            return     responseVo;
        }else{
            //验证码正确
             //1.删除redis中的验证码
            redisTemplate.delete(userDto.getCodeToKen());
        }
        /**
         *   进行到数据库中查询对于的账号
         *      1.然后查询的数据进行进行处理到redis使用token的方式
         *         1.2 每次请求的都要进行携带对应的toKen, 防止他人登陆之后使用对应的url进行调用其他接口
         * */
        LoginUsersDto lognUsersDto=userService.getLogin(userDto);//alt + 回车  快速创建方法

         //生成一个UUID,可以理解为tonken
        String toKen = UUIDUtils.random32();
        lognUsersDto.setToKen(toKen);
        //进程当前的这个对象存到到redis中
        redisTemplate.opsForValue().set(toKen, JSON.toJSONString(lognUsersDto),1200 , TimeUnit.SECONDS);
       //进行返回的数据信息



        responseVo.setCode(String.valueOf(BusinessExceptionEnum.OK_STATUS.getCode())).setMessage(BusinessExceptionEnum.OK_STATUS.getDesc())
                .setContent(lognUsersDto);


        return  responseVo;
    }

//    public static void main(String[] args) {
//        System.out.println("DigestUtils.md5DigestAsHex(\"test\".getBytes()) = " + DigestUtils.md5DigestAsHex("d242b1384ce5fdf0285b2796347c50d2".getBytes()));
//    }

    @ApiOperation(value = "账号的退出")
    @GetMapping("/logout/{token}")
    public ResponseVo logout(@PathVariable("token") String token){
        ResponseVo responseVo = new ResponseVo();
        //进行断言
        if(StringUtils.isEmpty(token)){
            responseVo.setCode(String.valueOf(BusinessExceptionEnum.USER_LOGIN_ERROR.getCode()))
                    .setMessage(BusinessExceptionEnum.USER_LOGIN_ERROR.getDesc())
                    .setBoo(false);
            return responseVo;
        }else {
            //要进行查询redis中是否有存在数据
            String userToken = (String) redisTemplate.opsForValue().get(token);
                if (!StringUtils.isEmpty(userToken)){
                    //进行清除redis中的信息
                    redisTemplate.delete(token);
                    log.info("用户退出，从redis中已经删除数据: {}",token);
                    responseVo.setCode(String.valueOf(BusinessExceptionEnum.OUT_STATUS.getCode()))
                            .setMessage(BusinessExceptionEnum.OUT_STATUS.getDesc());


                    return responseVo;
                }
                else {
                    //直接返回异常失败
                    responseVo.setCode(String.valueOf(BusinessExceptionEnum.USER_LOGIN_ERROR.getCode()))
                            .setMessage(BusinessExceptionEnum.USER_LOGIN_ERROR.getDesc());
                    return responseVo;
                }
        }

    }

    /**
     * 查询所有的数据信息
     * @param pageDto  分页的共用类
     * @return    返回共用的json数据vo模版格式
     */
    @ApiOperation(value = "查询所有的数据信息")
    @PostMapping("/list")
    public ResponseVo getList(@RequestBody PageDto pageDto){
        userService.getList(pageDto);

       ResponseVo responseVo = new ResponseVo();
       responseVo.setCode("200").setMessage("查询成功").setContent(pageDto);
        return  responseVo;
    }

    /**
     * 添加和修改的共用类
     * @param userDto  添加与修改的dto实体类
     * @return   返回共用的json数据vo模版格式
     */
    @ApiOperation(value = "添加和修改的共用类")
    @PostMapping("/saveAndUpdate")
    public ResponseVo saveAndUpdate(@RequestBody UserDto userDto){
        /**
         * 进行数据的长度和非空验证
         */

            ValidatorUtils.require(userDto.getLoginName(), "登陆名");
            ValidatorUtils.length(userDto.getLoginName(), "登陆名", 1, 50);
            ValidatorUtils.length(userDto.getName(), "昵称", 1, 50);
            ValidatorUtils.require(userDto.getPassword(), "密码");

        userService.saveAndUpdate(userDto);
        ResponseVo responseVo = new ResponseVo();
        responseVo.setCode("200").setMessage("操作成功").setContent(userDto);
        return  responseVo;
    }

    /**
     * 通过id编号进行 物理删除
     * @param id  删除的id编号
     * @return   返回共用的json数据vo模版格式
     */
    @ApiOperation(value = "通过id编号进行 物理删除")
    @RequestMapping("/del/{id}")
    public ResponseVo del(@PathVariable String id){


        userService.del(id);
        ResponseVo responseVo = new ResponseVo();
        responseVo.setCode("200").setMessage("操作成功");
        return  responseVo;
    }



}
