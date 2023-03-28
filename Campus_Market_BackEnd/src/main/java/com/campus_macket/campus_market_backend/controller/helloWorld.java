package com.campus_macket.campus_market_backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class helloWorld {
    @RequestMapping("/helloworld")
    public String hello() {
        return "Hello World";
    }
}