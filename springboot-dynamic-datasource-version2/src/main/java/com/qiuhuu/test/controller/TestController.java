package com.qiuhuu.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : qiuhuu
 * @date : 2020-11-09 16:17
 */
@RestController
public class TestController {

    @GetMapping("/test")
    public String test(String a,String b,String c,String d){
        System.out.println("a:"+a);
        System.out.println("b:"+b);
        System.out.println("c:"+c);
        System.out.println("d:"+d);
        return "dsada";
    }
}
