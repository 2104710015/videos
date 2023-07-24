package com.videos.course.server.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain=true)
public class UserDto {

    /**
    * id
    */
    private String id;

    /**
    * 登陆名
    */
    private String loginName;

    /**
    * 昵称
    */
    private String name;

    /**
    * 密码
    */
    private String password;


@Override
public String toString() {
StringBuilder sb = new StringBuilder();
sb.append(getClass().getSimpleName());
sb.append(" [");
sb.append("Hash = ").append(hashCode());
    sb.append(", id=").append(id);
    sb.append(", loginName=").append(loginName);
    sb.append(", name=").append(name);
    sb.append(", password=").append(password);
sb.append("]");
return sb.toString();
}

}