package com.peace.controller;

/**
 * @ClassName : Example  //类名
 * @Description : hello word  //描述
 * @Author : peaceWW  //作者
 * @Date: 2020-07-13 11:43  //时间
 */
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;

@RestController
@EnableAutoConfiguration
public class Example {

    @RequestMapping("/")
    String home() {
        return "Hello World!";
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Example.class, args);
    }

}
