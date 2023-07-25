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
@ApiModel(value = "Category模块")
public class CategoryDto {

    /**
    * id
    */
    @ApiModelProperty(value = " id")
    private String id;

    /**
    * 父id
    */
    @ApiModelProperty(value = " 父id")
    private String parent;

    /**
    * 名称
    */
    @ApiModelProperty(value = " 名称")
    private String name;

    /**
    * 顺序
    */
    @ApiModelProperty(value = " 顺序")
    private Integer sort;


@Override
public String toString() {
StringBuilder sb = new StringBuilder();
sb.append(getClass().getSimpleName());
sb.append(" [");
sb.append("Hash = ").append(hashCode());
    sb.append(", id=").append(id);
    sb.append(", parent=").append(parent);
    sb.append(", name=").append(name);
    sb.append(", sort=").append(sort);
sb.append("]");
return sb.toString();
}

}