package com.campus_market.campus_market_backend.DO;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

/**
 * @BelongsProject: Campus_Market_BackEnd
 * @BelongsPackage: com.campus_market.campus_market_backend.DO
 * @Author: v panda
 * @CreateTime: 2023-03-30  22:44
 * @Description: TODO
 * @Version: 1.0
 */

@Getter
@Setter
public class Commodity {

    private int ID;

    private String name;

    // 0 是书籍，1是衣物，2是摆件，3是其他（需要备注处描述）
    private int tag;

    private byte[] img;

    private String description;

    // 0 is not sell, 1 is sell
    private Boolean isSell;

    private Timestamp create_time;

    // 备注
    private String remark;
}
