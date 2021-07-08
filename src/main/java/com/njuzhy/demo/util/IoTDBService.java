package com.njuzhy.demo.util;

import com.njuzhy.demo.data.MySystemInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.List;

/**
 * @Author stormbroken
 * Create by 2021/07/07
 * @Version 1.0
 **/

@Service
public class IoTDBService {
    @Value("${iotdb.url}")
    String url;
    @Value("${iotdb.username}")
    String username;
    @Value("${iotdb.password}")
    String password;

    /**
     * 建立连接
     */
    Connection connection;

    public void getConnection() throws ClassNotFoundException, SQLException {
        if(connection == null){
            synchronized (this){
                if(connection == null){
                    Class.forName("org.apache.iotdb.jdbc.IoTDBDriver");
                    connection = DriverManager.getConnection(url, username, password);
                }
            }
        }
    }

    /**
     * 新建Statement后执行语句
     * @param statementStr
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public boolean executeStatement(String statementStr)
            throws ClassNotFoundException, SQLException{
        Statement statement = getStatement();
        return statement.execute(statementStr);
    }

    /**
     * 新建Statement后执行一批语句
     * @param statements
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public void executeStatements(List<String> statements)
            throws ClassNotFoundException, SQLException{
        Statement statement = getStatement();
        for(String statementStr: statements){
            statement.addBatch(statementStr);
        }
        statement.executeBatch();
    }

    /**
     * 按照查询语句进行查询
     * @param query
     * @param fetchSize
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public ResultSet executeQuery(String query, Integer fetchSize)
            throws ClassNotFoundException, SQLException{
        Statement statement = getStatement();
        statement.setFetchSize(fetchSize);
        return statement.executeQuery(query);
    }

    /**
     * 插入一条系统数据
     * @param statement
     * @param mySystemInfo
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public boolean insertSystemInfo(String statement, MySystemInfo mySystemInfo)
            throws ClassNotFoundException, SQLException{
        PreparedStatement preparedStatement = getPreparedStatement(statement);
        preparedStatement.setLong(1, mySystemInfo.getTime());
        preparedStatement.setDouble(2, mySystemInfo.getMemoryUsage());
        preparedStatement.setDouble(3, mySystemInfo.getCpuUsage());
        preparedStatement.setDouble(4, mySystemInfo.getDiskUsage());

        return preparedStatement.execute();
    }

    /**
     * 输出结果集
     * @param resultSet
     * @throws SQLException
     */
    public String outputResult(ResultSet resultSet) throws SQLException {
        StringBuilder stringBuilder = new StringBuilder();
        if (resultSet != null) {
            stringBuilder.append("--------------------------");
            final ResultSetMetaData metaData = resultSet.getMetaData();
            final int columnCount = metaData.getColumnCount();
            for (int i = 0; i < columnCount; i++) {
                stringBuilder.append(metaData.getColumnLabel(i + 1));
                stringBuilder.append(" ");
            }
            stringBuilder.append("\n");
            while (resultSet. next()) {
                StringBuilder builder = new StringBuilder();
                for (int i = 1; ; i++) {
                    builder.append(resultSet.getString(i));
                    if (i < columnCount) {
                        builder.append(", ");
                    } else {
                        builder.append("\n");
                        break;
                    }
                }
                stringBuilder.append(builder);
            }
            stringBuilder.append("--------------------------\n\n");
        }
        return stringBuilder.toString();
    }

    /**
     * 获取 Statement 进行执行
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    private Statement getStatement()
            throws ClassNotFoundException, SQLException{
        getConnection();
        return connection.createStatement();
    }

    /**
     * 获取 PreparedStatement 进行执行
     * @param statement
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    private PreparedStatement getPreparedStatement(String statement)
            throws ClassNotFoundException, SQLException{
        getConnection();
        return connection.prepareStatement(statement);
    }
}
