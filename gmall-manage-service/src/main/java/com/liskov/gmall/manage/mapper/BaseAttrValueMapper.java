package com.liskov.gmall.manage.mapper;

import com.liskov.gmall.bean.BaseAttrInfo;
import com.liskov.gmall.bean.BaseAttrValue;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BaseAttrValueMapper extends Mapper<BaseAttrValue>{

    List<BaseAttrInfo> selectAttrListByValueIds(String ids);
}
