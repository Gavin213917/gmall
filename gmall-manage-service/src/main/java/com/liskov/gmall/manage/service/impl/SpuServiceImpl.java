package com.liskov.gmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.liskov.gmall.bean.BaseSaleAttr;
import com.liskov.gmall.bean.SpuInfo;
import com.liskov.gmall.bean.SpuSaleAttr;
import com.liskov.gmall.bean.SpuSaleAttrValue;
import com.liskov.gmall.manage.mapper.BaseSaleAttrMapper;
import com.liskov.gmall.manage.mapper.SpuInfoMapper;
import com.liskov.gmall.manage.mapper.SpuSaleAttrMapper;
import com.liskov.gmall.manage.mapper.SpuSaleAttrValueMapper;
import com.liskov.gmall.service.SpuService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Service
public class SpuServiceImpl implements SpuService {

    @Autowired
    SpuInfoMapper spuInfoMapper;
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
}
