package com.liskov.gmall.service;



import com.liskov.gmall.bean.BaseCatalog1;
import com.liskov.gmall.bean.BaseCatalog2;
import com.liskov.gmall.bean.BaseCatalog3;

import java.util.List;

public interface CatalogService {
    List<BaseCatalog1> getCatalog1();

    List<BaseCatalog2> getCatalog2(String catalog1Id);

    List<BaseCatalog3> getCatalog3(String catalog2Id);
}
