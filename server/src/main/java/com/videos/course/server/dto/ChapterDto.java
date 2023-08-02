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
@ApiModel(value = "Chapter模块")
public class ChapterDto {

    /**
    * id
    */
    @ApiModelProperty(value = " id")
    private String id;

    /**
    * 课程id
    */
    @ApiModelProperty(value = " 课程id")
    private String courseId;

    /**
    * 名称
    */
    @ApiModelProperty(value = " 名称")
    private String name;


@Override
public String toString() {
StringBuilder sb = new StringBuilder();
sb.append(getClass().getSimpleName());
sb.append(" [");
sb.append("Hash = ").append(hashCode());
    sb.append(", id=").append(id);
    sb.append(", courseId=").append(courseId);
    sb.append(", name=").append(name);
sb.append("]");
return sb.toString();
}

}