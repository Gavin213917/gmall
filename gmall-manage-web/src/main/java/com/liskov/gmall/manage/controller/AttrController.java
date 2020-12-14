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


    @RequestMapping("saveAttr")
    @ResponseBody
    public String saveAttr(BaseAttrInfo baseAttrInfo){
        attrService.saveAttr(baseAttrInfo);

        return "success";

    }

    @RequestMapping("getAttrList")
    @ResponseBody
    public List<BaseAttrInfo> getAttrList(String catalog3Id){

        List<BaseAttrInfo> baseAttrInfos = attrService.getAttrList(catalog3Id);

        return baseAttrInfos;

    }

    @RequestMapping("getAttrValue")
    @ResponseBody
    public List<BaseAttrValue> getAttrInfo(@RequestParam("id") String attrId){
        BaseAttrInfo attrInfo = attrService.getAttrInfo(attrId);
        return attrInfo.getAttrValueList();
    }
}
