package com.liskov.gmall.list.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ListController {

    /**
     * 搜索
     * @return
     */
    @RequestMapping("list")
    public String search(){
        return "list";
    }

    /**
     * 首页资源
     * @return
     */
    @RequestMapping("index")
    public String index(){
        return "index";
    }
}
