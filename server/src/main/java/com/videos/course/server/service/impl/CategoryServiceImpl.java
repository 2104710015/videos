package com.videos.course.server.service.impl;

import com.videos.course.server.domain.Category;
import com.videos.course.server.domain.CategoryExample;
import com.videos.course.server.domain.TestUsers;
import com.videos.course.server.dto.CategoryDto;
import com.videos.course.server.dto.PageDto;
import com.videos.course.server.mapper.CategoryMapper;
import com.videos.course.server.mapper.TestUsersMapper;
import com.videos.course.server.service.CategoryService;
import com.videos.course.server.utils.UUIDUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    @Override
    public void del(String id) {
        categoryMapper.deleteByPrimaryKey(id);
    }

    private  void save(Category  category){

        category.setId(UUIDUtils.random32());
        categoryMapper.insert(category);
    }
    private  void update(Category  category){
        categoryMapper.updateByPrimaryKey(category);
    }

}
