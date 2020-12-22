package com.liskov.gmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;

import com.liskov.gmall.bean.BaseAttrInfo;
import com.liskov.gmall.bean.BaseAttrValue;
import com.liskov.gmall.manage.mapper.BaseAttrInfoMapper;
import com.liskov.gmall.manage.mapper.BaseAttrValueMapper;
import com.liskov.gmall.service.AttrService;
import com.liskov.gmall.utils.ConstantUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class AttrServiceImpl implements AttrService {

    @Autowired
    BaseAttrInfoMapper baseAttrInfoMapper;

    @Autowired
    BaseAttrValueMapper baseAttrValueMapper;

    /**
     * 属性表list
     * @param catalog3Id
     * @return
     */
    @Override
    public List<BaseAttrInfo> getAttrList(String catalog3Id) {
        BaseAttrInfo baseAttrInfo = new BaseAttrInfo();
        baseAttrInfo.setCatalog3Id(catalog3Id);
        baseAttrInfo.setIsEnabled(ConstantUtils.ISENABLED_QY);
        List<BaseAttrInfo> select = baseAttrInfoMapper.select(baseAttrInfo);
        return select;
    }

    /**
     * 保存或修改属性
     * @param baseAttrInfo
     */
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
            baseAttrInfo.setIsEnabled(ConstantUtils.ISENABLED_QY);
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

    /**
     * 获取属性值信息
     * @param attrId
     * @return
     */
    @Override
    public BaseAttrInfo getAttrInfo(String attrId) {
        //查询属性基本信息
        BaseAttrInfo baseAttrInfo = baseAttrInfoMapper.selectByPrimaryKey(attrId);
        //查询属性对应的属性值
        BaseAttrValue  baseAttrValue =new BaseAttrValue();
        baseAttrValue.setAttrId(baseAttrInfo.getId());
        baseAttrValue.setIsEnabled(ConstantUtils.ISENABLED_QY);
        List<BaseAttrValue> baseAttrValueList = baseAttrValueMapper.select(baseAttrValue);
        //将属性值信息放入属性表中，并返回属性信息
        baseAttrInfo.setAttrValueList(baseAttrValueList);
        return baseAttrInfo;
    }

    /**
     * 获取属性列表
     * @param catalog3Id
     * @return
     */
    @Override
    public List<BaseAttrInfo> getAttrListByCtg3Id(String catalog3Id) {
        BaseAttrInfo baseAttrInfo = new BaseAttrInfo();
        baseAttrInfo.setCatalog3Id(catalog3Id);
        List<BaseAttrInfo> select = baseAttrInfoMapper.select(baseAttrInfo);

        for (BaseAttrInfo attrInfo : select) {
            String attrId = attrInfo.getId();

            BaseAttrValue baseAttrValue = new BaseAttrValue();
            baseAttrValue.setAttrId(attrId);
            List<BaseAttrValue> select1 = baseAttrValueMapper.select(baseAttrValue);

            attrInfo.setAttrValueList(select1);
        }
        return select;
    }
}
