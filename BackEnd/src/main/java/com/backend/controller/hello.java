package com.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @BelongsProject: BackEnd
 * @BelongsPackage: com.backend.Controller
 * @Author: v panda
 * @CreateTime: 2023-04-02  18:21
 * @Description: TODO
 * @Version: 1.0
 */

@RestController("hello")
@RequestMapping(value="/hello")
public class hello {

    @RequestMapping(value = "/hello")
    public String helloWorld() {
        return "hello world";
    }
}
