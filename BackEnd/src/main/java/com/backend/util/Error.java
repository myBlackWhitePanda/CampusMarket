package com.backend.util;

import lombok.Getter;
import lombok.Setter;

/**
 * @BelongsProject: BackEnd
 * @BelongsPackage: com.backend.util
 * @Author: v panda
 * @CreateTime: 2023-04-03  11:32
 * @Description: TODO
 * @Version: 1.0
 */

@Setter
@Getter
public class Error {
    private int code;		// 状态码
    private String msg;		// 返回的信息

    private Error(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    // 静态常用异常
    public static Error ERROR_1 = new Error(400,"异常类型一");
    public static Error ERROR_2 = new Error(500,"异常类型二");

}
