package com.liskov.gmall.service;

import com.liskov.gmall.bean.BaseSaleAttr;
import com.liskov.gmall.bean.SpuInfo;

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
}
