package com.liskov.gmall.service;


import com.liskov.gmall.bean.BaseAttrInfo;

import java.util.List;

public interface AttrService {

    List<BaseAttrInfo> getAttrList(String catalog3Id);

    void saveAttr(BaseAttrInfo baseAttrInfo);
}
