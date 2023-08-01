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
@ApiModel(value = "UserStudent模块")
public class UserStudentDto {

    /**
    * 
    */
    @ApiModelProperty(value = " ")
    private String id;

    /**
    * 
    */
    @ApiModelProperty(value = " ")
    private String userid;

    /**
    * 
    */
    @ApiModelProperty(value = " ")
    private String studentid;


@Override
public String toString() {
StringBuilder sb = new StringBuilder();
sb.append(getClass().getSimpleName());
sb.append(" [");
sb.append("Hash = ").append(hashCode());
    sb.append(", id=").append(id);
    sb.append(", userid=").append(userid);
    sb.append(", studentid=").append(studentid);
sb.append("]");
return sb.toString();
}

}