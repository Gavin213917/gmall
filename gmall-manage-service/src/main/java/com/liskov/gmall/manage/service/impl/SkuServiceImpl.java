package com.liskov.gmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.liskov.gmall.bean.*;
import com.liskov.gmall.manage.mapper.SkuAttrValueMapper;
import com.liskov.gmall.manage.mapper.SkuImageMapper;
import com.liskov.gmall.manage.mapper.SkuInfoMapper;
import com.liskov.gmall.manage.mapper.SkuSaleAttrValueMapper;
import com.liskov.gmall.service.SkuService;
import com.liskov.gmall.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import java.util.List;

@Service
public class SkuServiceImpl implements SkuService {

    @Autowired
    SkuInfoMapper skuInfoMapper;
    @Autowired
    SkuAttrValueMapper skuAttrValueMapper;
    @Autowired
    SkuSaleAttrValueMapper skuSaleAttrValueMapper;
    @Autowired
    SkuImageMapper skuImageMapper;
    @Autowired 
    RedisUtil redisUtil;


    /**
     * 查询商品单元列表信息
     * @param spuId
     * @return
     */
    @Override
    public List<SkuInfo> getSkuListBySpu(String spuId) {
        SkuInfo skuInfo = new SkuInfo();
        skuInfo.setSpuId(spuId);
        List<SkuInfo> select = skuInfoMapper.select(skuInfo);
        return select;
    }

    /**
     * 保存商品单元信息
     * @param skuInfo
     * @return
     */
    @Override
    public void saveSku(SkuInfo skuInfo) {
        //保存 库存单元表
        skuInfoMapper.insertSelective(skuInfo);
        String skuId = skuInfo.getId();

        //保存 平台属性值关联表
        //前台的valueId 不能为空，否则会报错，Incorrect integer value: ” for column ‘id’ at row 1
        //原因：因为用了高版本的mysql导致的，发现高版本的mysql如果是空值应该要写NULL或者0
        List<SkuAttrValue> skuAttrValueList = skuInfo.getSkuAttrValueList();
        for (SkuAttrValue skuAttrValue : skuAttrValueList) {
            skuAttrValue.setSkuId(skuId);
            skuAttrValueMapper.insert(skuAttrValue);
        }

        //销售属性值表
        List<SkuSaleAttrValue> skuSaleAttrValueList = skuInfo.getSkuSaleAttrValueList();
        for (SkuSaleAttrValue skuSaleAttrValue : skuSaleAttrValueList) {
            skuSaleAttrValue.setSkuId(skuId);
            skuSaleAttrValueMapper.insert(skuSaleAttrValue);
        }

        //库存单元图片表
        List<SkuImage> skuImageList = skuInfo.getSkuImageList();
        for (SkuImage skuImage : skuImageList) {
            skuImage.setSkuId(skuId);
            skuImageMapper.insert(skuImage);
        }

    }

    /**
     * 查询库存商品信息
     * @param skuId
     * @return
     */
    @Override
    public SkuInfo getSkuById(String skuId) {

        Jedis jedis = null;
        try {
            jedis = redisUtil.getJedis();
        }catch (Exception e){
            return null;
        }
        SkuInfo skuInfo = null;

        // 查询redis缓存
        String key = "sku:" + skuId + ":info";
        String val = jedis.get(key);

        if("empty".equals(val)){
            System.out.println(Thread.currentThread().getName()+"发现数据库中暂时没有该商品，直接返回空对象");
            return skuInfo;
        }

        if (StringUtils.isBlank(val)) {
            System.out.println(Thread.currentThread().getName()+"发现缓存中没有数据，申请分布式锁");
            // 申请缓存锁，px 过期时间
            String OK = jedis.set("sku:" + skuId + ":lock", "1", "nx", "px", 5000);

            if("OK".equals(OK)){// 拿到缓存锁
                //便于测试自旋，让拿到锁的线程睡一会
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(Thread.currentThread().getName()+"获得分布式锁，开始访问数据");
                // 查询db
                skuInfo = getSkuByIdFromDb(skuId);

                if(skuInfo!=null){
                    System.out.println(Thread.currentThread().getName()+"通过分布式锁，查询到数据，同步缓存");
                    // 同步缓存
                    jedis.set(key, JSON.toJSONString(skuInfo));

                }else{
                    // 通知同伴
                    System.out.println(Thread.currentThread().getName()+"通过分布式锁，没有查询到数据，通知同伴在10秒之内不要访问该sku");
                    jedis.setex("sku:" + skuId + ":info", 10,"empty");
                }

                // 归还缓存锁
                System.out.println(Thread.currentThread().getName()+"归还分布式锁");
                jedis.del("sku:" + skuId + ":lock");

            }else{// 没有拿到缓存锁
                // 自旋
                System.out.println(Thread.currentThread().getName()+"发现分布式锁被占用，开始自旋");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getSkuById(skuId);
            }
        }else{
            // 正常转换缓存数据
            System.out.println(Thread.currentThread().getName()+"正常从缓存中取得数据，返回结果");
            skuInfo = JSON.parseObject(val, SkuInfo.class);
        }
        return skuInfo;
    }

    private SkuInfo getSkuByIdFromDb(String skuId) {
        SkuInfo skuInfo = new SkuInfo();
        skuInfo.setId(skuId);
        SkuInfo skuInfo1 = skuInfoMapper.selectOne(skuInfo);
        if(skuInfo1 != null){//会有空指针
            //图片信息
            SkuImage skuImage = new SkuImage();
            skuImage.setSkuId(skuId);
            List<SkuImage> skuImageList = skuImageMapper.select(skuImage);
            skuInfo1.setSkuImageList(skuImageList);

            SkuSaleAttrValue skuSaleAttrValue = new SkuSaleAttrValue();
            skuSaleAttrValue.setSkuId(skuId);
            List<SkuSaleAttrValue> skuSaleAttrValues = skuSaleAttrValueMapper.select(skuSaleAttrValue);
            skuInfo1.setSkuSaleAttrValueList(skuSaleAttrValues);
        }
        return skuInfo1;
    }
}
