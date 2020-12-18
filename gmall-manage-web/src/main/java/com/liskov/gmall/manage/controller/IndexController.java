package com.liskov.gmall.manage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    //跳转初始页面
    @RequestMapping("index")
    public String index(){
        return "index";
    }

    //跳转属性信息管理页面
    @RequestMapping("attrListPage")
    public String attrListPage(){
        return "attrListPage";
    }

    //跳转SPU商品信息管理页面
    @RequestMapping("spuListPage")
    public String spuListPage(){
        return "spuListPage";
    }
}
