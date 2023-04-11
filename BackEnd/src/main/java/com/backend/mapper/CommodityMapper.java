package com.backend.mapper;

import com.backend.bean.CommodityBean;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @BelongsProject: BackEnd
 * @BelongsPackage: com.backend.mapper
 * @Author: v panda
 * @CreateTime: 2023-04-02  19:12
 * @Description: TODO
 * @Version: 1.0
 */

@Repository
public interface CommodityMapper {
    public List<CommodityBean> findCommodity(String name);

    public List<CommodityBean> findCommodityByName(String name, int offset);

    public List<CommodityBean> findCommodityByTag(String tag, int offset);
}
