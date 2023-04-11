package com.backend.util;

import lombok.Getter;
import lombok.Setter;

/**
 * @BelongsProject: BackEnd
 * @BelongsPackage: com.backend.util
 * @Author: v panda
 * @CreateTime: 2023-04-03  11:29
 * @Description: TODO
 * @Version: 1.0
 */

@Getter
@Setter
public class Result <T> {
    private int code;       // 状态码
    private String msg;     // 返回的信息
    private T data;         // 返回的数据

    /**
     * 成功时候的调用（有数据）
     */
    public static <T> Result<T> success(T data){
        return new Result<T>(data);
    }

    /**
     * 成功时候的调用（无数据）
     */
    public static <T> Result<T> success(){
        return new Result<T>();
    }

    /**
     * 异常时候的调用（有msg参数）
     */
    public static <T> Result<T> error(String msg){
        return new Result<T>(msg);
    }

    /**
     * 异常时候的调用（无msg参数）
     */
    public static <T> Result<T> error(){
        return new Result<T>("error");
    }

    private Result(T data) {
        this.code = 200;
        this.msg = "success";
        this.data = data;
    }

    private Result() {
        this.code = 200;
        this.msg = "success";
    }

    private Result(String msg) {
        this.code = 400;
        this.msg = msg;
    }

    public static <T> Result<T> error(Error error){
        return new Result<T>(error);
    }

    private Result(Error error) {
        if (error == null){
            return ;
        }
        this.code = error.getCode();
        this.msg = error.getMsg();
    }

}
