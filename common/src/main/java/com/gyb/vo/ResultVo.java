package com.gyb.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @date 2023/3/13 - 14:43  
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "VO对象",description = "响应信息的VO对象")
public class ResultVo {
    @ApiModelProperty(value = "响应状态码",dataType = "int")
    //响应给前端的状态码
    private int code;
    @ApiModelProperty(value = "响应描述信息",dataType = "string")
    //响应给前端的状态信息
    private String msg;
    @ApiModelProperty(value = "响应数据",dataType = "object")
    //响应给前端的数据
    private Object data;
}
