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
@ApiModel(value = "CourseCategory模块")
public class CourseCategoryDto {

    /**
    * id
    */
    @ApiModelProperty(value = " id")
    private String id;

    /**
    * 课程|course.id
    */
    @ApiModelProperty(value = " 课程|course.id")
    private String courseId;

    /**
    * 分类|course.id
    */
    @ApiModelProperty(value = " 分类|course.id")
    private String categoryId;


@Override
public String toString() {
StringBuilder sb = new StringBuilder();
sb.append(getClass().getSimpleName());
sb.append(" [");
sb.append("Hash = ").append(hashCode());
    sb.append(", id=").append(id);
    sb.append(", courseId=").append(courseId);
    sb.append(", categoryId=").append(categoryId);
sb.append("]");
return sb.toString();
}

}