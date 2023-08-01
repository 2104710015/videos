package com.videos.course.server.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain=true)
@ApiModel(value = "UpdateInfo模块")
public class UpdateInfoDto {

    /**
    * 更新id
    */
    @ApiModelProperty(value = " 更新id")
    private String id;

    /**
    * 更新时间
    */
    @ApiModelProperty(value = " 更新时间")
    private LocalDate updatetime;

    /**
    * 更新内容
    */
    @ApiModelProperty(value = " 更新内容")
    private String updatecontent;


@Override
public String toString() {
StringBuilder sb = new StringBuilder();
sb.append(getClass().getSimpleName());
sb.append(" [");
sb.append("Hash = ").append(hashCode());
    sb.append(", id=").append(id);
    sb.append(", updatetime=").append(updatetime);
    sb.append(", updatecontent=").append(updatecontent);
sb.append("]");
return sb.toString();
}

}