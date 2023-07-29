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
@ApiModel(value = "Teacher模块")
public class TeacherDto {

    /**
    * id
    */
    @ApiModelProperty(value = " id")
    private String id;

    /**
    * 姓名
    */
    @ApiModelProperty(value = " 姓名")
    private String name;

    /**
    * 昵称
    */
    @ApiModelProperty(value = " 昵称")
    private String nickname;

    /**
    * 头像
    */
    @ApiModelProperty(value = " 头像")
    private String image;

    /**
    * 职位
    */
    @ApiModelProperty(value = " 职位")
    private String position;

    /**
    * 座右铭
    */
    @ApiModelProperty(value = " 座右铭")
    private String motto;

    /**
    * 简介
    */
    @ApiModelProperty(value = " 简介")
    private String intro;


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
    sb.append(", position=").append(position);
    sb.append(", motto=").append(motto);
    sb.append(", intro=").append(intro);
sb.append("]");
return sb.toString();
}

}