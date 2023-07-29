package com.videos.course.server.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain=true)
@ApiModel(value = "User模块")
public class UserDto {

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
    private String codeToKen;



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
    sb.append(", codeToKen=").append(codeToKen);
    sb.append(", imageCode=").append(imageCode);
sb.append("]");
return sb.toString();
}

}