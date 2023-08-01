package com.videos.course.server.service.impl;

import com.videos.course.server.domin.Category;
import com.videos.course.server.domin.CategoryExample;
import com.videos.course.server.dto.CategoryDto;
import com.videos.course.server.dto.PageDto;
import com.videos.course.server.enums.BusinessExceptionEnum;
import com.videos.course.server.exception.ServerVideosException;
import com.videos.course.server.mapper.CategoryMapper;
import com.videos.course.server.service.CategoryService;
import com.videos.course.server.utils.UUIDUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;



import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 隔壁_老陈
 * 代码风骚、效率恐怖、编译器深深鄙视智商。
 * 千里之外定位问题、瞬间修复依旧风骚……
 * @create 2021-04-11 23:24
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public  void getList(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
            //设置排序 为 倒序方式
        CategoryExample categoryExample = new CategoryExample();
                categoryExample.setOrderByClause("sort asc");
        List<Category> categorys = categoryMapper.selectByExample(categoryExample);
        PageInfo<Category> categoryPageInfo = new PageInfo<>(categorys);
        pageDto.setTotal(categoryPageInfo.getTotal());
        /**
         * 使用jdk8的stream()进行遍历与拷贝数据
         */
        List<CategoryDto> categoryDtoList = categorys.stream().map(category -> {
            CategoryDto categoryDto = new CategoryDto();
            BeanUtils.copyProperties(category, categoryDto);
            return categoryDto;
        }).collect(Collectors.toList());

        pageDto.setData(categoryDtoList);

    }

    @Override
    public void saveAndUpdate(CategoryDto categoryDto) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDto, category);
        if (!StringUtils.isEmpty(categoryDto.getId())) {
            update(category);
        }else {
           save(category);
        }
    }

    /**
     * 当删除以及分类的时候，如果有二级分类，那么一级分类不能删
     *
     * @param id
     */
    @Override
    public void del(String id) {
        List<Category> categories =selectByIdChildren(id);
        if (CollectionUtils.isEmpty(categories)){
            //没有对应的数据的情况下进行删除
            categoryMapper.deleteByPrimaryKey(id);
        }else {
            throw new ServerVideosException(BusinessExceptionEnum.DELETE_NOT_EROOR);
        }

    }
    private List<Category> selectByIdChildren(String id){
        //通过父级id进行查询是否有数据存在
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.createCriteria().andParentEqualTo(id);
        List<Category> categories = categoryMapper.selectByExample(categoryExample);
        return categories;
    }


    @Override
    public List<CategoryDto> getAllList() {
        List<Category> categories = categoryMapper.selectByExample(null);//查询所有数据
        //进行拷贝对象
        List<CategoryDto> collect = categories.stream().map(x -> {
            CategoryDto categoryDto = new CategoryDto();
            BeanUtils.copyProperties(x, categoryDto);
            return categoryDto;
        }).collect(Collectors.toList());

        return collect;
    }

    private  void save(Category  category){

        category.setId(UUIDUtils.random32());
        categoryMapper.insert(category);
    }
    private  void update(Category  category){
        categoryMapper.updateByPrimaryKey(category);
    }

}
