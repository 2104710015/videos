package com.videos.course.server.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.tomcat.jni.Local;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain=true)
@ApiModel(value = "Anno模块")
public class AnnoDto {

    /**
    * id
    */
    @ApiModelProperty(value = " id")
    private String id;

    /**
    * 标题
    */
    @ApiModelProperty(value = " 标题")
    private String title;

    /**
    * 内容
    */
    @ApiModelProperty(value = " 内容")
    private String content;

    /**
    * 教师id
    */
    @ApiModelProperty(value = " 教师id")
    private String teacherid;

    /**
    * 课程id
    */
    @ApiModelProperty(value = " 课程id")
    private String courseid;

    /**
    * 发布时间
    */
    @ApiModelProperty(value = " 发布时间")
    private LocalDate releasetime;

    /**
    * 状态
    */
    @ApiModelProperty(value = " 状态")
    private String status;


@Override
public String toString() {
StringBuilder sb = new StringBuilder();
sb.append(getClass().getSimpleName());
sb.append(" [");
sb.append("Hash = ").append(hashCode());
    sb.append(", id=").append(id);
    sb.append(", title=").append(title);
    sb.append(", content=").append(content);
    sb.append(", teacherid=").append(teacherid);
    sb.append(", courseid=").append(courseid);
    sb.append(", releasetime=").append(releasetime);
    sb.append(", status=").append(status);
sb.append("]");
return sb.toString();
}

}