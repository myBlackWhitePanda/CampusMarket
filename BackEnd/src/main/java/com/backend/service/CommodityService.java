package com.backend.service;

import com.backend.bean.CommodityBean;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @BelongsProject: BackEnd
 * @BelongsPackage: com.backend.service
 * @Author: v panda
 * @CreateTime: 2023-04-02  19:18
 * @Description: TODO
 * @Version: 1.0
 */

@Service
public interface CommodityService {
    public List<CommodityBean> findCommodity(String name, int offset);
}
