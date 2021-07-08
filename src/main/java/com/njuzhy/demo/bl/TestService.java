package com.njuzhy.demo.bl;

import com.njuzhy.demo.constant.MyResponse;

import java.sql.SQLException;

/**
 * @Author stormbroken
 * Create by 2021/07/07
 * @Version 1.0
 **/

public interface TestService {
    /**
     * 测试 JDBC 的默认创建部分
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    MyResponse jdbcCreateTest() throws ClassNotFoundException, SQLException;

    /**
     * 测试 JDBC 的默认插入部分
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    MyResponse jdbcInsertTest() throws ClassNotFoundException, SQLException;

    /**
     * 测试 JDBC 部分的查询部分
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    MyResponse jdbcQueryTest() throws ClassNotFoundException, SQLException;


    /**
     * 测试 JDBC 部分的删除部分
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    MyResponse jdbcDeleteTest() throws ClassNotFoundException, SQLException;

}
