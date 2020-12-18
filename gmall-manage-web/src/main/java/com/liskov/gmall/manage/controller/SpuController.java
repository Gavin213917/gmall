package com.liskov.gmall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.liskov.gmall.bean.BaseSaleAttr;
import com.liskov.gmall.bean.SpuInfo;
import com.liskov.gmall.service.SpuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class SpuController {

    @Reference
    SpuService spuService;

    /**
     * 商品信息list
     * @param catalog3Id
     * @return
     */
    @RequestMapping("spuList")
    @ResponseBody
    public List<SpuInfo> spuList(String catalog3Id){

        List<SpuInfo> spuInfos = spuService.spuList(catalog3Id);
        return spuInfos;

    }

    /**
     * 基本销售属性表-字典表
     * @return
     */
    @RequestMapping("baseSaleAttrList")
    @ResponseBody
    public List<BaseSaleAttr> baseSaleAttrList(){

        List<BaseSaleAttr> baseSaleAttrs = spuService.baseSaleAttrList();
        return baseSaleAttrs;

    }

    /**
     * 保存商品信息
     * @param spuInfo
     * @return
     */
    @RequestMapping("saveSpu")
    @ResponseBody
    public String saveSpu(SpuInfo spuInfo){
        spuService.saveSpu(spuInfo);
        return "success";

    }


}
