package com.liskov.gmall.manage.mapper;

import com.liskov.gmall.bean.SkuInfo;
import com.liskov.gmall.bean.SpuSaleAttr;
import com.liskov.gmall.bean.SpuSaleAttrValue;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface SpuSaleAttrValueMapper extends Mapper<SpuSaleAttrValue> {

    /**
     * 查出初始化页面时，对应商品加载的销售属性
     * @param stringStringHashMap
     * @return
     */
    List<SpuSaleAttr> selectSpuSaleAttrListCheckBySku(Map<String, String> stringStringHashMap);

    /**
     * spu的sku和销售属性对应关系的hash表
     * @param spuId
     * @return
     */
    List<SkuInfo> selectSkuSaleAttrValueListBySpu(String spuId);
}
