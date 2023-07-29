package com.videos.course.server.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain=true)
@ApiModel(value = "Resource模块")
public class ResourceDto {

    /**
    * id
    */
    @ApiModelProperty(value = " id")
    private String id;

    /**
    * 名称|菜单或按钮
    */
    @ApiModelProperty(value = " 名称|菜单或按钮")
    private String name;

    /**
    * 页面|路由
    */
    @ApiModelProperty(value = " 页面|路由")
    private String page;

    /**
    * 请求|接口
    */
    @ApiModelProperty(value = " 请求|接口")
    private String request;

    /**
    * 父id
    */
    @ApiModelProperty(value = " 父id")
    private String parent;

    @ApiModelProperty(value = " 子菜单集合")
    private List<ResourceDto> children;


@Override
public String toString() {
StringBuilder sb = new StringBuilder();
sb.append(getClass().getSimpleName());
sb.append(" [");
sb.append("Hash = ").append(hashCode());
    sb.append(", id=").append(id);
    sb.append(", name=").append(name);
    sb.append(", page=").append(page);
    sb.append(", request=").append(request);
    sb.append(", parent=").append(parent);
    sb.append(", children=").append(children);

    sb.append("]");
return sb.toString();
}

}