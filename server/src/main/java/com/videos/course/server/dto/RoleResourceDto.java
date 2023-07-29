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
@ApiModel(value = "RoleResource模块")
public class RoleResourceDto {

    /**
    * id
    */
    @ApiModelProperty(value = " id")
    private String id;

    /**
    * 角色|id
    */
    @ApiModelProperty(value = " 角色|id")
    private String roleId;

    /**
    * 资源|id
    */
    @ApiModelProperty(value = " 资源|id")
    private String resourceId;


@Override
public String toString() {
StringBuilder sb = new StringBuilder();
sb.append(getClass().getSimpleName());
sb.append(" [");
sb.append("Hash = ").append(hashCode());
    sb.append(", id=").append(id);
    sb.append(", roleId=").append(roleId);
    sb.append(", resourceId=").append(resourceId);
sb.append("]");
return sb.toString();
}

}