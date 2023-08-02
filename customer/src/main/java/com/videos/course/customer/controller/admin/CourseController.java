package com.videos.course.customer.controller.admin;

import com.videos.course.server.dto.CourseCategoryDto;
import com.videos.course.server.dto.CourseDto;
import com.videos.course.server.dto.PageDto;
import com.videos.course.server.dto.SortDto;
import com.videos.course.server.enums.BusinessExceptionEnum;
import com.videos.course.server.service.CourseCategoryService;
import com.videos.course.server.service.CourseService;
import com.videos.course.server.utils.ValidatorUtils;
import com.videos.course.server.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

/**
 * @author 隔壁_老陈
 * 代码风骚、效率恐怖、编译器深深鄙视智商。
 * 千里之外定位问题、瞬间修复依旧风骚……
 * @create 2021-04-11 18:07
 */
@Api(tags = "Course模块")
@RestController
@RequestMapping("/admin/course")
@Slf4j
public class CourseController {
        //输出日志的服务名称
     public static final String BUSINESS_NAME="SYSTEM_NAME服务之(CourseController)";
    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseCategoryService courseCategoryService;

    /**
     * 查询所有的数据信息
     * @param pageDto  分页的共用类
     * @return    返回共用的json数据vo模版格式
     */
    @ApiOperation(value = "查询所有的数据信息")
    @PostMapping("/list")
    public ResponseVo getList(@RequestBody PageDto pageDto){
        courseService.getList(pageDto);

       ResponseVo responseVo = new ResponseVo();
       responseVo.setCode("200").setMessage("查询成功").setContent(pageDto);
        return  responseVo;
    }

    /**
     * 添加和修改的共用类
     * @param courseDto  添加与修改的dto实体类
     * @return   返回共用的json数据vo模版格式
     */
    @ApiOperation(value = "添加和修改的共用类")
    @PostMapping("/saveAndUpdate")
    public ResponseVo saveAndUpdate(@RequestBody CourseDto courseDto){
        /**
         * 进行数据的长度和非空验证
         */

            ValidatorUtils.require(courseDto.getName(), "名称");
            ValidatorUtils.length(courseDto.getName(), "名称", 1, 50);
            ValidatorUtils.length(courseDto.getSummary(), "概述", 1, 3000);
            ValidatorUtils.length(courseDto.getImage(), "封面", 1, 100);

        courseService.saveAndUpdate(courseDto);
        ResponseVo responseVo = new ResponseVo();
        responseVo.setCode("200").setMessage("操作成功").setContent(courseDto);
        return  responseVo;
    }
    @ApiOperation(value = "t通过id查询课程的分类")

    @GetMapping("/list-category/{courseId}")
    public ResponseVo getListCategory(@PathVariable("courseId") String courseId){
        ResponseVo responseVo = new ResponseVo();
        log.info("通过课程编号进行查询对应的分类信息{}",courseId);
        //断言
        ValidatorUtils.require(courseId,"课程编号");
        List<CourseCategoryDto> courseCategoryDtoList=courseCategoryService.getListCategory(courseId);

        responseVo.setCode("200").setMessage("操作成功").setContent(courseCategoryDtoList);

        return  responseVo;
    }

    @ApiOperation(value = "t通过id修改课程的排序")
    @PostMapping("/sort")
    public ResponseVo updateSort(@RequestBody SortDto sortDto){
        ResponseVo responseVo = new ResponseVo();
        int i=courseService.updateSort(sortDto);
        if (i>0){
            responseVo.setCode("200").setMessage("操作成功");
        }else {
            responseVo.setBoo(false)
                    .setCode(String.valueOf(BusinessExceptionEnum.NOT_STATUS.getCode()))
                    .setMessage(BusinessExceptionEnum.NOT_STATUS.getDesc());

        }
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


        courseService.del(id);
        ResponseVo responseVo = new ResponseVo();
        responseVo.setCode("200").setMessage("操作成功");
        return  responseVo;
    }



}
