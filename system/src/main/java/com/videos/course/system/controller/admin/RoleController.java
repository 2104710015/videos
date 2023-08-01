package com.videos.course.system.controller.admin;

import com.videos.course.server.dto.RoleDto;
import com.videos.course.server.dto.PageDto;
import com.videos.course.server.enums.BusinessExceptionEnum;
import com.videos.course.server.service.RoleService;
import com.videos.course.server.utils.ValidatorUtils;
import com.videos.course.server.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
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
@Api(tags = "Role模块")
@RestController
@RequestMapping("/admin/role")
@Slf4j
public class RoleController {
        //输出日志的服务名称
     public static final String BUSINESS_NAME="SYSTEM_NAME服务之(RoleController)";
    @Autowired
    private RoleService roleService;

    /**
     * 查询所有的数据信息
     * @param pageDto  分页的共用类
     * @return    返回共用的json数据vo模版格式
     */
    @ApiOperation(value = "查询所有的数据信息")
    @PostMapping("/list")
    public ResponseVo getList(@RequestBody PageDto pageDto){
        roleService.getList(pageDto);

       ResponseVo responseVo = new ResponseVo();
       responseVo.setCode("200").setMessage("查询成功").setContent(pageDto);
        return  responseVo;
    }


    @ApiOperation(value = "通过角色的编号查询对应的权限菜单")
    @GetMapping("/listResourceByRoleId/{roleId}")
    public ResponseVo getListResourceByRoleId(@PathVariable("roleId") String roleId){
        ResponseVo responseVo = new ResponseVo();
        //验证
        ValidatorUtils.require(roleId,"角色编号");
        log.info("获取到查询某个的角色的id编号：{}",roleId);
        //调研service层
        List<String> roleResourceList= roleService.getListResourceByRoleId(roleId);

        responseVo.setCode(String.valueOf(BusinessExceptionEnum.OK_STATUS.getCode()))
                .setMessage(BusinessExceptionEnum.OK_STATUS.getDesc()).setContent(roleResourceList);
        return responseVo;
    }


    /**
     * 添加和修改的共用类
     * @param roleDto  添加与修改的dto实体类
     * @return   返回共用的json数据vo模版格式
     */
    @ApiOperation(value = "添加和修改的共用类")
    @PostMapping("/saveAndUpdate")
    public ResponseVo saveAndUpdate(@RequestBody RoleDto roleDto){
        /**
         * 进行数据的长度和非空验证
         */

            ValidatorUtils.require(roleDto.getName(), "角色");
            ValidatorUtils.length(roleDto.getName(), "角色", 1, 50);
            ValidatorUtils.require(roleDto.getDesc(), "描述");
            ValidatorUtils.length(roleDto.getDesc(), "描述", 1, 100);

        roleService.saveAndUpdate(roleDto);
        ResponseVo responseVo = new ResponseVo();
        responseVo.setCode("200").setMessage("操作成功").setContent(roleDto);
        return  responseVo;
    }

    /**
     * 进行修改角色对应的菜单的数据信息
     * 1、将之前的菜单数据进行删除
     * 2、再次重新添加对应的勾选上的数据信息
     * @return
     */
    @ApiOperation(value = "添加修改角色对应的菜单节点")
    @PostMapping("/saveResource")
    public ResponseVo getSaveResource(@RequestBody RoleDto roleDto){
        ResponseVo responseVo = new ResponseVo();
        log.info("当前的添加的角色编号：{ } 菜单节点资源",roleDto.getId(),roleDto.getResourcesIds());
        //进行断言
        ValidatorUtils.require(roleDto.getId(),"角色编号");
        ValidatorUtils.require(roleDto.getResourcesIds(),"菜单节点资源");
         int i=roleService.getSaveResource(roleDto);
         if (i>0){
             responseVo
                     .setCode(String.valueOf(BusinessExceptionEnum.OK_STATUS))
                     .setMessage(BusinessExceptionEnum.OK_STATUS.getDesc())
                     .setContent(roleDto);
         }else {
             responseVo
                     .setBoo(false)
                     .setCode(String.valueOf(BusinessExceptionEnum.NOT_STATUS))
                     .setMessage(BusinessExceptionEnum.NOT_STATUS.getDesc())
                     .setContent(roleDto);

         }
        return  responseVo;
    }

    /**
     * 通过角色id查询对应角色的用户信息
     * @param roleId
     * @return
     */
    @ApiOperation(value = "通过角色id查询对应角色的用户信息")
    @GetMapping("/roleAndUserList/{roleId}")
    public ResponseVo getRoleAndUserList(@PathVariable("roleId") String roleId){
        ResponseVo responseVo = new ResponseVo();
        log.info("通过角色编号:{},查询对应角色的用户信息",roleId);
        //进行断言
        ValidatorUtils.require(roleId,"角色编号");

        List<String> userList=roleService.roleAndUserList(roleId);
        if (!CollectionUtils.isEmpty(userList)){
            responseVo
                    .setCode(String.valueOf(BusinessExceptionEnum.OK_STATUS))
                    .setMessage(BusinessExceptionEnum.OK_STATUS.getDesc())
                    .setContent(userList);
        }else {
            responseVo
                    .setBoo(false)
                    .setCode(String.valueOf(BusinessExceptionEnum.NOT_STATUS))
                    .setMessage(BusinessExceptionEnum.NOT_STATUS.getDesc())
                    .setContent(userList);
        }
        return responseVo;
    }

    /**
     * 给某个用户添加对应的角色
     * @return
     */
    @ApiOperation(value = "给某个用户添加对应的角色")
    @PostMapping("/saveUser")
    public ResponseVo saveUser(@RequestBody RoleDto roleDto){
        ResponseVo responseVo = new ResponseVo();
        log.info("对某个角色,{}、修改对应的用户,{}",roleDto.getRoleId(),roleDto.getUserIds());
        ValidatorUtils.require(roleDto.getRoleId(),"角色编号");
//        ValidatorUtils.require(roleDto.getUserIds(),"用户编号");
        int i=roleService.saveUser(roleDto);
        if (i>0){
            responseVo
                    .setCode(String.valueOf(BusinessExceptionEnum.OK_STATUS))
                    .setMessage(BusinessExceptionEnum.OK_STATUS.getDesc())
                    .setContent(roleDto);
        }else {
            responseVo
                    .setBoo(false)
                    .setCode(String.valueOf(BusinessExceptionEnum.NOT_STATUS))
                    .setMessage(BusinessExceptionEnum.NOT_STATUS.getDesc())
                    .setContent(roleDto);

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
        ResponseVo responseVo = new ResponseVo();
        try {
            int del = roleService.del(id);
            if(del>0){
                responseVo.setCode("200").setMessage("操作成功");
            }else {
                responseVo
                        .setBoo(false)
                        .setCode(String.valueOf(BusinessExceptionEnum.NOT_STATUS.getCode()))
                        .setMessage(BusinessExceptionEnum.NOT_STATUS.getDesc());
            }
        }catch (Exception e){
            //操作失败
            responseVo.setCode(String.valueOf(BusinessExceptionEnum.NOT_STATUS.getCode())).setMessage(e.getMessage());
        }



        return  responseVo;
    }



}
