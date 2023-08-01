package com.videos.course.customer.controller.admin;

import com.videos.course.server.dto.CategoryDto;
import com.videos.course.server.dto.PageDto;
import com.videos.course.server.enums.BusinessExceptionEnum;
import com.videos.course.server.service.CategoryService;
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
@Api(tags = "Category模块")
@RestController
@RequestMapping("/admin/category")
@Slf4j
public class CategoryController {
        //输出日志的服务名称
     public static final String BUSINESS_NAME="CUSTOMER_NAME服务之(CategoryController)";
    @Autowired
    private CategoryService categoryService;

    /**
     * 查询所有的数据信息
     * @param pageDto  分页的共用类
     * @return    返回共用的json数据vo模版格式
     */
    @ApiOperation(value = "查询所有的数据信息")
    @PostMapping("/list")
    public ResponseVo getList(@RequestBody PageDto pageDto){
        categoryService.getList(pageDto);

       ResponseVo responseVo = new ResponseVo();
       responseVo.setCode("200").setMessage("查询成功").setContent(pageDto);
        return  responseVo;
    }

    /**
     * 分类查询数据信息
     * @return
     */
    @ApiOperation(value = "分类查询数据信息")
    @PostMapping("/getAllList")
    public ResponseVo getAllList(){
        ResponseVo responseVo = new ResponseVo();
        List<CategoryDto> list=categoryService.getAllList();
        responseVo
                .setCode(String.valueOf(BusinessExceptionEnum.OK_STATUS))
                .setMessage(BusinessExceptionEnum.OK_STATUS.getDesc())
                .setContent(list);


        return responseVo;
    }


    /**
     * 添加和修改的共用类
     * @param categoryDto  添加与修改的dto实体类
     * @return   返回共用的json数据vo模版格式
     */
    @ApiOperation(value = "添加和修改的共用类")
    @PostMapping("/saveAndUpdate")
    public ResponseVo saveAndUpdate(@RequestBody CategoryDto categoryDto){
        /**
         * 进行数据的长度和非空验证
         */

            ValidatorUtils.require(categoryDto.getParent(), "父id");
            ValidatorUtils.require(categoryDto.getName(), "名称");
            ValidatorUtils.length(categoryDto.getName(), "名称", 1, 50);

        categoryService.saveAndUpdate(categoryDto);
        ResponseVo responseVo = new ResponseVo();
        responseVo.setCode("200").setMessage("操作成功").setContent(categoryDto);
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
        log.info("通过分类编号:{}进行删除数据",id);
        ValidatorUtils.require(id,"分类编号");
        ResponseVo responseVo = new ResponseVo();
        try{
            categoryService.del(id);
            responseVo.setCode("200").setMessage("操作成功");
        }catch (Exception e){
            responseVo
                    .setBoo(false)
                    .setCode(String.valueOf(BusinessExceptionEnum.DELETE_NOT_EROOR.getCode()))
                    .setMessage(e.getMessage());

        }

        return  responseVo;
    }



}
