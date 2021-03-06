package com.liskov.gmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.liskov.gmall.bean.BaseCatalog1;
import com.liskov.gmall.bean.BaseCatalog2;
import com.liskov.gmall.bean.BaseCatalog3;
import com.liskov.gmall.manage.mapper.BaseCatalog1Mapper;
import com.liskov.gmall.manage.mapper.BaseCatalog2Mapper;
import com.liskov.gmall.manage.mapper.BaseCatalog3Mapper;
import com.liskov.gmall.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class CatalogServiceImpl implements CatalogService {

    @Autowired
    BaseCatalog1Mapper baseCatalog1Mapper;
    @Autowired
    BaseCatalog2Mapper baseCatalog2Mapper;
    @Autowired
    BaseCatalog3Mapper baseCatalog3Mapper;

    /**
     * 获取一级分类信息
     * @return
     */
    @Override
    public List<BaseCatalog1> getCatalog1() {
        List<BaseCatalog1> baseCatalog1s = baseCatalog1Mapper.selectAll();
        return baseCatalog1s;
    }

    /**
     * 获取二级分类信息
     * @return
     */
    @Override
    public List<BaseCatalog2> getCatalog2(String catalog1Id) {
        BaseCatalog2 baseCatalog2 = new BaseCatalog2();
        baseCatalog2.setCatalog1Id(catalog1Id);
        List<BaseCatalog2> select = baseCatalog2Mapper.select(baseCatalog2);
        return select;
    }

    /**
     * 获取三级分类信息
     * @return
     */
    @Override
    public List<BaseCatalog3> getCatalog3(String catalog2Id) {
        BaseCatalog3 baseCatalog3 = new BaseCatalog3();
        baseCatalog3.setCatalog2Id(catalog2Id);
        List<BaseCatalog3> select = baseCatalog3Mapper.select(baseCatalog3);
        return select;
    }
}
