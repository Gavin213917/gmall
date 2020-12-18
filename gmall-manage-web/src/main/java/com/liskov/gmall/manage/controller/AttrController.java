package com.liskov.gmall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.liskov.gmall.bean.BaseAttrInfo;
import com.liskov.gmall.bean.BaseAttrValue;
import com.liskov.gmall.service.AttrService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class AttrController {

    @Reference
    AttrService attrService;

    /**
     * 属性表list
     * @param catalog3Id
     * @return
     */
    @RequestMapping("getAttrList")
    @ResponseBody
    public List<BaseAttrInfo> getAttrList(String catalog3Id){
        List<BaseAttrInfo> baseAttrInfos = attrService.getAttrList(catalog3Id);
        return baseAttrInfos;
    }

    /**
     * 保存或修改属性
     * @param baseAttrInfo
     */
    @RequestMapping("saveAttr")
    @ResponseBody
    public String saveAttr(BaseAttrInfo baseAttrInfo){
        attrService.saveAttr(baseAttrInfo);
        return "success";
    }

    /**
     * 获取属性值信息
     * @param attrId
     * @return
     */
    @RequestMapping("getAttrValueList")
    @ResponseBody
    public List<BaseAttrValue> getAttrValueList(@RequestParam("id") String attrId){
        //通过属性id 获取属性信息
        BaseAttrInfo attrInfo = attrService.getAttrInfo(attrId);
        //返回属性值信息
        return attrInfo.getAttrValueList();
    }
}
