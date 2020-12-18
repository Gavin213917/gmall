package com.liskov.gmall.service;


import com.liskov.gmall.bean.BaseAttrInfo;
import com.liskov.gmall.bean.BaseAttrValue;

import java.util.List;

public interface AttrService {

    /**
     * 属性表list
     * @param catalog3Id
     * @return
     */
    List<BaseAttrInfo> getAttrList(String catalog3Id);

    /**
     * 保存或修改属性
     * @param baseAttrInfo
     */
    void saveAttr(BaseAttrInfo baseAttrInfo);

    /**
     * 获取属性值信息
     * @param attrId
     * @return
     */
    BaseAttrInfo getAttrInfo(String attrId);
}
