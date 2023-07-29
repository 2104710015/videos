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
@ApiModel(value = "Student模块")
public class StudentDto {

    /**
    * 学生id
    */
    @ApiModelProperty(value = " 学生id")
    private String id;

    /**
    * 学生名字
    */
    @ApiModelProperty(value = " 学生名字")
    private String name;

    /**
    * 学生昵称
    */
    @ApiModelProperty(value = " 学生昵称")
    private String nickname;

    /**
    * 头像
    */
    @ApiModelProperty(value = " 头像")
    private String image;

    /**
    * 性别
    */
    @ApiModelProperty(value = " 性别")
    private String sex;

    /**
    * 邮箱
    */
    @ApiModelProperty(value = " 邮箱")
    private String email;

    /**
    * 手机号
    */
    @ApiModelProperty(value = " 手机号")
    private String phone;


@Override
public String toString() {
StringBuilder sb = new StringBuilder();
sb.append(getClass().getSimpleName());
sb.append(" [");
sb.append("Hash = ").append(hashCode());
    sb.append(", id=").append(id);
    sb.append(", name=").append(name);
    sb.append(", nickname=").append(nickname);
    sb.append(", image=").append(image);
    sb.append(", sex=").append(sex);
    sb.append(", email=").append(email);
    sb.append(", phone=").append(phone);
sb.append("]");
return sb.toString();
}

}