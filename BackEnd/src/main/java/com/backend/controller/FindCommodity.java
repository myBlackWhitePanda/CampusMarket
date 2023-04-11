package com.backend.controller;

import com.backend.bean.CommodityBean;
import com.backend.service.CommodityService;
import com.backend.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @BelongsProject: BackEnd
 * @BelongsPackage: com.backend.controller
 * @Author: v panda
 * @CreateTime: 2023-04-02  22:33
 * @Description: TODO
 * @Version: 1.0
 */

@RestController("findCommodity")
@RequestMapping(value="/find")
public class FindCommodity {
    @Autowired
    CommodityService commodityService;

    @RequestMapping(value = "/findByName")
    public Result findByName(String name) {
        List<CommodityBean> list = commodityService.findCommodity(name, 0);
        if (list.size() != 0) {
            return Result.success(list);
        } else {
            return Result.error("未搜索到任何商品");
        }
    }
}
