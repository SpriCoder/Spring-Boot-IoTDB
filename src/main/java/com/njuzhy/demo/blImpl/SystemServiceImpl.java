package com.njuzhy.demo.blImpl;

import com.njuzhy.demo.bl.SystemService;
import com.njuzhy.demo.constant.MyResponse;
import com.njuzhy.demo.data.MySystemInfo;
import com.njuzhy.demo.util.IoTDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO 资源管理需要再斟酌，提前关闭导致无法读出数据
 *
 * @Author stormbroken
 * Create by 2021/07/07
 * @Version 1.0
 **/

@Service
public class SystemServiceImpl implements SystemService {
    private static final String INSERT = "insert into root.demo.pc1(timestamp, memory, cpu, disk) values(?,?,?,?)";
    @Autowired
    IoTDBService ioTDBService;

    /**
     * 初始化系统服务
     *
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @Override
    public MyResponse init() throws ClassNotFoundException, SQLException {
        List<String> statements = new ArrayList<>();
        statements.add("set storage group to root.demo");
        statements.add("create timeseries root.demo.pc1.memory WITH datatype=FLOAT,encoding=RLE");
        statements.add("create timeseries root.demo.pc1.cpu WITH datatype=FLOAT,encoding=RLE");
        statements.add("create timeseries root.demo.pc1.disk WITH datatype=FLOAT,encoding=RLE");
        ioTDBService.executeStatements(statements);
        return MyResponse.ok("创建成功");
    }

    /**
     * 插入一条系统数据
     *
     * @param mySystemInfo
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @Override
    public boolean insert(MySystemInfo mySystemInfo) throws ClassNotFoundException, SQLException {
        return ioTDBService.insertSystemInfo(INSERT, mySystemInfo);
    }

    /**
     * 查询全部数据
     *
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     * @param size
     */
    @Override
    public MyResponse findAll(Integer size) throws ClassNotFoundException, SQLException {
        ResultSet resultSet = ioTDBService.executeQuery("select * from root.demo", size);
        return MyResponse.ok(set2Result(resultSet));
    }

    /**
     * 删除所有数据
     *
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @Override
    public MyResponse delete() throws ClassNotFoundException, SQLException {
        ioTDBService.executeStatement("DELETE STORAGE GROUP root.demo");
        return MyResponse.ok("删除成功");
    }

    /**
     * 将结果集转换为系统参数
     * @param resultSet
     * @return
     * @throws SQLException
     */
    private List<MySystemInfo> set2Result(ResultSet resultSet) throws SQLException{
        List<MySystemInfo> mySystemInfos = new ArrayList<>();
        if (resultSet != null) {
            while (resultSet.next()) {
                MySystemInfo mySystemInfo = new MySystemInfo();
                mySystemInfo.setTime(Long.valueOf(resultSet.getString(1)));
                mySystemInfo.setDiskUsage(Double.valueOf(resultSet.getString(2)));
                mySystemInfo.setMemoryUsage(Double.valueOf(resultSet.getString(3)));
                mySystemInfo.setCpuUsage(Double.valueOf(resultSet.getString(4)));

                mySystemInfos.add(mySystemInfo);
            }
        }
        return mySystemInfos;
    }
}
