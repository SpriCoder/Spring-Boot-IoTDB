package com.njuzhy.demo.controller;

import com.njuzhy.demo.bl.SystemService;
import com.njuzhy.demo.constant.MyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author stormbroken
 * Create by 2021/07/07
 * @Version 1.0
 **/

@RestController
@RequestMapping("/system")
public class SystemController {
    @Autowired
    SystemService systemService;

    @PostMapping("/init")
    public MyResponse init() throws Exception{
        return systemService.init();
    }

    @GetMapping("/findAll")
    public MyResponse findAll(@RequestParam Integer size) throws Exception{
        return systemService.findAll(size);
    }

    @PostMapping("/delete")
    public MyResponse delete() throws Exception{
        return systemService.delete();
    }
}
