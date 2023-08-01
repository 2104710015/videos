package com.videos.course.server.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain=true)
@ApiModel(value = "Course模块")
public class CourseDto {

    /**
    * id
    */
    @ApiModelProperty(value = " id")
    private String id;

    /**
    * 名称
    */
    @ApiModelProperty(value = " 名称")
    private String name;

    /**
    * 概述
    */
    @ApiModelProperty(value = " 概述")
    private String summary;

    /**
    * 时长|单位秒
    */
    @ApiModelProperty(value = " 时长|单位秒")
    private Integer time;

    /**
    * 价格（元）
    */
    @ApiModelProperty(value = " 价格（元）")
    private BigDecimal price;

    /**
    * 封面
    */
    @ApiModelProperty(value = " 封面")
    private String image;

    /**
    * 级别|枚举[CourseLevelEnum]：ONE("1", "初级"),TWO("2", "中级"),THREE("3", "高级")
    */
    @ApiModelProperty(value = " 级别|枚举[CourseLevelEnum]：ONE('1', '初级'),TWO('2', '中级'),THREE('3', '高级')")
    private String level;

    /**
    * 收费|枚举[CourseChargeEnum]：CHARGE("C", "收费"),FREE("F", "免费")
    */
    @ApiModelProperty(value = " 收费|枚举[CourseChargeEnum]：CHARGE('C', '收费'),FREE('F', '免费')")
    private String charge;

    /**
    * 状态|枚举[CourseStatusEnum]：PUBLISH("P", "发布"),DRAFT("D", "草稿")
    */
    @ApiModelProperty(value = " 状态|枚举[CourseStatusEnum]：PUBLISH('P', '发布'),DRAFT('D', '草稿')")
    private String status;

    /**
    * 报名数
    */
    @ApiModelProperty(value = " 报名数")
    private Integer enroll;

    /**
    * 顺序
    */
    @ApiModelProperty(value = " 顺序")
    private Integer sort;

    /**
    * 创建时间
    */
    @ApiModelProperty(value = " 创建时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime createdAt;

    /**
    * 修改时间
    */
    @ApiModelProperty(value = " 修改时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime updatedAt;

    /**
    * 讲师|teacher.id
    */
    @ApiModelProperty(value = " 讲师|teacher.id")
    private String teacherId;


@Override
public String toString() {
StringBuilder sb = new StringBuilder();
sb.append(getClass().getSimpleName());
sb.append(" [");
sb.append("Hash = ").append(hashCode());
    sb.append(", id=").append(id);
    sb.append(", name=").append(name);
    sb.append(", summary=").append(summary);
    sb.append(", time=").append(time);
    sb.append(", price=").append(price);
    sb.append(", image=").append(image);
    sb.append(", level=").append(level);
    sb.append(", charge=").append(charge);
    sb.append(", status=").append(status);
    sb.append(", enroll=").append(enroll);
    sb.append(", sort=").append(sort);
    sb.append(", createdAt=").append(createdAt);
    sb.append(", updatedAt=").append(updatedAt);
    sb.append(", teacherId=").append(teacherId);
sb.append("]");
return sb.toString();
}

}