package com.liskov.gmall.service;


import com.liskov.gmall.bean.BaseAttrInfo;
import com.liskov.gmall.bean.BaseAttrValue;

import java.util.List;
import java.util.Set;

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

    /**
     * 获取属性列表
     * @param catalog3Id
     * @return
     */
    List<BaseAttrInfo> getAttrListByCtg3Id(String catalog3Id);

    /**
     * 查询平台属性列表
     * @param valueIds
     * @return
     */
    List<BaseAttrInfo> getAttrListByValueIds(Set<String> valueIds);
}
