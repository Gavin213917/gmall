package com.liskov.gmall.service;

import com.liskov.gmall.bean.SkuLsInfo;
import com.liskov.gmall.bean.SkuLsParam;

import java.util.List;

public interface ListService {
    /**
     * 搜索
     * @param skuLsParam
     * @return
     */
    List<SkuLsInfo> search(SkuLsParam skuLsParam);
}
