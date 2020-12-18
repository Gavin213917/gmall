package com.liskov.gmall.service;



import com.liskov.gmall.bean.BaseCatalog1;
import com.liskov.gmall.bean.BaseCatalog2;
import com.liskov.gmall.bean.BaseCatalog3;

import java.util.List;

public interface CatalogService {
    /**
     * 获取一级分类信息
     * @return
     */
    List<BaseCatalog1> getCatalog1();

    /**
     * 获取二级分类信息
     * @return
     */
    List<BaseCatalog2> getCatalog2(String catalog1Id);

    /**
     * 获取三级分类信息
     * @return
     */
    List<BaseCatalog3> getCatalog3(String catalog2Id);
}
