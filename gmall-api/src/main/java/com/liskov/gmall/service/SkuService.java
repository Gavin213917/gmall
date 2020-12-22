package com.liskov.gmall.service;

import com.liskov.gmall.bean.SkuInfo;

import java.util.List;

public interface SkuService {

    /**
     * 查询商品单元列表信息
     * @param spuId
     * @return
     */
    List<SkuInfo> getSkuListBySpu(String spuId);

    /**
     * 保存商品单元信息
     * @param skuInfo
     * @return
     */
    void saveSku(SkuInfo skuInfo);
}
