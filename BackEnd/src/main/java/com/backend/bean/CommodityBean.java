package com.backend.bean;

import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * @BelongsProject: BackEnd
 * @BelongsPackage: com.backend.Bean
 * @Author: v panda
 * @CreateTime: 2023-04-02  19:04
 * @Description: TODO
 * @Version: 1.0
 */

@Data
public class CommodityBean {
    private int ID;

    private String name;

    private Float price;

    // 0 是书籍，1是衣物，2是摆件，3是其他（需要备注处描述）
    private int tag;

    private byte[] img;

    private String description;

    // 0 is not sell, 1 is sold
    private Boolean isSell;

    private Timestamp create_time;

    // 备注
    private String remark;
}
