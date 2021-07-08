package com.njuzhy.demo.data;

import lombok.Data;

/**
 * @Author stormbroken
 * Create by 2021/07/07
 * @Version 1.0
 **/

@Data
public class MySystemInfo {
    Double memoryUsage;
    Double cpuUsage;
    Double diskUsage;
    Long time;
}
