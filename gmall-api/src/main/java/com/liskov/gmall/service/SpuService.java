package com.liskov.gmall.service;

import com.liskov.gmall.bean.BaseSaleAttr;
import com.liskov.gmall.bean.SpuImage;
import com.liskov.gmall.bean.SpuInfo;
import com.liskov.gmall.bean.SpuSaleAttr;

import java.util.List;

public interface SpuService {

    /**
     * 商品信息list
     * @param catalog3Id
     * @return
     */
    List<SpuInfo> spuList(String catalog3Id);

    /**
     * 基本销售属性表-字典表
     * @return
     */
    List<BaseSaleAttr> baseSaleAttrList();

    /**
     * 保存商品信息
     * @param spuInfo
     * @return
     */
    void saveSpu(SpuInfo spuInfo);

    /**
     * 获取销售属性列表
     * @param spuId
     * @return
     */
    List<SpuSaleAttr> getSaleAttrListBySpuId(String spuId);

    /**
     * 获取商品图片信息
     * @param spuId
     * @return
     */
    List<SpuImage> getSpuImageListBySpuId(String spuId);
}
