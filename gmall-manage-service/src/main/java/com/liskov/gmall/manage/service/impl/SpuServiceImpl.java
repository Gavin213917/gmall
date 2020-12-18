package com.liskov.gmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.liskov.gmall.bean.BaseSaleAttr;
import com.liskov.gmall.bean.SpuInfo;
import com.liskov.gmall.manage.mapper.BaseSaleAttrMapper;
import com.liskov.gmall.manage.mapper.SpuInfoMapper;
import com.liskov.gmall.service.SpuService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Service
public class SpuServiceImpl implements SpuService {

    @Autowired
    SpuInfoMapper spuInfoMapper;

    @Autowired
    BaseSaleAttrMapper baseSaleAttrMapper;

    /**
     * 商品信息list
     * @param catalog3Id
     * @return
     */
    @Override
    public List<SpuInfo> spuList(String catalog3Id) {
        SpuInfo spuInfo = new SpuInfo();
        spuInfo.setCatalog3Id(catalog3Id);
        List<SpuInfo> select = spuInfoMapper.select(spuInfo);
        return select;
    }

    /**
     * 基本销售属性表-字典表
     * @return
     */
    @Override
    public List<BaseSaleAttr> baseSaleAttrList() {
        return baseSaleAttrMapper.selectAll();
    }

    /**
     * 保存商品信息
     * @param spuInfo
     * @return
     */
    @Override
    public void saveSpu(SpuInfo spuInfo) {

    }
}
