package com.videos.course.server.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain=true)
@ApiModel(value = "Sort模块")
public class SortDto {

    /**
    * id
    */
    @ApiModelProperty(value = " id")
    private String id;

    /**
    * 旧排序值
    */
    @ApiModelProperty(value = " 旧排序值")
    private Integer oldSort;

    /**
    * 新排序值
    */
    @ApiModelProperty(value = " 新排序值")
    private Integer newSort;



@Override
public String toString() {
StringBuilder sb = new StringBuilder();
sb.append(getClass().getSimpleName());
sb.append(" [");
sb.append("Hash = ").append(hashCode());
    sb.append(", id=").append(id);
    sb.append(", oldSort=").append(oldSort);
    sb.append(", newSort=").append(newSort);
sb.append("]");
return sb.toString();
}

}