package com.videos.course.system.controller.admin;

import com.videos.course.server.dto.RoleUserDto;
import com.videos.course.server.dto.PageDto;
import com.videos.course.server.service.RoleUserService;
import com.videos.course.server.utils.ValidatorUtils;
import com.videos.course.server.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author 隔壁_老陈
 * 代码风骚、效率恐怖、编译器深深鄙视智商。
 * 千里之外定位问题、瞬间修复依旧风骚……
 * @create 2021-04-11 18:07
 */
@Api(tags = "RoleUser模块")
@RestController
@RequestMapping("/admin/roleUser")
@Slf4j
public class RoleUserController {
        //输出日志的服务名称
     public static final String BUSINESS_NAME="SYSTEM_NAME服务之(RoleUserController)";
    @Autowired
    private RoleUserService roleUserService;

    /**
     * 查询所有的数据信息
     * @param pageDto  分页的共用类
     * @return    返回共用的json数据vo模版格式
     */
    @ApiOperation(value = "查询所有的数据信息")
    @PostMapping("/list")
    public ResponseVo getList(@RequestBody PageDto pageDto){
        roleUserService.getList(pageDto);

       ResponseVo responseVo = new ResponseVo();
       responseVo.setCode("200").setMessage("查询成功").setContent(pageDto);
        return  responseVo;
    }

    /**
     * 添加和修改的共用类
     * @param roleUserDto  添加与修改的dto实体类
     * @return   返回共用的json数据vo模版格式
     */
    @ApiOperation(value = "添加和修改的共用类")
    @PostMapping("/saveAndUpdate")
    public ResponseVo saveAndUpdate(@RequestBody RoleUserDto roleUserDto){
        /**
         * 进行数据的长度和非空验证
         */

            ValidatorUtils.require(roleUserDto.getRoleId(), "角色");
            ValidatorUtils.require(roleUserDto.getUserId(), "用户");

        roleUserService.saveAndUpdate(roleUserDto);
        ResponseVo responseVo = new ResponseVo();
        responseVo.setCode("200").setMessage("操作成功").setContent(roleUserDto);
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


        roleUserService.del(id);
        ResponseVo responseVo = new ResponseVo();
        responseVo.setCode("200").setMessage("操作成功");
        return  responseVo;
    }



}
