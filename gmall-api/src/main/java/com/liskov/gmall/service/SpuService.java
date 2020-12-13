package com.liskov.gmall.service;

import com.liskov.gmall.bean.BaseSaleAttr;
import com.liskov.gmall.bean.SpuInfo;

import java.util.List;

public interface SpuService {
    List<SpuInfo> spuList(String catalog3Id);

    List<BaseSaleAttr> baseSaleAttrList();
}
