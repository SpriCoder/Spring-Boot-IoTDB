package com.njuzhy.demo.controller;

import com.njuzhy.demo.bl.TestService;
import com.njuzhy.demo.constant.MyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author stormbroken
 * Create by 2021/07/07
 * @Version 1.0
 **/

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    TestService testService;

    @GetMapping("/health")
    public MyResponse health(){
        return MyResponse.ok("hello!");
    }

    @PostMapping("/jdbcCreateTest")
    public MyResponse jdbcCreateTest() throws Exception{
        return testService.jdbcCreateTest();
    }

    @PostMapping("/jdbcInsertTest")
    public MyResponse jdbcInsertTest() throws Exception{
        return testService.jdbcInsertTest();
    }

    @GetMapping("/jdbcQueryTest")
    public MyResponse jdbcQueryTest() throws Exception{
        return testService.jdbcQueryTest();
    }

    @PostMapping("/jdbcDeleteTest")
    public MyResponse jdbcDeleteTest() throws Exception{
        return testService.jdbcDeleteTest();
    }

}
