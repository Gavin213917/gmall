package com.liskov.gmall.list;

import com.alibaba.dubbo.config.annotation.Reference;

import com.liskov.gmall.bean.SkuInfo;
import com.liskov.gmall.bean.SkuLsInfo;
import com.liskov.gmall.service.SkuService;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.apache.commons.beanutils.BeanUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
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
    public void search() {
        List<SkuLsInfo> skuLsInfos = new ArrayList<>();
        // 如何查询es中的数据
        //初级版
        Search search1 = new Search.Builder("\"query\": {\n" +
                "    \"bool\":{\n" +
                "    \"filter\": [\n" +
                "           {\n" +
                "           \"term\": {\n" +
                "             \"skuAttrValueList.valueId\": \"51\"\n" +
                "           }},\n" +
                "          {\"term\": {\n" +
                "             \"skuAttrValueList.valueId\": \"48\"\n" +
                "           }}\n" +
                "          ]\n" +
                "    \"must\": [\n" +
                "        {\n" +
                "          \"match\": {\n" +
                "            \"skuName\": \"华为\"\n" +
                "          }\n" +
                "        }\n" +
                "      ] \n" +
                "    }").addIndex("gmall").addType("SkuLsInfo").build();
        //升级版，建议使用
        Search search = new Search.Builder(getMyDsl()).addIndex("gmall").addType("SkuLsInfo").build();

        try {
            SearchResult execute = jestClient.execute(search);

            List<SearchResult.Hit<SkuLsInfo, Void>> hits = execute.getHits(SkuLsInfo.class);

            for (SearchResult.Hit<SkuLsInfo, Void> hit : hits) {
                SkuLsInfo source = hit.source;
                skuLsInfos.add(source);
            }

            System.out.println(skuLsInfos.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static  String getMyDsl() {

        // filter term
        // must match

        // 创建一个dsl工具对象
        SearchSourceBuilder dsl = new SearchSourceBuilder();

        // 创建一个先过滤后搜索的query对象
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();


        // query对象过滤语句
        TermQueryBuilder t1 = new TermQueryBuilder("catalog3Id", "1");
        boolQueryBuilder.filter(t1);
        TermQueryBuilder t2 = new TermQueryBuilder("skuAttrValueList.valueId", "1");
        boolQueryBuilder.filter(t2);
        TermQueryBuilder t3 = new TermQueryBuilder("skuAttrValueList.valueId", "4");
        boolQueryBuilder.filter(t3);

        String[] s = new String[2];
        s[0] = "1";
        s[1] = "4";
        TermsQueryBuilder t4 = new TermsQueryBuilder("skuAttrValueList.valueId", s);// 并集
        boolQueryBuilder.filter(t4);


        // query对象搜索语句
        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("skuName", "华为");
        boolQueryBuilder.must(matchQueryBuilder);

        MatchQueryBuilder matchQueryBuilder1 = new MatchQueryBuilder("skuDesc", "华为");
        boolQueryBuilder.must(matchQueryBuilder1);

        // 将query和from和size放入dsl
        dsl.query(boolQueryBuilder);
        dsl.size(100);
        dsl.from(0);


        System.out.println(dsl.toString());
        return dsl.toString();
    }

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
