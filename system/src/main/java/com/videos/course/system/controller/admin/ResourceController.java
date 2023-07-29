package com.videos.course.system.controller.admin;

import com.videos.course.server.dto.ResourceDto;
import com.videos.course.server.dto.PageDto;
import com.videos.course.server.service.ResourceService;
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
@Api(tags = "Resource模块")
@RestController
@RequestMapping("/admin/resource")
@Slf4j
public class ResourceController {
        //输出日志的服务名称
     public static final String BUSINESS_NAME="SYSTEM_NAME服务之(ResourceController)";
    @Autowired
    private ResourceService resourceService;

    /**
     * 查询所有的数据信息
     * @param pageDto  分页的共用类
     * @return    返回共用的json数据vo模版格式
     */
    @ApiOperation(value = "查询所有的数据信息")
    @PostMapping("/list")
    public ResponseVo getList(@RequestBody PageDto pageDto){
        resourceService.getList(pageDto);

       ResponseVo responseVo = new ResponseVo();
       responseVo.setCode("200").setMessage("查询成功").setContent(pageDto);
        return  responseVo;
    }
    @ApiOperation(value = "查询所有的资源菜单的数据")
    @GetMapping("/resourceListTree")
    public ResponseVo getResourceListTree(){
        ResponseVo responseVo = new ResponseVo();
        //进行调用对应的service层
        List<ResourceDto> treeList =resourceService.getResourceListTree();
        responseVo.setCode("200").setMessage("查询成功").setContent(treeList);



        return  responseVo;
    }

    /**
     * 添加和修改的共用类
     * @param resourceDto  添加与修改的dto实体类
     * @return   返回共用的json数据vo模版格式
     */
    @ApiOperation(value = "添加和修改的共用类")
    @PostMapping("/saveAndUpdate")
    public ResponseVo saveAndUpdate(@RequestBody ResourceDto resourceDto){
        /**
         * 进行数据的长度和非空验证
         */

            ValidatorUtils.require(resourceDto.getName(), "名称");
            ValidatorUtils.length(resourceDto.getName(), "名称", 1, 100);
            ValidatorUtils.length(resourceDto.getPage(), "页面", 1, 50);
            ValidatorUtils.length(resourceDto.getRequest(), "请求", 1, 200);

        resourceService.saveAndUpdate(resourceDto);
        ResponseVo responseVo = new ResponseVo();
        responseVo.setCode("200").setMessage("操作成功").setContent(resourceDto);
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


        resourceService.del(id);
        ResponseVo responseVo = new ResponseVo();
        responseVo.setCode("200").setMessage("操作成功");
        return  responseVo;
    }



}
