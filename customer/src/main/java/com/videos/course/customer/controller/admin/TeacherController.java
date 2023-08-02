package com.videos.course.customer.controller.admin;

import com.videos.course.server.domin.Teacher;
import com.videos.course.server.dto.TeacherDto;
import com.videos.course.server.dto.PageDto;
import com.videos.course.server.enums.BusinessExceptionEnum;
import com.videos.course.server.service.TeacherService;
import com.videos.course.server.utils.ValidatorUtils;
import com.videos.course.server.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author 隔壁_老陈
 * 代码风骚、效率恐怖、编译器深深鄙视智商。
 * 千里之外定位问题、瞬间修复依旧风骚……
 * @create 2021-04-11 18:07
 */
@Api(tags = "Teacher模块")
@RestController
@RequestMapping("/admin/teacher")
@Slf4j
public class TeacherController {
        //输出日志的服务名称
     public static final String BUSINESS_NAME="CUSTOMER_NAME服务之(TeacherController)";
    @Autowired
    private TeacherService teacherService;

    /**
     * 查询所有的数据信息
     * @param pageDto  分页的共用类
     * @return    返回共用的json数据vo模版格式
     */
    @ApiOperation(value = "查询所有的数据信息")
    @PostMapping("/list")
    public ResponseVo getList(@RequestBody PageDto pageDto){
        teacherService.getList(pageDto);

       ResponseVo responseVo = new ResponseVo();
       responseVo.setCode("200").setMessage("查询成功").setContent(pageDto);
        return  responseVo;
    }

    @ApiOperation(value = "通过教师id查询用户id")
    @GetMapping("/selectById/{teacherId}")
    public ResponseVo getSelectById(@PathVariable("teacherId") String teacherId){
        ResponseVo responseVo = new ResponseVo();
        TeacherDto teacherDto=teacherService.getSelectById(teacherId);
        String userid=teacherDto.getUserid();
        if (userid!=null){
            responseVo
                    .setCode(String.valueOf(BusinessExceptionEnum.OK_STATUS))
                    .setMessage(BusinessExceptionEnum.OK_STATUS.getDesc())
                    .setContent(userid);
        }else {
            responseVo
                    .setBoo(false)
                    .setCode(String.valueOf(BusinessExceptionEnum.NOT_STATUS))
                    .setMessage(BusinessExceptionEnum.NOT_STATUS.getDesc());
        }
        return  responseVo;
    }

    @ApiOperation(value = "给某个讲师添加或修改对应的用户")
    @PostMapping("/saveUser")
    public ResponseVo getSaveUser(@RequestBody TeacherDto teacherDto){
        ResponseVo responseVo = new ResponseVo();
        log.info("对某个讲师,{}修改对应的用户,{}",teacherDto.getId(),teacherDto.getUserid());
        ValidatorUtils.require(teacherDto.getId(),"讲师编号");
        teacherService.saveUser(teacherDto);
        responseVo
                .setCode(String.valueOf(BusinessExceptionEnum.OK_STATUS))
                .setMessage(BusinessExceptionEnum.OK_STATUS.getDesc());

        return  responseVo;
    }

    @ApiOperation(value = "通过教师id查询所有的用户数据信息")
    @GetMapping("/getByUsersId/{id}")
    public ResponseVo getByUsersId(@PathVariable("id") String id){
        ResponseVo responseVo = new ResponseVo();
        //进行断言
        ValidatorUtils.require(id,"教师编号");
        TeacherDto teacherDto=teacherService.getByUsersId(id);
        if (teacherDto!=null){
            responseVo
                    .setCode(String.valueOf(BusinessExceptionEnum.OK_STATUS))
                    .setMessage(BusinessExceptionEnum.OK_STATUS.getDesc())
                    .setContent(teacherDto);
        }else {
            responseVo
                    .setBoo(false)
                    .setCode(String.valueOf(BusinessExceptionEnum.NOT_STATUS))
                    .setMessage(BusinessExceptionEnum.NOT_STATUS.getDesc());
        }

        return  responseVo;
    }

    /**
     * 添加和修改的共用类
     * @param teacherDto  添加与修改的dto实体类
     * @return   返回共用的json数据vo模版格式
     */
    @ApiOperation(value = "添加和修改的共用类")
    @PostMapping("/saveAndUpdate")
    public ResponseVo saveAndUpdate(@RequestBody TeacherDto teacherDto){
        /**
         * 进行数据的长度和非空验证
         */

            ValidatorUtils.require(teacherDto.getName(), "姓名");
            ValidatorUtils.length(teacherDto.getName(), "姓名", 1, 50);
            ValidatorUtils.length(teacherDto.getNickname(), "昵称", 1, 50);
            ValidatorUtils.length(teacherDto.getImage(), "头像", 1, 100);
            ValidatorUtils.length(teacherDto.getPosition(), "职位", 1, 50);
            ValidatorUtils.length(teacherDto.getMotto(), "座右铭", 1, 50);
            ValidatorUtils.length(teacherDto.getIntro(), "简介", 1, 500);

        teacherService.saveAndUpdate(teacherDto);
        ResponseVo responseVo = new ResponseVo();
        responseVo.setCode("200").setMessage("操作成功").setContent(teacherDto);
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


        teacherService.del(id);
        ResponseVo responseVo = new ResponseVo();
        responseVo.setCode("200").setMessage("操作成功");
        return  responseVo;
    }



}
