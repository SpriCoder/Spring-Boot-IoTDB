package com.njuzhy.demo.util;

import com.njuzhy.demo.bl.SystemService;
import com.njuzhy.demo.data.MySystemInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import oshi.json.SystemInfo;
import oshi.json.hardware.CentralProcessor;
import oshi.json.hardware.GlobalMemory;
import oshi.json.hardware.HWDiskStore;
import oshi.json.hardware.HardwareAbstractionLayer;

import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.sql.SQLException;

/**
 * @Author stormbroken
 * Create by 2021/07/07
 * @Version 1.0
 **/

@Service
public class ExtractService {
    private static SystemInfo systemInfo = new SystemInfo();

    @Autowired
    SystemService systemService;

    /**
     * 获取内存的使用率
     *
     * @return
     */
    public static double getMemoryUsage() {
        HardwareAbstractionLayer hal = systemInfo.getHardware();
        GlobalMemory memory = hal.getMemory();
        long available = memory.getAvailable();
        long total = memory.getTotal();
        return available / (total * 1.0);
    }

    /**
     * 获取CPU的使用率
     *
     * @return CPU使用率
     */
    public static double getCpuUsage() {
        HardwareAbstractionLayer hal = systemInfo.getHardware();
        CentralProcessor processor = hal.getProcessor();
        double useRate = processor.getSystemCpuLoadBetweenTicks();
        return useRate;
    }

    /**
     * 获取磁盘的使用率
     *
     * @return CPU使用率 0.36
     */
    public static double getDiskUsage() {
        if (isWindows()) {
            return getWinDiskUsage();
        }
        return getUnixDiskUsage();
    }


    /**
     * 判断系统是否为windows
     *
     * @return 是否
     */
    private static boolean isWindows() {
        return System.getProperties().getProperty("os.name").toUpperCase().contains("WINDOWS");
    }

    /**
     * 获取linux 磁盘使用率
     *
     * @return 磁盘使用率
     */
    private static double getUnixDiskUsage() {
        String ioCmdStr = "df -h /";
        String resultInfo = runCommand(ioCmdStr);
        String[] data = resultInfo.split(" +");
        double total = Double.parseDouble(data[10].replace("%", ""));
        return total / 100;
    }

    /**
     * 获取linux 磁盘使用率
     *
     * @return 磁盘使用率
     */
    private static double getWinDiskUsage() {

        HardwareAbstractionLayer hal = systemInfo.getHardware();
        HWDiskStore[] diskStores = hal.getDiskStores();
        long total = 0;
        long used = 0;
        if (diskStores != null && diskStores.length > 0) {
            for (HWDiskStore diskStore : diskStores) {
                long size = diskStore.getSize();
                long writeBytes = diskStore.getWriteBytes();
                total += size;
                used += writeBytes;
            }
        }
        return used / (total * 1.0);
    }


    /**
     * 执行系统命令
     *
     * @param CMD 命令
     * @return 字符串结果
     */
    private static String runCommand(String CMD) {
        StringBuilder info = new StringBuilder();
        try {
            Process pos = Runtime.getRuntime().exec(CMD);
            pos.waitFor();
            InputStreamReader isr = new InputStreamReader(pos.getInputStream());
            LineNumberReader lnr = new LineNumberReader(isr);
            String line;
            while ((line = lnr.readLine()) != null) {
                info.append(line).append("\n");
            }
        } catch (Exception e) {
            info = new StringBuilder(e.toString());
        }
        return info.toString();
    }

    @Scheduled(cron = "* * * * * *")
    public void updateInfo() throws ClassNotFoundException, SQLException {
        MySystemInfo mySystemInfo = new MySystemInfo();
        mySystemInfo.setCpuUsage(getCpuUsage());
        mySystemInfo.setDiskUsage(getDiskUsage());
        mySystemInfo.setMemoryUsage(getMemoryUsage());
        mySystemInfo.setTime(System.currentTimeMillis());

        systemService.insert(mySystemInfo);
    }

}
