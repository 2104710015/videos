package com.videos.course.server.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

<#list typeSet as type>
    <#if type=='LocalDateTime'>
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
    </#if>
    <#if type=='BigDecimal'>
import java.math.BigDecimal;
    </#if>
</#list>
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain=true)
@ApiModel(value = "${Domain}模块")
public class ${Domain}Dto {

<#list fieldList as field>
    /**
    * ${field.comment}
    */
    @ApiModelProperty(value = " ${field.comment}")
    <#if field.javaType=='LocalDateTime'>
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    </#if>
    private ${field.javaType} ${field.nameHump};

</#list>
<#--<#list fieldList as field>
    public ${field.javaType} get${field.nameBigHump}() {
    return ${field.nameHump};
    }

    public void set${field.nameBigHump}(${field.javaType} ${field.nameHump}) {
    this.${field.nameHump} = ${field.nameHump};
    }

</#list>-->

@Override
public String toString() {
StringBuilder sb = new StringBuilder();
sb.append(getClass().getSimpleName());
sb.append(" [");
sb.append("Hash = ").append(hashCode());
<#list fieldList as field>
    sb.append(", ${field.nameHump}=").append(${field.nameHump});
</#list>
sb.append("]");
return sb.toString();
}

}