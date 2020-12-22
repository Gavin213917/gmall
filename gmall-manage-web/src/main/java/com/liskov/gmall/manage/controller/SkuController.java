package com.liskov.gmall.manage.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.liskov.gmall.bean.SkuInfo;
import com.liskov.gmall.service.SkuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class SkuController {

    @Reference
    SkuService skuService;

    /**
     * 保存商品单元信息
     * @param skuInfo
     * @return
     */
    @RequestMapping("saveSku")
    @ResponseBody
    public String saveSku(SkuInfo skuInfo){
        return "success";
    }

    /**
     * 查询商品单元列表信息
     * @param spuId
     * @return
     */
    @RequestMapping("getSkuListBySpu")
    @ResponseBody
    public List<SkuInfo> getSkuListBySpu(String spuId){
        List<SkuInfo> skuInfos =  skuService.getSkuListBySpu(spuId);
        return skuInfos;
    }
}
