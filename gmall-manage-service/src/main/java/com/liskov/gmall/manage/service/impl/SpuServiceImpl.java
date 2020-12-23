package com.liskov.gmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.liskov.gmall.bean.*;
import com.liskov.gmall.manage.mapper.*;
import com.liskov.gmall.service.SpuService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;


@Service
public class SpuServiceImpl implements SpuService {

    @Autowired
    SpuInfoMapper spuInfoMapper;
    @Autowired
    SpuImageMapper spuImageMapper;
    @Autowired
    SpuSaleAttrMapper spuSaleAttrMapper;
    @Autowired
    SpuSaleAttrValueMapper spuSaleAttrValueMapper;
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
        //保存spuInfo信息，插入后，将主键值（属性id）返回，在实体类上需要配置主键属性
        spuInfoMapper.insertSelective(spuInfo);

        //保存图片信息
        List<SpuImage> spuImageList = spuInfo.getSpuImageList();
        for (SpuImage spuImage : spuImageList) {
            spuImage.setSpuId(spuInfo.getId());
            spuImageMapper.insert(spuImage);
        }

        //获取前台传过来添加的销售属性值
        List<SpuSaleAttr> spuSaleAttrList = spuInfo.getSpuSaleAttrList();
        //循环插入，保存商品销售属性信息
        for (SpuSaleAttr spuSaleAttr:spuSaleAttrList) {
            spuSaleAttr.setSpuId(spuInfo.getId());
            spuSaleAttrMapper.insert(spuSaleAttr);
            //保存商品销售属性值信息
            List<SpuSaleAttrValue> saleAttrValueList = spuSaleAttr.getSpuSaleAttrValueList();
            for(SpuSaleAttrValue spuSaleAttrValue:saleAttrValueList){
                spuSaleAttrValue.setSpuId(spuInfo.getId());
                spuSaleAttrValueMapper.insert(spuSaleAttrValue);
            }
        }
    }

    /**
     * 获取销售属性列表
     * @param spuId
     * @return
     */
    @Override
    public List<SpuSaleAttr> getSaleAttrListBySpuId(String spuId) {
        SpuSaleAttr spuSaleAttr = new SpuSaleAttr();
        spuSaleAttr.setSpuId(spuId);
        List<SpuSaleAttr> spuSaleAttrs = spuSaleAttrMapper.select(spuSaleAttr);

        for (SpuSaleAttr saleAttr : spuSaleAttrs) {
            String saleAttrId = saleAttr.getSaleAttrId();

            SpuSaleAttrValue spuSaleAttrValue = new SpuSaleAttrValue();
            spuSaleAttrValue.setSaleAttrId(saleAttrId);
            spuSaleAttrValue.setSpuId(spuId);
            List<SpuSaleAttrValue> spuSaleAttrValues = spuSaleAttrValueMapper.select(spuSaleAttrValue);

            saleAttr.setSpuSaleAttrValueList(spuSaleAttrValues);
        }

        return spuSaleAttrs;
    }

    /**
     * 获取商品图片信息
     * @param spuId
     * @return
     */
    @Override
    public List<SpuImage> getSpuImageListBySpuId(String spuId) {

        SpuImage spuImage = new SpuImage();
        spuImage.setSpuId(spuId);
        return spuImageMapper.select(spuImage);
    }

    /**
     * 获取商品的销售属性信息
     * @param stringStringHashMap
     * @return
     */
    @Override
    public List<SpuSaleAttr> getSpuSaleAttrListCheckBySku(Map<String, String> stringStringHashMap) {
        return spuSaleAttrValueMapper.selectSpuSaleAttrListCheckBySku(stringStringHashMap);
    }

    /**
     * spu的sku和销售属性对应关系的hash表
     * @param spuId
     * @return
     */
    @Override
    public List<SkuInfo> getSkuSaleAttrValueListBySpuId(String spuId) {
        return spuSaleAttrValueMapper.selectSkuSaleAttrValueListBySpu(spuId);
    }
}
