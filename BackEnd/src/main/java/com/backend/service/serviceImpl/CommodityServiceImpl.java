package com.backend.service.serviceImpl;

import com.backend.bean.CommodityBean;
import com.backend.mapper.CommodityMapper;
import com.backend.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @BelongsProject: BackEnd
 * @BelongsPackage: com.backend.service.serviceImpl
 * @Author: v panda
 * @CreateTime: 2023-04-02  19:20
 * @Description: TODO
 * @Version: 1.0
 */

@Service
public class CommodityServiceImpl implements CommodityService {
    @Autowired
    private CommodityMapper commodityMapper;

    @Override
    public List<CommodityBean> findCommodity(String name, int offset) {
        return commodityMapper.findCommodityByName(name, offset);
    }
}
