package com.liskov.gmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;

import com.liskov.gmall.bean.BaseAttrInfo;
import com.liskov.gmall.bean.BaseAttrValue;
import com.liskov.gmall.manage.mapper.BaseAttrInfoMapper;
import com.liskov.gmall.manage.mapper.BaseAttrValueMapper;
import com.liskov.gmall.service.AttrService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class AttrServiceImpl implements AttrService {

    @Autowired
    BaseAttrInfoMapper baseAttrInfoMapper;

    @Autowired
    BaseAttrValueMapper baseAttrValueMapper;

    @Override
    public List<BaseAttrInfo> getAttrList(String catalog3Id) {

        BaseAttrInfo baseAttrInfo = new BaseAttrInfo();
        baseAttrInfo.setCatalog3Id(catalog3Id);
        List<BaseAttrInfo> select = baseAttrInfoMapper.select(baseAttrInfo);
        return select;
    }

    @Override
    public void saveAttr(BaseAttrInfo baseAttrInfo) {
//        //BaseAttrInfo 属性表  BaseAttrValue 属性值表
//        //插入后，将主键值（属性id）返回，在实体类上需要配置主键属性
//        baseAttrInfoMapper.insertSelective(baseAttrInfo);
//
//        //获取前台传过来添加的属性值
//        List<BaseAttrValue> attrValueList = baseAttrInfo.getAttrValueList();
//        //循环插入
//        for (BaseAttrValue baseAttrValue : attrValueList) {
//            //属性id
//            baseAttrValue.setAttrId(baseAttrInfo.getId());
//            baseAttrValueMapper.insert(baseAttrValue);
//        }

        //如果有主键就进行更新，如果没有就插入
        if(baseAttrInfo.getId()!=null&&baseAttrInfo.getId().length()>0){
            baseAttrInfoMapper.updateByPrimaryKey(baseAttrInfo);
        }else{
            //防止主键被赋上一个空字符串
//            if(baseAttrInfo.getId().length()==0){
//                baseAttrInfo.setId(null);
//            }
            baseAttrInfoMapper.insertSelective(baseAttrInfo);
        }
        //把原属性值全部清空
        BaseAttrValue baseAttrValue4Del = new BaseAttrValue();
        baseAttrValue4Del.setAttrId(baseAttrInfo.getId());
        baseAttrValueMapper.delete(baseAttrValue4Del);

        //重新插入属性
        if(baseAttrInfo.getAttrValueList()!=null&&baseAttrInfo.getAttrValueList().size()>0) {
            for (BaseAttrValue attrValue : baseAttrInfo.getAttrValueList()) {
                //防止主键被赋上一个空字符串
                if(attrValue.getId()!=null&&attrValue.getId().length()==0){
                    attrValue.setId(null);
                }
                attrValue.setAttrId(baseAttrInfo.getId());
                baseAttrValueMapper.insertSelective(attrValue);
            }
        }

    }

    @Override
    public BaseAttrInfo getAttrInfo(String attrId) {
        //查询属性基本信息
        BaseAttrInfo baseAttrInfo = baseAttrInfoMapper.selectByPrimaryKey(attrId);

        //查询属性对应的属性值
        BaseAttrValue  baseAttrValue4Query =new BaseAttrValue();
        baseAttrValue4Query.setAttrId(baseAttrInfo.getId());
        List<BaseAttrValue> baseAttrValueList = baseAttrValueMapper.select(baseAttrValue4Query);

        baseAttrInfo.setAttrValueList(baseAttrValueList);
        return baseAttrInfo;
    }
}
