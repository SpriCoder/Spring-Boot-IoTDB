package com.njuzhy.demo.config;

import com.njuzhy.demo.constant.MyResponse;
import com.njuzhy.demo.constant.ResponseCode;
import com.njuzhy.demo.constant.ServerException;
import lombok.extern.slf4j.Slf4j;
import org.apache.iotdb.jdbc.IoTDBSQLException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author stormbroken
 * 全局异常捕获处理
 * Create by 2021/03/07
 * @Version 1.0
 **/

@Slf4j
@ControllerAdvice(basePackages = {"com.njuzhy.demo"})
@Component
public class GlobalExceptionHandler {

    private static int exceptionNumber = 1;

    /**
     * 全局的异常处理包
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public MyResponse errorHandler(Exception e){
        synchronized (this) {
            if(e instanceof ServerException){
                // 处理系统包裹的错误
                ServerException serverException = (ServerException) e;
                e.printStackTrace();
                return new MyResponse(serverException.getCode(), e.getMessage());
            } else if (e instanceof IoTDBSQLException){
                e.printStackTrace();
                return new MyResponse(ResponseCode.CATCH_EXCEPTION, e.getMessage());
            } else {
                // 处理一般错误
                StringBuffer exceptionStatus = new StringBuffer("Exception[ id = ");
                exceptionStatus.append(exceptionNumber);
                exceptionStatus.append(" ]: ");
                if(e instanceof HttpMessageNotReadableException){
                    exceptionStatus.append("传输数据格式错误");
                }else{
                    exceptionStatus.append(e.getMessage());
                }
                e.printStackTrace();
                return MyResponse.exception(exceptionStatus.toString());
            }
        }
    }
}
