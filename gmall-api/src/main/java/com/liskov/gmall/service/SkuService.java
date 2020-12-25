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

    /**
     * 查询库存商品信息-item 调用 manage 的服务
     * @param skuId
     * @return
     */
    SkuInfo getSkuById(String skuId);

    /**
     * 测试方法查询mysql中的sku信息
     * @param catalog3Id
     * @return
     */
    List<SkuInfo> getSkuListByCatalog3Id(String catalog3Id);
}
