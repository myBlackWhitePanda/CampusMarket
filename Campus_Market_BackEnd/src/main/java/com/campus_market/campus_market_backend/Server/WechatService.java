package com.campus_macket.campus_market_backend.Server;

import com.campus_macket.campus_market_backend.DTO.WechatLoginRequestDTO;

import java.util.Map;

public interface WechatService {
    Map<String, Object> getUserInfoMap(WechatLoginRequestDTO loginRequest) throws Exception;
}