package com.njuzhy.demo.bl;

import com.njuzhy.demo.constant.MyResponse;
import com.njuzhy.demo.data.MySystemInfo;

import java.sql.SQLException;

/**
 * @Author stormbroken
 * Create by 2021/07/07
 * @Version 1.0
 **/

public interface SystemService {
    /**
     * 初始化系统服务
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    MyResponse init() throws ClassNotFoundException, SQLException;

    /**
     * 插入一条系统数据
     * @param mySystemInfo
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    boolean insert(MySystemInfo mySystemInfo)throws ClassNotFoundException, SQLException;

    /**
     * 查询全部数据
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     * @param size
     */
    MyResponse findAll(Integer size)throws ClassNotFoundException, SQLException;

    /**
     * 删除所有数据
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    MyResponse delete() throws ClassNotFoundException, SQLException;
}
