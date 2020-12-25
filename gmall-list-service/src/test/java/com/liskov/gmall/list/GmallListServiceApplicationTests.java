package com.liskov.gmall.list;

import com.alibaba.dubbo.config.annotation.Reference;

import com.liskov.gmall.bean.SkuInfo;
import com.liskov.gmall.bean.SkuLsInfo;
import com.liskov.gmall.service.SkuService;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GmallListServiceApplicationTests {

    @Autowired
    JestClient jestClient;

    @Reference
    SkuService skuService;

    @Test
    public void contextLoads() {

        // 查询mysql中的sku信息
        String catalog3Id = "1";
        List<SkuInfo> skuInfoList = skuService.getSkuListByCatalog3Id(catalog3Id);

        // 转化es中的sku信息
        List<SkuLsInfo> skuLsInfos = new ArrayList<>();

        for (SkuInfo skuInfo : skuInfoList) {
            SkuLsInfo skuLsInfo = new SkuLsInfo();

            try {
                //拷贝属性 BeanUtils工具类方法
                BeanUtils.copyProperties(skuLsInfo,skuInfo);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            skuLsInfos.add(skuLsInfo);
        }

        // 导入到es中
        for (SkuLsInfo skuLsInfo : skuLsInfos) {
            String id = skuLsInfo.getId();
            //long skuId = Long.parseLong(id);
            //source 数据 index 数据库 type table id 主键
            Index build = new Index.Builder(skuLsInfo).index("gmall").type("SkuLsInfo").id(id).build();

            System.out.println(build.toString());
            try {
                jestClient.execute(build);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
