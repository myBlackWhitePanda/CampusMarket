package com.campus_macket.campus_market_backend.Controller;

import com.campus_macket.campus_market_backend.DTO.WechatLoginRequestDTO;
import com.campus_macket.campus_market_backend.Server.WechatService;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController("LoginController")
@RequestMapping(value = "/wechat/login")
public class LoginController {
    @Resource
    WechatService wechatService;

    @ApiOperation(value = "1.登入接口", httpMethod = "POST")
    @PostMapping("/save")
    public Map<String, Object> login(@Validated @RequestBody WechatLoginRequestDTO loginRequest) throws Exception {
        return wechatService.getUserInfoMap(loginRequest);
    }
}
