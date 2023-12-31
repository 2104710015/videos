package com.videos.course.server.service;



import com.videos.course.server.dto.LoginUsersDto;
import com.videos.course.server.dto.UserDto;
import com.videos.course.server.dto.PageDto;


/**
 * @author 隔壁_老陈
 * 代码风骚、效率恐怖、编译器深深鄙视智商。
 * 千里之外定位问题、瞬间修复依旧风骚……
 * @create 2021-04-11 23:24
 */
public interface UserService {//到实现类ctrl + ALT + B
    public void getList(PageDto pageDto);
    public void saveAndUpdate(UserDto userDto);

    public void del(String id);

    LoginUsersDto getLogin(UserDto userDto);
}
