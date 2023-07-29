package com.videos.course.server.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain=true)
@ApiModel(value = "User模块")
public class LoginUsersDto {

    /**
    * id
    */
    @ApiModelProperty(value = " id")
    private String id;

    /**
    * 登陆名
    */
    @ApiModelProperty(value = " 登陆名")
    private String loginName;

    /**
    * 昵称
    */
    @ApiModelProperty(value = " 昵称")
    private String name;

    /**
    * 密码
    */
    @ApiModelProperty(value = " 密码")
    private String password;
    @ApiModelProperty(value = " 验证码")
    private String imageCode;
    @ApiModelProperty(value = " 验证码")
    private String toKen;
    @ApiModelProperty(value = " 权限资源的数据信息")
    private List<ResourceDto> resourceDtos;
    @ApiModelProperty(value = " 请求的路径地址")
    private Set<String> requests;


@Override
public String toString() {
StringBuilder sb = new StringBuilder();
sb.append(getClass().getSimpleName());
sb.append(" [");
sb.append("Hash = ").append(hashCode());
    sb.append(", id=").append(id);
    sb.append(", loginName=").append(loginName);
    sb.append(", name=").append(name);
    sb.append(", password=").append(password);
    sb.append(", requests=").append(requests);
    sb.append(", toKen=").append(toKen);
    sb.append(", resourceDtos=").append(resourceDtos);
    sb.append(", imageCode=").append(imageCode);
sb.append("]");
return sb.toString();
}

}