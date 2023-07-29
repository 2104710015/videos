package com.videos.course.server.service.impl;

import com.alibaba.fastjson.JSON;
import com.videos.course.server.domin.Resource;
import com.videos.course.server.domin.User;
import com.videos.course.server.domin.UserExample;
import com.videos.course.server.dto.LoginUsersDto;
import com.videos.course.server.dto.ResourceDto;
import com.videos.course.server.dto.UserDto;
import com.videos.course.server.dto.PageDto;
import com.videos.course.server.enums.BusinessExceptionEnum;
import com.videos.course.server.exception.ServerVideosException;
import com.videos.course.server.exception.ValidatorException;
import com.videos.course.server.mapper.ResourceMapper;
import com.videos.course.server.mapper.UserMapper;
import com.videos.course.server.service.UserService;
import com.videos.course.server.utils.CopyUtils;
import com.videos.course.server.utils.UUIDUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;


import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 隔壁_老陈
 * 代码风骚、效率恐怖、编译器深深鄙视智商。
 * 千里之外定位问题、瞬间修复依旧风骚……
 * @create 2021-04-11 23:24
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {//alt +回车
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ResourceMapper  resourceMapper;
    @Override
    public  void getList(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
            //设置排序 为 倒序方式
        UserExample userExample = new UserExample();
        List<User> users = userMapper.selectByExample(userExample);
        PageInfo<User> userPageInfo = new PageInfo<>(users);
        pageDto.setTotal(userPageInfo.getTotal());
        /**
         * 使用jdk8的stream()进行遍历与拷贝数据
         */
        List<UserDto> userDtoList = users.stream().map(user -> {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(user, userDto);
            return userDto;
        }).collect(Collectors.toList());

        pageDto.setData(userDtoList);

    }

    @Override
    public void saveAndUpdate(UserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        if (!StringUtils.isEmpty(userDto.getId())) {
            update(user);
        }else {
           save(user);
        }
    }

    @Override
    public void del(String id) {
        userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public LoginUsersDto getLogin(UserDto userDto) {
        /*
        * 编写对应的业务逻辑
        * */
        //1.查询对应的账号信息
          //通过账号进行查询(账号唯一的),然后在进行对比密码
        User  user=  selectUsersLoginByNameExample(userDto.getLoginName());//账号是唯一的
        if(user==null){
            log.info("当前账号不存在:{}",userDto.getLoginName());
            //进行抛出对应的异常
            throw  new ServerVideosException(BusinessExceptionEnum.USER_LOGIN_ERROR);
        }else{
             //对密码进行加密  再次进行对比密码是否一致
//            byte[] bytes = userDto.getPassword().getBytes();
//            String s = DigestUtils.md5DigestAsHex(bytes);
//            userDto.setPassword(s);
            userDto.setPassword(DigestUtils.md5DigestAsHex(userDto.getPassword().getBytes()));
            if(user.getPassword().equals(userDto.getPassword())){
                 //账号登陆成功
                log.info("账号登陆成功:{}",userDto.getLoginName());
                //进行拷贝对象  注意:拷贝对象的时需求两个对象的属性名称是一致的
                LoginUsersDto loginUsersDto = CopyUtils.copy(user, LoginUsersDto.class);
                /**
                 * 进行查询当前账号的权限(明天编写)
                 *     1.将对应的权限进行封装为当前的这个对象LoginUsersDto
                 *     2.返回当前这个对象LoginUsersDto
                 *
                 *
                 * */
                getUsersAuth(loginUsersDto);
                    return  loginUsersDto;
            }else{//密码不正确
                log.info("当前密码不存在:{}=>数据库中对应的密码：{}",userDto.getPassword(),user.getPassword());
                //进行抛出异常
                throw  new ServerVideosException(BusinessExceptionEnum.USER_LOGIN_ERROR);
            }
        }
    }

    /**
     * 查询当前账号的权限
     * @param loginUsersDto
     */
    private void getUsersAuth(LoginUsersDto loginUsersDto) {
        /**
         * 正常情况下还是是返回Resource
         *     (进行数据的拷贝)
         *我们这位简单直接返回ResourceDto  不用转换
         */
      List<ResourceDto> listResource  =  resourceMapper.findResourceByUsersId(loginUsersDto.getId());
      /**
       * 将当前对应的数据信息将封装到LoginUsersDto
       * **/
        loginUsersDto.setResourceDtos(listResource);
        /**
         * 所有权限的的请求，进行接口的拦截
         * */
       HashSet<String> hashSet=  new HashSet<>();
        if(!CollectionUtils.isEmpty(listResource)){
            listResource.stream().forEach(r->{
                String request = r.getRequest();
                List<String> list = JSON.parseArray(request, String.class);
                  if(!CollectionUtils.isEmpty(list)){
                      hashSet.addAll(list);
                  }
            });
        }
      log.info("当前账号:{}-->获取到了对应的权限:{}",loginUsersDto.getLoginName(),hashSet);

        loginUsersDto.setRequests(hashSet);

    }

    private User selectUsersLoginByNameExample(String loginName) {
        UserExample userExample = new UserExample();
           userExample.createCriteria().andLoginNameEqualTo(loginName);
        List<User> users = userMapper.selectByExample(userExample);
           if(CollectionUtils.isEmpty(users)){
               return  null;
           }else{
                return  users.get(0);
           }
    }

    private  void save(User  user){

        user.setId(UUIDUtils.random32());
        userMapper.insert(user);
    }
    private  void update(User  user){
        userMapper.updateByPrimaryKey(user);
    }

}
