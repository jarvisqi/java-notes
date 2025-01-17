package org.softmax.ms.gateway.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Jarvis
 */
@Setter
@Getter
@ApiModel("返回值")
public class Result<T> implements Serializable {

    @ApiModelProperty(value = "成功标识-1=失败，0=成功")
    private Integer code;
    @ApiModelProperty(value = "描述信息")
    private String message;
    @ApiModelProperty(value = "访问路径")
    private String path;
    @ApiModelProperty(value = "返回数据对象")
    private T data;

}
