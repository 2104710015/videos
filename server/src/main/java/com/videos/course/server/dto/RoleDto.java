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
@ApiModel(value = "Role模块")
public class RoleDto {

    /**
    * id
    */
    @ApiModelProperty(value = " id")
    private String id;

    /**
    * 角色
    */
    @ApiModelProperty(value = " 角色")
    private String name;

    /**
    * 描述
    */
    @ApiModelProperty(value = " 描述")
    private String desc;

    @ApiModelProperty(value = "勾选上的菜单节点id数据")
    private List<String> resourcesIds;

    @ApiModelProperty(value = "角色的编号")
    private String roleId;

    @ApiModelProperty(value = "修改的用户的编号")
    private List<String> userIds;



@Override
public String toString() {
StringBuilder sb = new StringBuilder();
sb.append(getClass().getSimpleName());
sb.append(" [");
sb.append("Hash = ").append(hashCode());
    sb.append(", id=").append(id);
    sb.append(", name=").append(name);
    sb.append(", desc=").append(desc);
    sb.append(", resourcesIds=").append(resourcesIds);
    sb.append(", roleId=").append(roleId);
    sb.append(", userIds=").append(userIds);


    sb.append("]");
return sb.toString();
}

}