package com.campus_macket.campus_market_backend.DTO;

import lombok.Setter;

/**
 * @BelongsProject: Campus_Market_BackEnd
 * @BelongsPackage: com.campus_macket.campus_market_backend.DTO
 * @Author: v panda
 * @CreateTime: 2023-03-29  20:37
 * @Description: TODO
 * @Version: 1.0
 */


@Setter
public class Token {
    private String token;

    public Token(String token) {
        this.token = token;
    }
}
