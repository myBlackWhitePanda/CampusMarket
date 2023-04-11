package com.campus_macket.campus_market_backend.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@ApiModel(value = "小程序用户表")
public class WechatLoginRequestDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull(message = "code不能为空")
    @ApiModelProperty(value = "微信code", required = true)
    private String code;

    @NotNull(message = "appName不能为空")
    @ApiModelProperty(value = "小程序名称，判断从哪个小程序登录", required = true)
    private String appName;

    @NotNull(message = "rawData不能为空")
    @ApiModelProperty(value = "用户非敏感字段")
    private String rawData;

    @ApiModelProperty(value = "签名")
    private String signature;

    @NotNull(message = "encryptedData不能为空")
    @ApiModelProperty(value = "用户敏感字段")
    private String encryptedData;

    @NotNull(message = "iv不能为空")
    @ApiModelProperty(value = "解密向量")
    private String iv;
}
