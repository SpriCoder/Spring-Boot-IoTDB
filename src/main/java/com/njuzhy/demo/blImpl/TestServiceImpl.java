package com.njuzhy.demo.blImpl;

import com.njuzhy.demo.bl.TestService;
import com.njuzhy.demo.constant.MyResponse;
import com.njuzhy.demo.util.IoTDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author stormbroken
 * Create by 2021/07/07
 * @Version 1.0
 **/

@Service
public class TestServiceImpl implements TestService {
    @Autowired
    IoTDBService ioTDBService;

    /**
     * 测试jdbc的默认创建部分
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @Override
    public MyResponse jdbcCreateTest() throws ClassNotFoundException, SQLException {
        List<String> statements = new ArrayList<>();
        statements.add("SET STORAGE GROUP TO root.sg1");
        statements.add("CREATE TIMESERIES root.sg1.d1.s1 WITH DATATYPE=INT64, ENCODING=RLE, COMPRESSOR=SNAPPY");
        statements.add("CREATE TIMESERIES root.sg1.d1.s2 WITH DATATYPE=INT64, ENCODING=RLE, COMPRESSOR=SNAPPY");
        statements.add("CREATE TIMESERIES root.sg1.d1.s3 WITH DATATYPE=INT64, ENCODING=RLE, COMPRESSOR=SNAPPY");
        ioTDBService.executeStatements(statements);
        return MyResponse.ok("创建成功");
    }

    /**
     * 测试jdbc的默认插入部分
     *
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @Override
    public MyResponse jdbcInsertTest() throws ClassNotFoundException, SQLException {
        List<String> statements = new ArrayList<>();
        for(int i = 0; i < 100; i ++){
            statements.add(prepareInsertStatement(i));
        }
        ioTDBService.executeStatements(statements);
        return MyResponse.ok("插入成功");
    }

    /**
     * 测试 JDBC 部分的查询部分
     *
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @Override
    public MyResponse jdbcQueryTest() throws ClassNotFoundException, SQLException {
        ResultSet resultSet1 = ioTDBService.executeQuery("select * from root where time <= 10", 10000);
        ResultSet resultSet2 = ioTDBService.executeQuery("select count(*) from root", 10000);
        ResultSet resultSet3 = ioTDBService.executeQuery("select count(*) from root where time >= 1 and time <= 100 group by ([0, 100), 20ms, 20ms)", 10000);
        return MyResponse.ok(ioTDBService.outputResult(resultSet1));
    }

    /**
     * 测试 JDBC 部分的删除部分
     *
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @Override
    public MyResponse jdbcDeleteTest() throws ClassNotFoundException, SQLException {
        ioTDBService.executeStatement("DELETE STORAGE GROUP root.sg1");
        return MyResponse.ok("删除成功");
    }

    /**
     * 准备默认语句
     * @param time
     * @return
     */
    private static String prepareInsertStatement(int time) {
        return "insert into root.sg1.d1(timestamp, s1, s2, s3) values("
                + time
                + ","
                + 1
                + ","
                + 1
                + ","
                + 1
                + ")";
    }
}
