package com.liskov.gmall.item.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.liskov.gmall.bean.SkuInfo;
import com.liskov.gmall.bean.SkuSaleAttrValue;
import com.liskov.gmall.bean.SpuSaleAttr;
import com.liskov.gmall.bean.UserInfo;
import com.liskov.gmall.service.SkuService;
import com.liskov.gmall.service.SpuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ItemController {

    @Reference
    SkuService skuService;
    @Reference
    SpuService spuService;

    /**
     * 首页
     * @param map
     * @return
     */
    @RequestMapping("{skuId}.html")
    public String item(@PathVariable String skuId, ModelMap map){

        SkuInfo skuInfo = skuService.getSkuById(skuId);

        map.put("skuInfo",skuInfo);

        String spuId = skuInfo.getSpuId();
//         分别查出两者，双重 for 比较，一样的就被选中，不一样的就不选中
//        // 当前sku所包含的销售属性
//        List<SkuSaleAttrValue> skuSaleAttrValueList = skuInfo.getSkuSaleAttrValueList();
//        // spu的销售属性列表
//        List<SpuSaleAttr> saleAttrListBySpuId = spuService.getSaleAttrListBySpuId(spuId);

        //spu的sku和销售属性对应关系的hash表
        List<SkuInfo> infos = spuService.getSkuSaleAttrValueListBySpuId(spuId);
        Map<String, String> mapJson = new HashMap<>();
        for (SkuInfo info : infos) {
            String v = info.getId();
            List<SkuSaleAttrValue> skuSaleAttrValueList = info.getSkuSaleAttrValueList();
            String k = "";
            for (SkuSaleAttrValue skuSaleAttrValue : skuSaleAttrValueList) {
                k = k+ "|" + skuSaleAttrValue.getSaleAttrId();
            }
            mapJson.put(k,v);
        }
        String skuJson = JSON.toJSONString(mapJson);
        map.put("skuJson",skuJson);
        //
        Map<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("spuId",spuId);
        stringStringHashMap.put("skuId",skuId);
        List<SpuSaleAttr> saleAttrListBySpuId = spuService.getSpuSaleAttrListCheckBySku(stringStringHashMap);
        map.put("spuSaleAttrListCheckBySku",saleAttrListBySpuId);
        return "item";
    }

    /**
     * 测试thymeleaf
     * @param map
     * @return
     */
    @RequestMapping("index")
    public String index(ModelMap map){
        map.put("hello","hello thymeleaf");
        List<UserInfo> userInfos = new ArrayList<UserInfo>();
        for (int i = 0; i <5 ; i++) {
            UserInfo userInfo = new UserInfo();
            userInfo.setNickName("小"+i);
            userInfo.setPhoneNum("12333333333");
            userInfos.add(userInfo);
        }
        map.put("userInfos",userInfos);
        return "demo";
    }
}

